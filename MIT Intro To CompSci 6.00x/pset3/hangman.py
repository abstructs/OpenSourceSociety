"""
In this problem, you will implement a function, called hangman, 
that will start up and carry out an interactive Hangman game 
between a player and the computer.
"""

import string

def getAvailableLetters(lettersGuessed):
    '''
    lettersGuessed: list, what letters have been guessed so far
    returns: string, comprised of letters that represents what letters have not
      yet been guessed.
    '''
    # FILL IN YOUR CODE HERE...
    newStr = ''
    for letter in string.ascii_lowercase:
        if not(letter in lettersGuessed):
            newStr += letter
    return newStr

def getGuessedWord(secretWord, lettersGuessed):
    '''
    secretWord: string, the word the user is guessing
    lettersGuessed: list, what letters have been guessed so far
    returns: string, comprised of letters and underscores that represents
      what letters in secretWord have been guessed so far.
    '''
    # FILL IN YOUR CODE HERE...
    newStr = ''
    for letter in secretWord:
        if not(letter in lettersGuessed):
            newStr += '_'
        else:
            newStr += letter
    return newStr

def isWordGuessed(secretWord, lettersGuessed):
    '''
    secretWord: string, the word the user is guessing
    lettersGuessed: list, what letters have been guessed so far
    returns: boolean, True if all the letters of secretWord are in lettersGuessed;
      False otherwise
    '''
    for letter in secretWord:
        if not(letter in lettersGuessed):
            return False
    return True


def hangman(secretWord):
    '''
    secretWord: string, the secret word to guess.

    Starts up an interactive game of Hangman.

    * At the start of the game, let the user know how many 
      letters the secretWord contains.

    * Ask the user to supply one guess (i.e. letter) per round.

    * The user should receive feedback immediately after each guess 
      about whether their guess appears in the computers word.

    * After each round, you should also display to the user the 
      partially guessed word so far, as well as letters that the 
      user has not yet guessed.

    Follows the other limitations detailed in the problem write-up.
    '''
    # FILL IN YOUR CODE HERE...
    guessed = 0
    lettersGuessed = []

    print("Welcome to the game, Hangman!")
    print("I am thinking of a word that is " + str(len(secretWord)) + " letters long.")

    while guessed < 8:
      print("-------------")
      print("You have " + str(8 - guessed) + " guesses left.")
      print("Available Letters: " + getAvailableLetters(lettersGuessed))
      guessedLetter = input("Please guess a letter: ")
      if guessedLetter in lettersGuessed:
        print("Oops! You've already guessed that letter: " + getGuessedWord(secretWord, lettersGuessed))
        continue
      if guessedLetter in secretWord:
        lettersGuessed.append(guessedLetter)
        print("Good guess: " + getGuessedWord(secretWord, lettersGuessed))
        if isWordGuessed(secretWord, lettersGuessed) == True:
          print("-------------")
          print("Congratulations, you won!")
          break
      else:
        lettersGuessed.append(guessedLetter)
        print("Oops! That letter is not in my word: " + getGuessedWord(secretWord, lettersGuessed))
        guessed += 1
        if guessed == 8:
          print("-------------")
          print("Sorry, you ran out of guesses. The word was " + secretWord)
          break

hangman("zebras")