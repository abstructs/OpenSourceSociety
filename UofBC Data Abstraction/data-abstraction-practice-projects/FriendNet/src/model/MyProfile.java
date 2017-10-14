package model;

import java.util.List;

public class MyProfile {

    private String name;
    private int age;
    private String currentLocation;
    private String workPlace;
    private List<MyProfile> friendsList;
    private List<Event> upcomingEvents;

    public MyProfile(String nm, int age, String locn, String work) {
        // TODO: complete the implementation of this method
    }

    // getters
    public String getName() {
        // TODO: complete the implementation of this method
        return null;
    }
    public int getAge() {
        // TODO: complete the implementation of this method
        return 0;
    }
    public String getCurrentLocation() {
        // TODO: complete the implementation of this method
        return null;
    }
    public String getWorkPlace() {
        // TODO: complete the implementation of this method
        return null;

    }
    public int upcomingEventNum() {
        // TODO: complete the implementation of this method
        return 0;

    }
    public List<MyProfile> getFriendsList() {
        // TODO: complete the implementation of this method
        return null;
    }
    public List<Event> getEventList() {
        // TODO: complete the implementation of this method
        return null;
    }

    // REQUIRES: f is not already in friendsList
    // MODIFIES: this
    // EFFECTS: consumes a MyProfile object, a friend f, and adds it to the friendsList
    public void addFriend(MyProfile f) {
        // TODO: complete the implementation of this method
    }

    // MODIFIES: this
    // EFFECTS: removes a friend with the given name from this. If removal is successful, return true, else
    //          return false
    public boolean unFriend(String nm) {
        // TODO: complete the implementation of this method
        return false;
    }

    // REQUIRES: ev is not in upcomingEvents
    // MODIFIES: this
    // EFFECTS: adds the given event to the list of upcoming events
    public void addEvent(Event ev) {
        // TODO: complete the implementation of this method
    }

    // MODIFIES: this
    // EFFECTS: removes an event with the given name. If removal is successful, return true, else return false
    public boolean removeEvent(String nm) {
        // TODO: complete the implementation of this method
        return false;
    }

    // EFFECTS: returns the number of events that are at the current location of this person
    public int eventNumNearMe() {
        // TODO: complete the implementation of this method
        return 0;
    }

    // EFFECTS: returns the number of events of the given type et
    public int specificEventType(EventType et) {
        // TODO: complete the implementation of this method
        return 0;
    }

    // EFFECTS: prints events of  type et to the console
    //          Hint: is there a method you have already written that you can use?
    public void printEventSchedule(EventType et) {
        // TODO: complete the implementation of this method
    }

    // EFFECTS: prints out the list of friends that this MyProfile has
    public void printFriendNames() {
        // TODO: complete the implementation of this method
    }

    // EFFECTS: prints out the names of friends who live at the same location associated with this profile
    public void printFriendsNearMe() {
        // TODO: complete the implementation of this method
    }

    // EFFECTS: produces true if this profile has a friend with the given name,
    //          OR if any of this profile's friends has a friend with the given name
    //          Hint: use recursion!
    public boolean canFindPerson(String name) {
        // TODO: complete the implementation of this method
        return false;
    }


}
