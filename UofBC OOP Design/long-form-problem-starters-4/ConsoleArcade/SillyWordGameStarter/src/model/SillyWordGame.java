package model;

import java.util.List;
import java.util.Iterator;

public class SillyWordGame implements Iterable<Phrase> {
    private List<Phrase> phrases;

    private class SillyIterator implements Iterator<Phrase> {
        private int currentPhraseIndex;
        private int length = phrases.size();
        private int numWordsNeeded;

        public SillyIterator() {
            for(Phrase p : phrases) {
                if (p.needsWord())
                    numWordsNeeded++;
            }
        }

        @Override
        public boolean hasNext() {
            return numWordsNeeded > 0;
        }

        @Override
        public Phrase next() {
            for(Phrase p : phrases) {
                if(!phrases.get(currentPhraseIndex).needsWord()) {
                    currentPhraseIndex++;
                } else {
                    numWordsNeeded--;
                    return phrases.get(currentPhraseIndex++);
                }
            }

            throw new IllegalStateException();
        }
    }

    public SillyWordGame(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    //EFFECTS: returns all phrases in this game
    public List<Phrase> getAllPhrases() {
        return phrases;
    }

    @Override
    public Iterator<Phrase> iterator() {
        return new SillyIterator();
    }
}
