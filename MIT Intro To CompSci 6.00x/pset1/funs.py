# Basic functions, function names describe their purpose

def count_vowels(s):
    vowels = ['a', 'e', 'i', 'o', 'u']
    c = 0
    for letter in s:
        if letter in vowels:
            c += 1

    print('Number of vowels: ' + str(c))

def count_occurances(s):
    word = 'bob'
    c = 0
    for i in range(0, len(s)):
        if s[i:i + len(word)] == word:
            c += 1
            
    print("Number of times " + word + " occurs is: " + str(c))

def longest_substr(s):
    substrArr = []
    curr = ''
    n = len(s)

    for i in range(0, n):
        letter = s[i]  
        if curr == '':
            curr = letter
        elif curr[-1] <= letter:
            curr += letter
            if i == n - 1:
                substrArr.append(curr)
        else:
            substrArr.append(curr)
            curr = letter

    largestSubStr = ''
    for s in substrArr:
        if len(s) > len(largestSubStr):
            largestSubStr = s
        
    print("Longest substring in alphabetical order is: " + largestSubStr)