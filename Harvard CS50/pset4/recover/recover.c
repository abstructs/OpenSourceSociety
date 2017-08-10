#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[]) {
    
    
    if (argc != 2) {
        fprintf(stderr, "Usage: ./recover image\n");
        return 1;
    }
    
    int amount_written = 0;
    char *filename = malloc(10 * sizeof(char));
    
    FILE *card = fopen(argv[1], "r");
    FILE *outfile = fopen("000.jpg", "w");
    
    int amount_read = 1;
    unsigned char buffer[511];

    int found_jpeg = 0;
    
    while(amount_read != 0) { // finds start of jpeg sequence
        amount_read = fread(buffer, 1, 512, card);
        
        if(buffer[0] == 0xff && buffer[1] == 0xd8 &&
            buffer[2] == 0xff && (buffer[3] & 0xf0) == 0xe0) { // if jpeg header
            if(found_jpeg == 0) {
                found_jpeg = 1;
            }
            else {
                fclose(outfile);
                snprintf(filename, 10, "%03i.jpg", ++amount_written); // create filenames with 0 padding
                fopen(filename, "w"); // open a new file
            }
        }
        
        if (found_jpeg == 1) {
            fwrite(buffer, 1, amount_read, outfile); // write the file 512 bytes at a time, baby
        }
    }
    
    fclose(outfile);
    fclose(card);
    free(filename);
    return 0;
}