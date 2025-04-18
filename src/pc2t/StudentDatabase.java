package pc2t;

import java.util.ArrayList;

public class StudentDatabase {
	private ArrayList<Student> students;

	public StudentDatabase() {
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
		return student.addGrades(grade);
	}
	
	public boolean removeStudent(int id) {
		Student student = findStudent(id);
		if (student == null) {
			return false;
		}
		return this.students.remove(student);
	}

}
