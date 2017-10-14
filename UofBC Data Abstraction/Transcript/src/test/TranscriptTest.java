package test;

import model.Transcript;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TranscriptTest {
    private Transcript testTranscript;

    @Before
    public void setUp(){
        testTranscript = new Transcript("Andrew Wilson", 1312);
        //TODO: write new values in testTranscript constructor
//        testTranscript.addCourse("Object Oriented Design", 4.0);
//        testTranscript.addCourse("Algorithms", 4.0);
//        testTranscript.addCourse("Linear Algebra", 4.0);
//        testTranscript.addCourse("Social Life: 101", 1.0);
    }

    @Test
    public void testAddCourse() {
        testTranscript.addCourse("Object Oriented Design", 4.0);
        assertTrue(testTranscript.getCourses().size() == 1);
        testTranscript.addCourse("Object Oriented Design", 4.0);
        assertTrue(testTranscript.getCourses().size() == 1);
        assertTrue(testTranscript.containsCourse("Object Oriented Design"));
    }

    @Test
    public void testRemoveCourse() {
        testTranscript.addCourse("Algorithms", 4.0);
        assertTrue(testTranscript.getCourses().size() == 1);
        testTranscript.removeCourse("Algorithms");
        assertTrue(testTranscript.getCourses().size() == 0);
    }

    @Test
    public void testAddGrade() {
        testTranscript.addCourse("Object Oriented Design", 4.0);
        testTranscript.addGrade("Object Oriented Design", 3.0);
        assertTrue(testTranscript.getGrade("Object Oriented Design") == 3.0);
        testTranscript.addGrade("Not Real Course", 3.0);
        assertTrue(testTranscript.getGrades().size() == 2);
        assertTrue(testTranscript.containsCourse("Not Real Course"));
    }

    @Test
    public void testRemoveGrade() {
        testTranscript.addCourse("Object Oriented Design", 4.0);
        testTranscript.removeGrade("Object Oriented Design", 4.0);
        assertTrue(testTranscript.getGrade("Object Oriented Design") == 0);
        assertTrue(testTranscript.getGrades().size() == 0);
    }
}
