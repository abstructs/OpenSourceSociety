import nltk

class Analyzer():
    """Implements sentiment analysis."""

    def __init__(self, positives, negatives):
        """Initialize Analyzer."""
        # positives = open("positive-words.txt", "r")
        
        # exaugst comment
        self.positives = set()
        self.negatives = set()
        
        # load positive values
        with open('positive-words.txt') as file: 
            for line in file:
                if line.startswith(';'):
                    continue
                
                self.positives.add(line.strip('\n'))
                
        with open('negative-words.txt') as file: 
            for line in file:
                if line.startswith(';'):
                    continue
                
                self.negatives.add(line.strip('\n'))

    def analyze(self, text):
        """Analyze text for sentiment, returning its score."""

        if text.lower() in self.positives:
            return 1
        elif text.lower() in self.negatives:
            return -1
        else:
            return 0
