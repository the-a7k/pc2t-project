package pc2t.Student;

import java.util.ArrayList;
import java.util.Comparator;

import pc2t.Interface.IDatabase;
import pc2t.Student.Student.StudyProgramme;

public class Database implements IDatabase {
	private ArrayList<Student> students;

	public Database() {
		this.students = new ArrayList<>();
	}
	
	public void addStudent(Student newStudent) {
		this.students.add(newStudent);
	}
	
	public Student findStudent(int id) {
		for (int i = 0; i < this.students.size(); i++) {
			if (this.students.get(i).getID() == id) {
				return this.students.get(i);
			}
		}
		return null;
	}
	
	public boolean addStudentGrade(int id, int grade) {
		Student student = findStudent(id);
		if (student == null) {
			return false;
		}
		return student.addGrade(grade);
	}
	
	public boolean removeStudent(int id) {
		Student student = findStudent(id);
		if (student == null) {
			return false;
		}
		return this.students.remove(student);
	}
	
	public void printSpecialAbility(int id) {
		Student s = findStudent(id);
		if (s == null) {
			System.out.println("Error: Cannot print special ability, student with this ID does not exist.");
		}
		else {
			System.out.println("Special ability of the student " + s.getFullName() + " from the study programme " + s.getStudyProgramme() + ": " + s.specialAbility());
		}
	}
	
	public void printStudyProgrammeSortedStudents() {
		students.sort(Comparator.comparing(Student::getLastName));

        for (StudyProgramme sp : StudyProgramme.values()) {
        	int programmeStudentCount = 0;
        	System.out.println("Showing all students from study programme " + sp + ":");
        	
	        for (Student s : this.students) {
				if (s.getStudyProgramme() != sp) {
					continue;
				}
				programmeStudentCount++;
	            System.out.println(s);
	        }
	        
	        if (programmeStudentCount == 0) {
	        	System.out.println("\tThis study programme (" + sp + ") has no students.");
	        }
        }
        
        students.sort(Comparator.comparing(Student::getID));
	}
	
	public void printStudyProgrammeStudentCount() {
		
		for (StudyProgramme sp : StudyProgramme.values()) {
			int programmeStudentCount = 0;
			
			for (int i = 0; i < this.students.size(); i++) {
				if (this.students.get(i).getStudyProgramme() != sp) {
					continue;
				}
				programmeStudentCount++;			
			}
			
			System.out.println("Total student count from study programme " + sp + ": " + programmeStudentCount);
		}
	}
	
	public void printStudyProgrammeGradeAverage() {		
		for (StudyProgramme sp : StudyProgramme.values()) {
			int totalGradeCount = 0;
			int totalGradeSum = 0;
			
			for (int i = 0; i < this.students.size(); i++) {
				Student currentStudent = this.students.get(i);
				
				if (currentStudent.getStudyProgramme() != sp) {
					continue;
				}
				totalGradeSum += currentStudent.getGradeSum();
				totalGradeCount += currentStudent.getGrades().size();
			}
			
			if (totalGradeCount == 0 || totalGradeSum == 0) {
				System.out.println("Total grade average from study programme " + sp + ": " + 0);
			}
			
			else {
				System.out.println("Total grade average from study programme " + sp + ": " + (float)totalGradeSum / totalGradeCount);
			}
		}
	}
}
