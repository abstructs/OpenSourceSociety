package model;

public class Transcript {
    String studentName;
    Integer studentID;
    String[] courses;
    int[] grades;

    public Transcript(String name, Integer id) {
        this.studentName = name;
        this.studentID = id;
    }

    // getters

    public Integer getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String[] getCourses() { return courses; }

    public int getGrade(String course) { return 0; }

    public int[] getGrades() { return grades; }

    public Boolean containsCourse() { return false; }

    public Boolean containsGrade() { return false; }

    // REQUIRES: course, grade
    // MODIFIES: this
    // EFFECTS: adds a grade
    public void addGrade(String course, double grade){ }

    // REQUIRES: course, grade
    // MODIFIES: this
    // EFFECTS: removes a grade
    public void removeGrade(String course, double grade){ }

    // REQUIRES: course
    // MODIFIES: nothing
    // EFFECTS: returns course name and grade in some consistent String format
    public String getCourseAndGrade(String course){ return null; }

    // REQUIRES: course
    // MODIFIES: this
    // EFFECTS: adds a course
    public void addCourse(String course, double grade){ }

    // REQUIRES: course
    // MODIFIES: this
    // EFFECTS: removes a course
    public void removeCourse(String course){ }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints out the transcript
    public void printTranscript(){ }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the GPA of a student
    public double getGPA(){ return 0.0; }
}
