#include <stdio.h>
#include <cs50.h>
#include <unistd.h>
#include <string.h>
#define _XOPEN_SOURCE

char *crypt(const char *key, const char *salt);
char *swap(char *word, int pos1, int pos2);

int main(void) {
    // char *password = crypt("key", "aa");
    char *alphabet = "ABC";
    
    // char *new_password = crypt("key", password);
    
    // bool not_found = true;
    // int pos = 0;
    // while (!not_found) {
    //     char *guess = alphabet[pos]
    // }
    
    // printf("%s\n %s\n", password, new_password);
    
    alphabet = swap(alphabet, 0, 1);
    
    printf("%s\n", alphabet);
}

char *swap(char *word, int pos1, int pos2) {
    int answer = pos1 + pos2;
    char word[128];
    
    strcpy(word, "WTF");
    
    printf("%s\n", word);
    
    printf("%i\n", answer);
    return word;
}