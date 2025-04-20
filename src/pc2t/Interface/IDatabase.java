package pc2t.Interface;

import pc2t.Student.Student;

public interface IDatabase {
	
	public void addStudent(Student newStudent);
	public Student findStudent(int id);
	public boolean addStudentGrade(int id, int grade);
	public boolean removeStudent(int id);
	
	public void printSpecialAbility(int id);
	public void printStudyProgrammeSortedStudents();
	public void printStudyProgrammeStudentCount();
	public void printStudyProgrammeGradeAverage();
}
