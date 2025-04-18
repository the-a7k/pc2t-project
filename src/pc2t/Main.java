package pc2t;

import java.util.ArrayList;
import java.util.Scanner;

import pc2t.IBEStudent.HashVariant;

public class Main {

	public static void main(String[] args) {
		TLIStudent s1 = new TLIStudent("Petr", "Pavel", 2005);
		s1.addGrades(5);
		s1.addGrades(2);
		
		IBEStudent s2 = new IBEStudent("Andrej", "Pavel", 1999);
		
		StudentDatabase database = new StudentDatabase();
		database.addStudent(s1);
		database.addStudent(s2);
		
		database.addStudentGrade(1, 3);
		database.addStudentGrade(1, 3);
		database.addStudentGrade(1, 0);
		database.addStudentGrade(2, 3);
		//database.addStudentGrade(100, 3);
		//database.removeStudent(0);
		
		System.out.println(database.findStudent(1));
		System.out.println(database.findStudent(2));

	}
}
