package model;
import java.util.List;

public class Registrar {

    private String name;
    private List<Transcript> students;

    public Registrar(String name) {
        // TODO: complete the implementation of this method
    }

    // getters
    public String getName() {
        // TODO: complete the implementation of this method
        return null;
    }
    public List<Transcript> getStudents() {
        // TODO: complete the implementation of this method
        return null;
    }

    // MODIFIES: this
    // EFFECTS: returns true if the student (represented as a transcript) was successfully added to the
    //          Registrar's list. Remember to check if the student already exists in the list
    public boolean addStudent(Transcript stu) {
        // TODO: complete the implementation of this method
        return false;
    }

    // EFFECTS: registers a given student represented by tct to a course c. It should throw
    //          CourseFullException or MissingPrereqException
    public boolean registerStudent(Course c, Transcript tct) {
        // TODO: complete the implementation of this method
        return false;
    }

    // EFFECTS: promotes all students to their next year level. It should throw GPATooLowException or
    //          NoCoursesTakenException
    public void promoteAllStudents() {
        // TODO: complete the implementation of this method
    }


}