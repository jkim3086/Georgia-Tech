
extern unsigned char fontdata_6x8[12288];

void drawChar(int row, int col, char ch, volatile unsigned short color);
void drawString(int row, int col, char *str, volatile unsigned short color);
