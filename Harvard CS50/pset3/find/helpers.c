/**
 * helpers.c
 *
 * Helper functions for Problem Set 3.
 */
 
#include <cs50.h>
#include <stdio.h>
#include "helpers.h"

/**
 * Returns true if value is in array of n values, else false.
 */
bool search(int value, int values[], int n)
{   
    int left = 0;
    int right = n - 1;
    int count = 0;
    int middle = (right + left) / 2;
    
    while(count < n) {
        if(value == values[middle]) {
            return true;
        }
        else if(value < values[middle]) {
            right = middle - 1;
        }
        else if(value > values[middle]) {
            left = middle + 1;
        }
        middle = (right + left) / 2;
        count++;
    }
    
    return false;   
}

void merge(int new_array[], int left_array[], int left_count, int right_array[], int right_count) {
    int i = 0, j = 0, k = 0;
    
    while(left_count > i && right_count > j) { // while values still in one array iterate
        if (left_array[i] < right_array[j]) { // add values in sorted ordder
            new_array[k++] = left_array[i++];
        }
        else {
            new_array[k++] = right_array[j++];
        }
    }
    
    while(left_count > i) { // exaugst left array into new array if values left
        new_array[k++] = left_array[i++];
    }
    while(right_count > j) { // same but with right
        new_array[k++] = right_array[j++];
    }
    
    return;
}

/**
 * Sorts array of n values.
 */
void sort(int values[], int n)
{
    // TODO: implement an O(n^2) sorting algorithm
    
    if(n < 2) {
        return; // if array is a single element return
    }
    
    int left_half[n / 2];
    int right_half[(n / 2) + n % 2]; // if n is odd right half will be one size bigger than left half
    int left_size = 0; 
    int right_size = 0;
    
    int index = 0;
    
    
    
    for (int i = 0; i < n / 2; i++) {
        left_half[i] = values[index++]; // create left array
        left_size++;
    }
    
    // if n is odd loop will run an additional time so right half is one larger than left half
    for (int i = 0; i < (n + (n % 2)) / 2; i++) {
        right_half[i] = values[index++]; /// create right array
        right_size++;
    }
    
    sort(left_half, left_size); // call sort again passing in left half 
    sort(right_half, right_size); // now pass in right half
    merge(values, left_half, left_size, right_half, right_size); // combine the two arrays
    
    return;
}

