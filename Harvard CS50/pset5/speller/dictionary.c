/**
 * Implements a dictionary's functionality.
 */
#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>
#include "dictionary.h"
#include <stdlib.h>
/**
 * Returns true if word is in dictionary else false.
 */
 
node *root;
int dic_size;

bool check(const char *word)
{
    if(strlen(word) > 45) {
        return false;
    }
    
    node *trav = root;
    int index;
    
    int i = 0;
    
    while(isalpha(word[i]) || word[i] == '\'') {
        if(word[i] == '\'') {
            index = 26;
        }
        else {
            index = tolower(word[i]) - 'a';
        }
        
        if(trav->children[index] == NULL) {
            return false;
        }
        
        trav = trav->children[index];    
        
        i++;
    }
    
    
    if(trav->is_word) {
        return true;
    }
    
    return false;
}

/**
 * Loads dictionary into memory. Returns true if successful else false.
 */
bool load(const char *dictionary)
{
    
    FILE *dic = fopen(dictionary, "r");
    
    if(dic == NULL) {
        printf("Could not open %s.\n", dictionary);
        unload();
        return false;
    }
    
    root = calloc(1, sizeof(node));
    char str[45];
    int index;
    dic_size = 0;
    node *trav;
    int i = 0;
    
    while(fscanf(dic, "%s", str) != EOF) {
        trav = root;
        dic_size++;
        int length = strlen(str);
        while(i < length) {
            
            if(str[i] == '\'') {
                index = 26;
            }
            else {
                index = str[i] - 'a';
            }
            
            if(trav->children[index] == NULL) {
                node *new_node = calloc(1, sizeof(node));
                trav->children[index] = new_node;
            }
            
            trav = trav->children[index];
            i++;
        }
        
        trav->is_word = true;
        i = 0;
    }
    
    fclose(dic);
    
    return true;
}

/**
 * Returns number of words in dictionary if loaded else 0 if not yet loaded.
 */
unsigned int size(void)
{
    return dic_size;
}

/**
 * Unloads dictionary from memory. Returns true if successful else false.
 */
bool unload(void)
{
    free_nodes(root);
    free(root);
    return true;
}

bool free_nodes(node *head) {
    
    for(int i = 0; i <= 26; i++) {
        if(head->children[i] != NULL) {
            free_nodes(head->children[i]);
            free(head->children[i]);
        }
    }
    
    return true;
}