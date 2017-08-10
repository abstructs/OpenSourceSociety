#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include <ctype.h>

int toupper(int c);

int main(void) {
    string s = get_string();
    int index = 0;
    char initials[10];
    int start = 1;
    
    // some logic for handling the first character in a string because it can be whitespace.
    
    if (s[0] == ' ') { // if first character in string is a space start at first index
        start = 0;
    }
    else {
        initials[0] = s[0]; // otherwise set the first index of our initials array to the first character of the string
        index = 1;
    }
    
    for(int i = start, n = strlen(s) - 1; i < n - 1; i++) { // only iterate to second last in array because we're doing a look ahead
        if (s[i] == ' ' && s[i + 1] != ' ') { // check if character is whitespace and next character is not
            initials[index++] = s[i+1]; 
        }
    }
    
    for (int i = 0; i < index; i++) { // print out the characters of our array as capitals
        printf("%c", toupper(initials[i])); 
    }
    printf("\n");
}