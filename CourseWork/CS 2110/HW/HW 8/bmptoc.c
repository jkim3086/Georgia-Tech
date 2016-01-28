// **** JEONGSOO KIM  *****
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <ctype.h>

// This is the array into which you will load the raw data from the file
char data_arr[0x36 + 240 * 160 * 4];

int main(int argc, char *argv[]) {
unsigned int ImHeight = 0;
unsigned int ImWidth = 0;	

	// 1. Make sure the user passed in the correct number of arguments
	if(argc < 2) {
	  printf("please to put a name of image file!\n");
	}

	// 2. Open the file; if it doesn't exist, tell the user and then exit
	FILE* file;
 	file = fopen(argv[1], "r"); //Can I directly put argv[1] here?
 	if(file==NULL) {
	  printf("There is no image file!"); 
          return -1;
	}

	// 3. Read the file into the buffer then close it when you are done
	int check;
	check = fread(data_arr,1,(sizeof(data_arr))/(sizeof(data_arr[0])),file);
	if(check == 0) {
	  printf("fread is weird!\n");
	}
	fclose(file);

	// 4. Get the width and height of the image
	char* getHW = &data_arr[0];
	ImWidth = *((unsigned int *) (getHW + 0x12));
	ImHeight = *((unsigned int *) (getHW + 0x16));

	// 5. Create header file, and write header contents; close i
	FILE* cf1;
	char buffer[100];
	char upper_buffer[100];
	strcpy(buffer,argv[1]);
  	int len = strlen(argv[1]);
	buffer[len - 4] = '\0';
	strcpy(upper_buffer,buffer);
	for(int i =0; upper_buffer[i] != '\0'; i++) {	
	   upper_buffer[i] = toupper(upper_buffer[i]);
	}
	char Hfilename[100];
	strcpy(Hfilename, buffer);
	strcat(Hfilename,".h");     

	cf1 = fopen(Hfilename, "w");
	if(cf1) {
 	  fprintf(cf1, "#define %s_WIDTH %d\n",upper_buffer, ImWidth);
	  fprintf(cf1, "#define %s_HEIGHT %d\n",upper_buffer, ImHeight);
	  fprintf(cf1, "const unsigned short %s_data[%d];\n",buffer, (ImWidth*ImHeight));
	}
	fclose(cf1);


	// 6. Create c file, and write pixel data; close it
	FILE* cf2;
	strcpy(Hfilename, buffer);
        strcat(Hfilename,".c");  

	/*const unsigned short something_data[15750] = {
	  0x04B2, 0x491C, 0x7F02, 0x6912, 0x4BBA, 0x1FD5,
	  0x51BC, 0x43E7, 0x7244, 0x3AA6, 0x6309, 0x038D,
	  };*/

	cf2 = fopen(Hfilename, "w");
        if(cf2) {
          fprintf(cf2, "const unsigned short %s_data[%d] = {\n", buffer, (ImWidth*ImHeight));
	  for(int i = (ImHeight -1); i >= 0; i--) {
                for(int j = 0; j < ImWidth; j++) {
                        unsigned int* pos = (unsigned int*) (0x36 + data_arr + 4*((i*ImWidth) + j));

                        unsigned short blue = (unsigned short) (*pos & 0xFF);
			unsigned short green = (unsigned short) ((*pos >> 8) & 0xFF);
			unsigned short red = (unsigned short) ((*pos >> 16) & 0xFF);

			blue = (blue >> 3);
			green = (green >> 3);
			red = (red >> 3);

			blue = (blue << 10);
			green = (green << 5);

			unsigned short sum = blue | green | red;

			fprintf(cf2, "0x%x, ", sum);
		}
		fprintf(cf2,"\n");
	  }
          fprintf(cf2, "};");
        }
        fclose(cf2);
	
	return 0;
}
