package model;
import java.util.ArrayList;

public class Transcript {
    /**
     * INVARIANT: course list and grade list are the same size
     * each course has a grade associated, and vice versa, at matching indices
     */

    private String studentName;
    private Integer studentID;
    private ArrayList<String> courses;
    private ArrayList<Double> grades;

    public Transcript(String name, Integer id) {
        this.studentName = name;
        this.studentID = id;
        courses = new ArrayList<>();
        grades = new ArrayList<>();
    }

    // getters

    public Integer getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public ArrayList<String> getCourses() { return courses; }

    // REQUIRES: course should have been added
    public double getGrade(String course) { return grades.get(courseIndex(course)); }

    public ArrayList<Double> getGrades() { return grades; }

    public Boolean containsCourse(String course) { return courses.contains(course); }

    public Boolean containsGrade(double grade) { return grades.contains(grade); }

    // REQUIRES: course should have been added, if not creates a new course with the grade
    // MODIFIES: this
    // EFFECTS: modifies the grade for a course
    public void addGrade(String course, double grade) {
        int i;
        try {
            i = courseIndex(course);
        } catch (RuntimeException e) {
            addCourse(course, grade);
            return;
        }

        grades.set(i, grade);
    }

    // REQUIRES: course, grade
    // MODIFIES: this
    // EFFECTS: removes a grade
    public void removeGrade(String course, double grade){ grades.set(courseIndex(course), 0.0); }

    // REQUIRES: course
    // MODIFIES: nothing
    // EFFECTS: returns course name and grade in some consistent String format
    public String getCourseAndGrade(String course){ return null; }

    // REQUIRES: Course should not have been added to the transcript before!
    // MODIFIES: this
    // EFFECTS: adds a course
    public void addCourse(String course, double grade){
        if (courses.contains(course)) { return; }
        courses.add(course);
        grades.add(grade);
    }

    // REQUIRES: course
    // MODIFIES: this
    // EFFECTS: removes a course and the corresponding grade
    public void removeCourse(String course){
        int i;
        try {
            i = courseIndex(course);
        } catch (RuntimeException e) {
            return;
        }
        courses.remove(i);
        grades.remove(i);
    }

    // REQUIRES: course must be in the array list otherwise error is thrown
    // EFFECTS: finds the index of a course in the courses array
    private int courseIndex(String course) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).equals(course)) {
                return i;
            }
        }

        throw new RuntimeException(course + ": is not in the <ArrayList>.");
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints out the transcript
    public void printTranscript(){ }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the GPA of a student
    public double getGPA(){ return 0.0; }
}
