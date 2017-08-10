#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include <ctype.h>

char *encrypt(char *word, char *key);

int main(int argc, string argv[]) {
    
    if (argc > 2 || argc <= 1) {
        printf("Usage: ./vigenere k\n");
        return 1;
    }
    
    for(int i = 0, n = strlen(argv[1]); i < n; i++) {
        if (isdigit(argv[1][i])) {
            printf("Usage: ./vigenere k\n");
            return 1;
        }
    }
    
    
    char *word;
    char *encrypted_word;
    char *key = argv[1];
    // char *key = "baz";
    printf("plaintext: ");
    word = get_string();
    
    encrypted_word = encrypt(word, key);
    
    
    printf("ciphertext: %s\n", encrypted_word);
    
    return 0;
}

char *encrypt(char *word, char *key) {
    char *new_word = malloc(128);
    int i = 0;
    int key_length = strlen(key);
    int key_value = 0;
    
    for (int j = 0, n = strlen(word); j < n; j++) {
        if ( isalpha(word[j]) ) {
            
            if (isupper(key[i])) { // handle getting the alphabetical value of the key
                key_value = key[i] - 'A';
                i++;
            }
            else if (islower(key[i])) {
                key_value = key[i] - 'a';
                i++;
            }
            
            if ( isupper(word[j]) ) { // apply the shift
                new_word[j] = ((word[j] - 'A') + key_value) % 26 + 'A';
            }
            else if ( islower(word[j]) ) {
                new_word[j] = ((word[j] - 'a') + key_value) % 26 + 'a';
            }
        }
        else {
            new_word[j] = word[j]; // leave non alphabetical values unchanged
        }
        
        if (i == key_length) i = 0; // if we reach the end of the key, restart
        
    }
    
    return new_word;
}