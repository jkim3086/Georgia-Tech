#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "myLib.h"
#include "StartScreen.h"
#include "text.h"
#include "bricks.h"
#include "BAR.h"
#include "OVER.h"
#include "GBALL.h"

#define INTERVAL_W 1
#define INTERVAL_H 1
#define NUMBRICKS 60
#define STARTBRICK_X 25
#define STARTBRICK_Y 10
#define BRICKS_WIDTH 15
#define BRICKS_HEIGHT 8
#define BAR_Width 30
#define BAR_Height 5
#define STARTBAR_X 105
#define STARTBAR_Y 135
#define SCREENHEIGHT 160
#define STARTBALL_X 110
#define STARTBALL_Y 125
#define BALL_Width 5
#define BALL_Height 5
#define abs(x) ((x) < 0 ? -(x) : (x))

extern const unsigned short StartScreen[38400];
extern const unsigned short bricks[120];
extern const unsigned short BAR[360];
extern const unsigned short OVER[38400];
extern const unsigned short GBALL[25];

typedef struct
{
    int X;
    int Y;
    bool visible;
} BRICKS;

typedef struct
{
    int RW;
    int CL;
    int CD;
} Bar;

typedef struct
{
    int row;
    int col;
    int rd;
    int cd;
} BALLOBJ;

enum CollisionResult {
	NO_COLLISION, LEFT, RIGHT, TOP, BOTTOM
};


BRICKS Bricks[NUMBRICKS]; //Declare brick objects
Bar bar;
Bar bar_old;
Bar *cur_bar;
Bar *oldcur_bar;	
BALLOBJ ball;
BALLOBJ ball_old;
BALLOBJ *cur;
BALLOBJ *oldcur;
int jei = -1;
int score = 0;
int life = 10;
char buffer[41];
char buffer_life[41];
int start = -1;
enum CollisionResult result;

u16 setColor();
void startscreen();
void cleanScreen();
void bricksSet(BRICKS* Bricks);
void bricksDraw();
void barSet();
void barControl();
void ballSet();
void ballControl();
enum CollisionResult CollisionDetect_BAR();
void CollisionDetect_BRICKS();
void overscreen();
void Reset();
int main(void) {
	REG_DISPCNT = MODE3 | BG2_ENABLE;
	
	while(1){
		if(start == -1) 
		{
			waitForVblank();
			bricksSet(Bricks);
			cleanScreen();
			startscreen();
			waitForVblank();
			start = 1;
			cleanScreen();
			bricksDraw();
			barSet();
			ballSet();
		}
		CollisionDetect_BRICKS();
		barControl();
		ballControl();
		waitForVblank();
		if(KEY_DOWN_NOW(BUTTON_SELECT))
		{
			Reset();
			continue;
		}
		if(score == 600 || life == 0){
			cleanScreen();
			overscreen();
			Reset();	
		}
	}
}	

void Reset(){
	score = 0;
	life = 10;
	start = -1;
}

void startscreen() {
	int i = 0;
	DMA[3].src = StartScreen;
        DMA[3].dst = videoBuffer;
	DMA[3].cnt = (240*160) | DMA_ON;
	while(!KEY_DOWN_NOW(BUTTON_START)){
		waitForVblank();
		if(i%30 > 0 && i%30 < 25) {
			drawString(130, 55, "Press start to continue", WHITE);
		}else{
			drawString(130, 55, "Press start to continue", BLACK);
		}
	i++;
	}
}

void overscreen() {
	int i = 0;
	DMA[3].src = OVER;
        DMA[3].dst = videoBuffer;
	DMA[3].cnt = (240*160) | DMA_ON;
	while(!KEY_DOWN_NOW(BUTTON_SELECT)){
		waitForVblank();
		if(i%30 > 0 && i%30 < 25) {
			drawString(130, 55, "Press Select to restart", WHITE);
		}else{
			drawString(130, 55, "Press Select to restart", BLACK);
		}
		i++;
	}
}

void cleanScreen() {
	DMA[3].src = BLACK;
        DMA[3].dst = videoBuffer;
        DMA[3].cnt = (240*160) | DMA_ON;
}

void bricksSet(BRICKS* Bricks){
	int x_point = STARTBRICK_X;
        int y_point = STARTBRICK_Y;

	for(int j = 0; j < NUMBRICKS; j++){
                if(j != 0 && j%12 == 0) {
                        y_point += (INTERVAL_H + BRICKS_HEIGHT);
                        x_point = Bricks[0].X;
                }
                Bricks[j].X = x_point;
                Bricks[j].Y = y_point;
		Bricks[j].visible = true;
                x_point += (INTERVAL_W + BRICKS_WIDTH);
        }
}

void bricksDraw(){
	for(int j = 0; j < NUMBRICKS; j++){
                drawImage3(Bricks[j].Y, Bricks[j].X, BRICKS_WIDTH, BRICKS_HEIGHT, bricks);
	}
}

void barSet(){      
	bar.CL = STARTBAR_X;
        bar.RW = STARTBAR_Y;
	bar.CD = 5;
	bar_old = bar;
}

void barControl(){
		ballControl();
        	if(KEY_DOWN_NOW(BUTTON_RIGHT))
        	{
			cur_bar->CL = cur_bar->CL + cur_bar->CD;
        	}
        	if(KEY_DOWN_NOW(BUTTON_LEFT))
        	{
             		cur_bar->CL = cur_bar->CL - (cur_bar->CD);
		}       
            	cur_bar = &bar;
           	oldcur_bar = &bar_old;
            
            	if(cur_bar->CL < 0)
            	{
                	cur_bar->CL = 0;
            	}
            	if(cur_bar->CL > 239-BAR_Width+1)
            	{
                	cur_bar->CL = 239-BAR_Width+1;
            	}
	    waitForVblank();
            oldcur_bar = &bar_old;
	    drawImage3(oldcur_bar->RW, oldcur_bar->CL, BAR_Width, BAR_Height, 0x0000);           
            bar_old = bar;      
            cur_bar = &bar;
            drawImage3(cur_bar->RW, cur_bar->CL, BAR_Width, BAR_Height, BAR); 
}

void ballSet(){        
	ball.row = STARTBALL_Y;
	ball.col = STARTBALL_X;
        ball.rd = 1;
        ball.cd = 1;
        ball_old = ball;
}

void ballControl(){
	enum CollisionResult hit_bar;
	int check = -1;
	
	    cur = &ball;
            oldcur = &ball_old;	
            cur->row = cur->row + cur->rd;
            cur->col += cur->cd;
	    
            hit_bar = CollisionDetect_BAR();
	    if(cur->row < 0)
            {
                cur->row = 0;
                cur->rd = -cur->rd;
            }
            if(cur->row > SCREENHEIGHT-BALL_Height+1)
            {
                cur->row = SCREENHEIGHT-BALL_Height+1;
                cur->rd = -cur->rd;
		life--;
		drawString(150, 170, buffer_life, BLACK);		
            }
            if(cur->col < 0)
            {
                cur->col = 0;
                cur->cd = -cur->cd;
            }
            if((cur->col + BALL_Width) > 239+1)
            {
                cur->col = 239 - BALL_Width;
                cur->cd = -cur->cd;
            } 
	    if(hit_bar == TOP)
	    {
		cur->row = cur_bar->RW-BALL_Height;
                cur->rd = -cur->rd;
	    }
	    else if(hit_bar == LEFT)
	    {
		cur->col = cur_bar->CL - (3 + BALL_Width);
                cur->cd = -cur->cd;
	    }
	    else if(hit_bar == RIGHT)
	    {
		cur->col = (cur_bar->CL + BAR_Width +3);
                cur->cd = -cur->cd;
	    }
	    else if(hit_bar == BOTTOM)
	    {
		cur->row = cur_bar->RW-BALL_Height;
		cur->rd = -cur->rd;
	    }
	    if(result == BOTTOM)
	    {
		cur->row = Bricks[jei].Y + 2 + BRICKS_HEIGHT;
                cur->rd = -cur->rd;
		check=0;
		score+=10;
	    }
	    if(result == TOP)
	    {
		cur->row = Bricks[jei].Y - BALL_Height - 2;
                cur->rd = -cur->rd;
		check=0;
		score+=10;
	    }
	    if(result == LEFT)
	    {
		cur->col = Bricks[jei].X - (2 + BALL_Width);
                cur->cd = -cur->cd;
		check=0;
		score+=10;
	    }
	    if(result == RIGHT)
	    {
		cur->col = (Bricks[jei].X + BRICKS_WIDTH + 2);
                cur->cd = -cur->cd;
		check=0;
		score+=10;
	    }
	    sprintf(buffer, "Score: %d", score);
	    sprintf(buffer_life, "Life: %d", life);
	    waitForVblank();
            oldcur = &ball_old;
	    if(jei >= 0 && result != NO_COLLISION && check == 0){
    		drawImage3(Bricks[jei].Y, Bricks[jei].X, BRICKS_WIDTH, BRICKS_HEIGHT, 0x0000);
		Bricks[jei].visible =false;
    	    }
	    drawImage3(oldcur->row, oldcur->col, BALL_Width, BALL_Height, 0x0000);            
            ball_old = ball;           
            cur = &ball;
	    drawImage3(oldcur->row, oldcur->col, BALL_Width, BALL_Height, GBALL);
	    drawRect(150, 48, 8, 24, BLACK);
            drawString(150, 5, buffer, WHITE);
	    drawString(150, 170, buffer_life, GREEN);
	    hit_bar = ' ';	    
	    result = ' ';
	    check = -1;
}


enum CollisionResult CollisionDetect_BAR() {
	bool check1 = false;
	bool check2 = false;
	bool check3 = false;
	bool check4 = false;
  
	check1 = (cur->col <= cur_bar->CL + BAR_Width);
	check2 = (cur_bar->CL <= cur->col + BALL_Width);
	check3 = (cur->row <= cur_bar->RW + BAR_Height);
	check4 = (cur_bar->RW <= cur->row + BALL_Height);

	if(!check1 || !check2 || !check3 || !check4) {
		return NO_COLLISION;
	}

	if(abs((cur->col+BALL_Width) - cur_bar->CL) > abs((cur->row + BALL_Height) - cur_bar->RW) && abs((cur_bar->CL + BAR_Width) - cur->col) > abs((cur->row + BALL_Height) - cur_bar->RW)){
		if(cur->col >= (cur_bar->CL-BALL_Width+1) && cur->col <= (cur_bar->CL+BAR_Width-1))
		{
			return TOP;
		}
	}
	else if(abs((cur->col+BALL_Width) - cur_bar->CL) > abs(cur->row - cur_bar->RW - BAR_Height) && abs((cur_bar->CL + BAR_Width) - cur->col) > abs(cur->row - cur_bar->RW - BAR_Height)) {
		if(cur->col >= (cur_bar->CL-BALL_Width+1) && cur->col <= (cur_bar->CL+BAR_Width-1))
		{
			return BOTTOM;
		}
	}
	else if(abs(cur->row + BALL_Height - cur_bar->RW) > abs(cur->col + BALL_Width - cur_bar->CL) && abs(cur->row - (cur_bar->RW + BAR_Height)) > abs((cur->col + BALL_Width) - cur_bar->CL)){
		if(cur->row >= (cur_bar->RW-BALL_Height+3) && cur->row <= (cur_bar->RW+BAR_Height-3))
		{
			return LEFT;
		}
	}
	else if(abs(cur->row + BALL_Height - cur_bar->RW) > abs(cur_bar->CL + BAR_Width - cur->col) && abs(cur->row - (cur_bar->RW + BAR_Height)) > abs(cur->col - (cur_bar->CL + BAR_Width))){
		if(cur->row >= (cur_bar->RW-BALL_Height+3) && cur->row <= (cur_bar->RW+BAR_Height-3))
		{
			return RIGHT;
		}
	}

	return NO_COLLISION;
}

void CollisionDetect_BRICKS() {
  int j;

  for(j = NUMBRICKS-1; j >= 0; j--){
     if(Bricks[j].visible == true) {
	if(abs((cur->col+BALL_Width) - Bricks[j].X) > abs((cur->row + BALL_Height) - Bricks[j].Y) && abs((Bricks[j].X + BRICKS_WIDTH) - cur->col) > abs((cur->row + BALL_Height) - Bricks[j].Y)){
		if(cur->col >= (Bricks[j].X-BALL_Width+1) && cur->col <= (Bricks[j].X+BRICKS_WIDTH-1))
		{
			jei=j;
			result= TOP;
		}
	}
	else if(abs((cur->col+BALL_Width) - Bricks[j].X) > abs(cur->row - Bricks[j].Y - BRICKS_HEIGHT) && abs((Bricks[j].X + BRICKS_WIDTH) - cur->col) > abs(cur->row - Bricks[j].Y - BRICKS_HEIGHT)) {
		if(cur->col >= (Bricks[j].X-BALL_Width+1) && cur->col <= (Bricks[j].X+BRICKS_WIDTH-1))
		{	
			jei=j;
			result= BOTTOM;
		}
	}
	else if(abs(cur->row + BALL_Height - Bricks[j].Y) > abs(cur->col + BALL_Width - Bricks[j].X) && abs(cur->row - (Bricks[j].Y + BRICKS_HEIGHT)) > abs((cur->col + BALL_Width) - Bricks[j].X)){
		if(cur->row >= (Bricks[j].Y-BALL_Height+3) && cur->row <= (Bricks[j].Y+BRICKS_HEIGHT-3))
		{
			jei=j;
			result= LEFT;
		}
	}
	else if(abs(cur->row + BALL_Height - Bricks[j].Y) > abs(Bricks[j].X + BRICKS_WIDTH - cur->col) && abs(cur->row - (Bricks[j].Y + BRICKS_HEIGHT)) > abs(cur->col - (Bricks[j].X + BRICKS_WIDTH))){
		if(cur->row >= (Bricks[j].Y-BALL_Height+3) && cur->row <= (Bricks[j].Y+BRICKS_HEIGHT-3))
		{
			jei=j;
			result= RIGHT;
		}
	}
      }
   }
}



