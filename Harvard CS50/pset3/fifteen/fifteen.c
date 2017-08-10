/**
 * fifteen.c
 *
 * Implements Game of Fifteen (generalized to d x d).
 *
 * Usage: fifteen d
 *
 * whereby the board's dimensions are to be d x d,
 * where d must be in [DIM_MIN,DIM_MAX]
 *
 * Note that usleep is obsolete, but it offers more granularity than
 * sleep and is simpler to use than nanosleep; `man usleep` for more.
 */
 
#define _XOPEN_SOURCE 500

#include <cs50.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// constants
#define DIM_MIN 3
#define DIM_MAX 9

// dimensions
int d;

// prototypes
void clear(void);
void greet(void);
void init(int *arr[], int n);
void draw(int *arr[], int n);
bool move(int tile, int *arr[], int n);
bool won(int *arr[], int n);

// index of zero
int zero_row;
int zero_column;

int main(int argc, string argv[])
{
    // ensure proper usage
    if (argc != 2)
    {
        printf("Usage: fifteen d\n");
        return 1;
    }

    // ensure valid dimensions
    d = atoi(argv[1]);
    if (d < DIM_MIN || d > DIM_MAX)
    {
        printf("Board must be between %i x %i and %i x %i, inclusive.\n",
            DIM_MIN, DIM_MIN, DIM_MAX, DIM_MAX);
        return 2;
    }

    // open log
    FILE *file = fopen("log.txt", "w");
    if (file == NULL)
    {
        return 3;
    }

    // greet user with instructions
    greet();

    // initialize the board
    
    int *arr[d]; // board
    
    init(arr, d);
    
    
    usleep(5000);
    // accept moves until game is won
    while (true)
    {
        // clear the screen
        clear();

        // draw the current state of the board
        draw(arr, d);

        // log the current state of the board (for testing)
        for (int i = 0; i < d; i++)
        {
            for (int j = 0; j < d; j++)
            {
                fprintf(file, "%i", arr[i][j]);
                if (j < d - 1)
                {
                    fprintf(file, "|");
                }
            }
            fprintf(file, "\n");
        }
        fflush(file);

        // check for win
        if (won(arr, d))
        {
            printf("ftw!\n");
            break;
        }

        // prompt for move
        printf("Tile to move: ");
        int tile = get_int();
        
        // quit if user inputs 0 (for testing)
        if (tile == 0)
        {
            break;
        }

        // log move (for testing)
        fprintf(file, "%i\n", tile);
        fflush(file);

        // move if possible, else report illegality
        if (!move(tile, arr, d))
        {
            printf("\nIllegal move.\n");
            usleep(500000);
        }

        // sleep thread for animation's sake
        usleep(500000);
    }
    
    // close log
    fclose(file);

    // success
    return 0;
}

/**
 * Clears screen using ANSI escape sequences.
 */
void clear(void)
{
    printf("\033[2J");
    printf("\033[%d;%dH", 0, 0);
}

/**
 * Greets player.
 */
void greet(void)
{
    clear();
    printf("WELCOME TO GAME OF FIFTEEN\n");
    // usleep(2000000);
}

/**
 * Initializes the game's board with tiles numbered 1 through d*d - 1
 * (i.e., fills 2D array with values but does not actually print them).  
 */
void init(int *arr[], int n)
{
    int value = (n*n) - 1;
    
    for(int i = 0; i < n; i++) {
        arr[i] = malloc(sizeof(int) * n);
        for(int j = 0; j < n; j++) {
            arr[i][j] = value--; 
        }
    }
    
    if (n % 2 == 0) {
        arr[n - 1][n - 3] = 1;  // swap 1 and 2 if width of array is even
        arr[n - 1][n - 2] = 2; 
    }
    
    zero_row = zero_column = n - 1; // initialize value of zero globally
    
    return;
}

/**
 * Prints the board in its current state.
 */
void draw(int *arr[], int n)
{
    for(int i = 0; i < n; i++) {
        printf("\n");
        for(int j = 0; j < n; j++) {
            if (arr[i][j] <= 9) {
                printf("%2i\t", arr[i][j]);
            }
            else {
                printf("%i\t", arr[i][j]);
            }
        }
        printf("\n");
    }
    
    return;
}

/**
 * If tile borders empty space, moves tile and returns true, else
 * returns false. 
 */
bool move(int tile, int *arr[], int n)
{
    if (tile < 0 && tile >= n) {
        return false; // be sure tile is a valid value
    }
    
    // logic for finding tiles around zero
    // checks if number is within array range before checking array at the index
    bool above = zero_row - 1 >= 0 && arr[zero_row - 1][zero_column] == tile;
    bool below = zero_row + 1 < n && arr[zero_row + 1][zero_column] == tile;
    bool left = zero_column - 1 >= 0 && arr[zero_row][zero_column - 1] == tile;
    bool right = zero_column  + 1 < n && arr[zero_row][zero_column + 1] == tile;
    
    // check tiles surrounding zero for tile, if found swap zero and tile
    if (above) {
        arr[zero_row - 1][zero_column] = 0; // swap zero and tile, change zero location globally
        arr[zero_row][zero_column] = tile;
        zero_row--;
        return true;
    }
    else if (below) { // TODO: Fix bug for below
        arr[zero_row + 1][zero_column] = 0;
        arr[zero_row][zero_column] = tile;
        zero_row++;
        return true;
    }
    else if (left) {
        arr[zero_row][zero_column - 1] = 0;
        arr[zero_row][zero_column] = tile;
        zero_column--;
        return true; 
    }
    else if (right) {
        arr[zero_row][zero_column + 1] = 0;
        arr[zero_row][zero_column] = tile;
        zero_column++;
        return true;
    }
    // printf("Row: %i Column: %i\n", zero_row, zero_column); debugging printf
    // returns false if tile isn't surrounding zero
    
    return false;
}

/**
 * Returns true if game is won (i.e., board is in winning configuration), 
 * else false.
 */
bool won(int *arr[], int n)
{
    int current = 0;
    for(int i = 0; i < n; i++) {
        for(int j = 0; j < n; j++) {
            if(arr[i][j] > current) current = arr[i][j];
            else if(arr[i][j] == 0) {
                if (i == n - 1 && j == n - 1){
                    return true; // check if 0 is the last element of the array
                } 
            }
            else return false; // return false if array is out of order and 0 is not last num
        }
    }
    return false; // base case return false
}
