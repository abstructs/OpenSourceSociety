package model;

import exceptions.CourseFullException;
import exceptions.GPATooLowException;
import exceptions.MissingPrereqException;
import exceptions.NoCoursesTakenException;

import java.util.List;

public class Transcript {

    private String name;
    private int year;
    private int id;
    private double gpa;
    private List<Course> currentCourses;
    private List<Course> pastCourses;

    public Transcript(String studentName, int academicYear, int studentID) {
        // TODO: complete the implementation of this method
    }

    // getters
    public String getName() {
        // TODO: complete the implementation of this method
        return null;
    }

    public int getAcademicYear() {
        // TODO: complete the implementation of this method
        return 0;
    }

    public int getId() {
        // TODO: complete the implementation of this method
        return 0;
    }

    public List<Course> getCurrentCourses() {
        // TODO: complete the implementation of this method
        return null;
    }

    public List<Course> getPastCourses() {
        // TODO: complete the implementation of this method
        return null;
    }

    // REQUIRES: a non-empty list of past courses, i.e. size of pastCourses must be non-zero
    //           use the following formula to convert into a GPA
    //           GPA (4.0 scale) = (total percentage/20) - 1
    //           **Do you need a helper?**
    // EFFECTS: computes cGPA. In this case, we take it to mean that it is the total grades received so far, divided
    //          by the number of past courses taken
    public double computeGPA() throws NoCoursesTakenException {
        // TODO: complete the implementation of this method
        return 0.0;
    }

    // REQUIRES: the GPA computed from the pastCourses field needs to be over 2.6 (out of 4.0 scale)
    // EFFECTS: promotes the student represented by the transcript
    //          to the next academic year if the REQUIRES clause is met, and return true
    //          else return false with no change to the year field
    public boolean promoteStudent() throws GPATooLowException, NoCoursesTakenException {
        // TODO: complete the implementation of this method
        return false;
    }

    // REQUIRES: - check if the course you want to add is already in the list of past courses
    // MODIFIES: this
    // EFFECTS: adds the given course to the list of previously taken courses, given that the REQUIRES clause
    //          is fulfilled, and returns true. If the course is unable to be added, return false
    public boolean addToPastCourses(Course c) {
        //TODO: implement this method
        return false;
    }

    // REQUIRES: this transcript must have all the necessary prerequisites in the pastCourses field
    //           The course you want to add must have space for more students to register
    // MODIFIES: this
    // EFFECTS: adds a course (c) into the record
    public boolean addCourse(Course course) throws MissingPrereqException, CourseFullException {
        // TODO: implement this method.
        // You have to realize that there are a number of cases that your code needs to consider. What if the course
        // you wish to add has no prerequisites? What if the course you want to add is already full?
        // Careful consideration of these cases will lead to code that is correct
        return false;
    }


}