/**
 * Copies a BMP piece by piece, just because.
 */
       
#include <stdio.h>
#include <stdlib.h>
#include <cs50.h>

#include "bmp.h"

int main(int argc, char *argv[])
{
    // ensure proper usage
    
    if (argc != 4)
    {
        fprintf(stderr, "Usage: ./resize n infile outfile\n");
        return 1;
    }

    // remember filenames
    float resize = atof(argv[1]);
    char *infile = argv[2];
    char *outfile = argv[3];
    
    // open input file 
    FILE *inptr = fopen(infile, "r");
    if (inptr == NULL)
    {
        fprintf(stderr, "Could not open %s.\n", infile);
        return 2;
    }

    // open output file
    FILE *outptr = fopen(outfile, "w");
    if (outptr == NULL)
    {
        fclose(inptr);
        fprintf(stderr, "Could not create %s.\n", outfile);
        return 3;
    }

    // read infile's BITMAPFILEHEADER
    BITMAPFILEHEADER bf;
    fread(&bf, sizeof(BITMAPFILEHEADER), 1, inptr);

    // read infile's BITMAPINFOHEADER
    BITMAPINFOHEADER bi;
    fread(&bi, sizeof(BITMAPINFOHEADER), 1, inptr);

    // ensure infile is (likely) a 24-bit uncompressed BMP 4.0
    if (bf.bfType != 0x4d42 || bf.bfOffBits != 54 || bi.biSize != 40 || 
        bi.biBitCount != 24 || bi.biCompression != 0)
    {
        fclose(outptr);
        fclose(inptr);
        fprintf(stderr, "Unsupported file format.\n");
        return 4;
    }
    
    // create new bitmap headers for output file
    
    BITMAPINFOHEADER out_bi = bi;
    
    BITMAPFILEHEADER out_bf = bf;
    
    
    
    
    out_bi.biWidth = bi.biWidth * resize;
    out_bi.biHeight = bi.biHeight * resize; // resize the height and width of the file
    
    int in_padding = (4 - (bi.biWidth * sizeof(RGBTRIPLE)) % 4) % 4; 
    int out_padding = (4 - (out_bi.biWidth * sizeof(RGBTRIPLE)) % 4) % 4; 
    
    // printf("\nOutpadding: %i\n", out_padding);
    // printf("\nInPadding: %i\n", in_padding);
    
    // printf("\nbiwidth: %i\n", bi.biWidth);
    // printf("\nout_biwidth: %i\n", out_bi.biWidth);
    // return 1;
    // out_bi.biSizeImage = (abs(out_bi.biHeight) * out_bi.biWidth) * sizeof(RGBTRIPLE);
    out_bi.biSizeImage = out_bi.biWidth * abs(out_bi.biHeight) * sizeof(RGBTRIPLE) + out_padding * abs(out_bi.biHeight);
    out_bf.bfSize = out_bi.biSizeImage + sizeof(BITMAPFILEHEADER) + sizeof(BITMAPINFOHEADER);

    // write outfile's BITMAPFILEHEADER
    fwrite(&out_bf, sizeof(BITMAPFILEHEADER), 1, outptr);

    // write outfile's BITMAPINFOHEADER
    fwrite(&out_bi, sizeof(BITMAPINFOHEADER), 1, outptr);

    // determine output padding for scanlines


    // iterate over infile's scanlines
    for (int i = 0, biHeight = abs(bi.biHeight); i < biHeight; i++)
    {
        
        for(int fuckyou = 0; fuckyou < resize; fuckyou++) {
        
            // iterate over pixels in scanline
            for (int j = 0; j < bi.biWidth; j++)
            {
                // temporary storage
                RGBTRIPLE triple;
    
                // read RGB triple from infile
                fread(&triple, sizeof(RGBTRIPLE), 1, inptr);
                
                for(int k = 0; k < resize; k++) { // horizontally write into new file
                    fwrite(&triple, sizeof(RGBTRIPLE), 1, outptr);
                }
                
            }
            
            fseek(inptr, in_padding, SEEK_CUR);
            
            for (int l = 0; l < out_padding; l++) {
                    fputc(0x00, outptr);
            }
            
            fseek(inptr, -(bi.biWidth * sizeof(RGBTRIPLE) + in_padding) , SEEK_CUR);
        }
        
        fseek(inptr, bi.biWidth * sizeof(RGBTRIPLE) + in_padding, SEEK_CUR);
    }

    // close infile
    fclose(inptr);

    // close outfile
    fclose(outptr);

    // success
    return 0;
}
