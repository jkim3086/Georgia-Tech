#include <stdlib.h>
#include "myLib.h"

u16* videoBuffer = (u16*) 0x6000000;

void setPixel(int x, int y, u16 color)
{
    videoBuffer[((x)*(240)+(y))] = color;
}

void waitForVblank()
{
	while(SCANLINECOUNTER > 160);
	while(SCANLINECOUNTER < 160);
}

void drawRect(int row, int col, int height, int width, unsigned short color)
{
	/*int r, c;
	for(r=0; r<height; r++)
	{
		for(c=0; c<width; c++)
		{
			setPixel(row+r, col+c, color);
		}
	}*/
	int r;
    for(r=0; r<height; r++)
    {
        DMA[3].src = &color;
        DMA[3].dst = &videoBuffer[OFFSET(row+r, col, 240)];
        DMA[3].cnt = width | DMA_DESTINATION_INCREMENT | DMA_SOURCE_FIXED | DMA_ON;
        
    }
}

void drawImage3(int r, int c, int width, int height, const u16* image)
{
	int h;
	for(h = 0; h < height; h++){
		DMA[3].src = image;
        	DMA[3].dst = videoBuffer + c + 240*(r+h);
        	DMA[3].cnt = (width) | DMA_ON;
	}
}

