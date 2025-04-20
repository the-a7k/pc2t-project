package pc2t.Interface;

import java.util.ArrayList;

import pc2t.Student.Student;

public interface IDatabase {
	
	public void addStudent(Student newStudent);
	public void setStudents(ArrayList<Student> students);
	public Student findStudent(int id);
	public boolean addStudentGrade(int id, int grade);
	public boolean removeStudent(int id);
	
	public void printStudyProgrammeSortedStudents();
	public void printStudyProgrammeStudentCount();
	public void printStudyProgrammeGradeAverage();
	
	public boolean saveToFile(int id, String filename);
	public Student loadFromFile(String filename);	
	
}
