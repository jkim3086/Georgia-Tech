#include "text.h"
#include "myLib.h"

void drawChar(int row, int col, char ch, volatile unsigned short color)
{
	int r, c;

	for(r=0; r<8; r++)
	{
		for(c=0; c<6; c++)
		{
			if(fontdata_6x8[OFFSET(r, c, 6)+ch*48])
			{
				setPixel(row+r, col+c, color);
			}
		}
	}
}

void drawString(int row, int col, char *str, volatile unsigned short color)
{
	while(*str)
	{
		drawChar(row, col, *str++, color);
		col +=6;
	}
}
