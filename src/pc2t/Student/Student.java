package pc2t.Student;

import java.util.ArrayList;

import pc2t.Interface.IStudent;

public abstract class Student implements IStudent {
	private static int nextID = 1;
	private int id;
	private String firstName;
	private String lastName;
	private int yearBorn;
	private StudyProgramme studyProgramme;
	private ArrayList<Integer> grades;
	
	public Student(String firstName, String lastName, int yearBorn, StudyProgramme studyProgramme) {
		this.id = nextID++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.yearBorn = yearBorn;
		this.studyProgramme = studyProgramme;
		this.grades = new ArrayList<>();
	}
	
	public enum StudyProgramme {
		IBE,
		TLI
	}
	
	@Override
	public String toString() {
		return "Student:" 		+
				"\n\tID: " 					+ this.id +
				"\n\tStudijni program: " 	+ this.studyProgramme + 
				"\n\tJmeno: " 				+ this.firstName + 
				"\n\tPrijmeni: " 			+ this.lastName + 
				"\n\tRok narozeni: " 		+ this.yearBorn + 
				"\n\tZnamky: " 				+ this.grades + 
				"\n\tStudijni prumer: " 	+ this.calculateGradeAverage();
	}
	
	public abstract String specialAbility();
	
	public static int getNextID() {
		return nextID;
	}

	public static void setNextID(int nextID) {
		Student.nextID = nextID;
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
	
	public int getYearBorn() {
		return yearBorn;
	}

	public void setYearBorn(int yearBorn) {
		this.yearBorn = yearBorn;
	}
	
	public StudyProgramme getStudyProgramme() {
		return studyProgramme;
	}

	public void setStudyProgramme(StudyProgramme studyProgramme) {
		this.studyProgramme = studyProgramme;
	}

	public ArrayList<Integer> getGrades() {
		return grades;
	}
	
	public void setGrades(ArrayList<Integer> grades) {
		this.grades = grades;
	}

	public boolean addGrade(int grade) {
		if (grade > 5 || grade < 1) {
			return false;
		}
		
		this.grades.add(grade);
		return true;
	}
	
	public int getGradeSum() {
		int gradeSum = 0;
		int totalGrades = this.grades.size();
		
		if (totalGrades < 0) {
			return 0;
		}
		
		for (int i = 0; i < totalGrades; i++) {
			gradeSum += this.grades.get(i);
		}
		return gradeSum;	
	}
	
	public float calculateGradeAverage() {
		if (this.grades.size() == 0) {
			return 0;
		}
		return (float) getGradeSum() / this.grades.size();	
	}
}
