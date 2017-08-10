#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

int atoi(const char *str);

char *encrypt(char *word, int key);

int main(int argc, string argv[]) {
    if (argc > 2 || argc <= 1) {
        printf("Usage: ./caesar k\n");
        return 1;
    }
    printf("plaintext: ");
    char *word = get_string();
    
    int key = atoi(argv[1]);
    char *encrypted_word;
    
    encrypted_word = encrypt(word, key);

    printf("ciphertext: %s\n", encrypted_word);
    
    
    return 0;
}

char *encrypt(char *word, int key) {
    char *new_word = malloc(128); // allocate memory for chars
    
    for(int i = 0, n = strlen(word); i < n; i++) { // iterate over word
        if( isalpha(word[i]) ) {
            if ( isupper(word[i]) ) {
                new_word[i] = ((word[i] - 'A' + key) % 26) + 'A';
            }
            else if ( islower(word[i]) ) {
                new_word[i] = ((word[i] - 'a' + key) % 26) + 'a';
            }
        }
        else {
            new_word[i] = word[i];
        }
    }
    
    return new_word;
}