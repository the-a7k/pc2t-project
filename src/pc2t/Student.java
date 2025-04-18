package pc2t;

import java.util.ArrayList;

public abstract class Student {
	private static int nextID = 1;
	private int id;
	private String firstName;
	private String lastName;
	private int yearBorn;
	private ArrayList<Integer> grades;
	
	public Student(String firstName, String lastName, int yearBorn) {
		this.id = nextID++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.yearBorn = yearBorn;
		this.grades = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "Student Type: " + this.getClass().getSimpleName() + 
				"\nStudent Detail:" 	+
				"\n\tID: " 				+ this.id + 
				" \n\tFirst Name: " 	+ this.firstName + 
				" \n\tLast Name: " 		+ this.lastName + 
				"\n\tYear born: " 		+ this.yearBorn + 
				"\n\tGrades: " 			+ this.grades + 
				"\n\tGrade average: " 	+ this.calculateGradeAverage();
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return new StringBuilder()
				.append(this.getFirstName())
				.append(" ")
				.append(this.getLastName())
				.toString();
	}

	public ArrayList<Integer> getGrades() {
		return grades;
	}

	public boolean addGrades(int grade) {
		if (grade > 5 || grade < 1) {
			return false;
		}
		
		this.grades.add(grade);
		return true;
	}
	
	public float calculateGradeAverage() {
		Integer gradeSum = 0;
		Integer totalGrades = this.grades.size();
		
		if (totalGrades < 0) {
			return 0;
		}
		
		for (int i = 0; i < totalGrades; i++) {
			gradeSum += this.grades.get(i);
		}
		return (float) gradeSum / totalGrades;	
	}
}
