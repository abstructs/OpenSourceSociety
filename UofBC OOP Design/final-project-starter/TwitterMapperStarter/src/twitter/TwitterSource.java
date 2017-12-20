package twitter;

import twitter4j.Status;
import util.ImageCache;

import java.util.*;


public abstract class TwitterSource extends Observable {
    protected boolean doLogging = true;
    // The set of terms to look for in the stream of tweets
    protected Set<String> terms = new HashSet<>();

    // Called each time a new set of filter terms has been established
    abstract protected void sync();

    protected void log(Status status) {
        if (doLogging) {
            System.out.println(status.getUser().getName() + ": " + status.getText());
        }
        ImageCache.getInstance().loadImage(status.getUser().getProfileImageURL());
    }

    public void setFilterTerms(Collection<String> newterms) {
        terms.clear();
        terms.addAll(newterms);
        sync();
    }

    public List<String> getFilterTerms() {
        return new ArrayList<>(terms);
    }

    // This method is called each time a tweet is delivered to the application.
    // TODO: Each active query should be informed about each incoming tweet so that
    //       it can determine whether the tweet should be displayed
    protected void handleTweet(Status s) {
        setChanged();
        notifyObservers(s);
    }
}
