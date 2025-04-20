package pc2t;

import java.sql.Connection;
import java.sql.SQLException;

import pc2t.Query.ConnectDB;
import pc2t.Student.Database;
import pc2t.Student.StudentIBE;
import pc2t.Student.StudentTLI;

public class Main {

	public static void main(String[] args) {
		StudentIBE s1 = new StudentIBE("Jan", "Zebra", 2005);
		StudentTLI s2 = new StudentTLI("Jakub", "Brambora", 1999);
		Database database = new Database();
		
		database.addStudent(s1);
		database.addStudent(s2);

		database.addStudentGrade(1, 1);
		database.addStudentGrade(1, 1);
		database.addStudentGrade(2, 5);
		database.addStudentGrade(2, 3);
			
		//database.removeStudent(1);
		
		database.printStudyProgrammeGradeAverage();
		database.printStudyProgrammeStudentCount();
		database.printStudyProgrammeSortedStudents();
		database.printSpecialAbility(2);
		database.printSpecialAbility(1);
		
	    Connection conn = ConnectDB.connect();
		

	}
}
