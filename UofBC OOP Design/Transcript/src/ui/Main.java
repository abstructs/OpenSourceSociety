package ui;

import model.Transcript;

public class Main {
    public static void main(String[] args) {
        Transcript t1 = new Transcript("Jane Doe", 7830);
        Transcript t2 = new Transcript("Ada Lovelace", 8853);
        Transcript t3 = new Transcript("Natalie Dorman", 7766);
        double grade1 = 3.5;
        double grade2 = 3.5;
        double grade3 = 3.5;

        t1.addGrade("CPSC-210", grade1);
        t1.addGrade("ENGL-201", grade2);
        t1.addGrade("CPSC-110", grade3);

        t2.addGrade("CPSC-210", grade1);
        t2.addGrade("ENGL-201", grade2);
        t2.addGrade("CPSC-110", grade3);

        t3.addGrade("CPSC-210", grade1);
        t3.addGrade("ENGL-201", grade2);
        t3.addGrade("CPSC-110", grade3);

        System.out.print(t1.getStudentName() + ": ");
        t1.printTranscript();

        System.out.print(t3.getStudentID().toString() + ": ");
        t1.printTranscript();

        System.out.println(t1.getGPA());

        System.out.println(t1.getCourseAndGrade("CPSC-110"));

        t1.removeCourse("CPSC-110");

        t2.addCourse("CPSC-210", grade1);

        System.out.println(t1.getCourseAndGrade("CPSC-210"));

        t2.printTranscript();

        t2.removeGrade("CPSC-210", grade3);

        System.out.println(t2.getGPA());

        t2.printTranscript();

    }
}
