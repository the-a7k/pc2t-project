package pc2t.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import pc2t.Interface.IDatabase;
import pc2t.Student.Student.StudyProgramme;

public class Database implements IDatabase {
	private ArrayList<Student> students;

	public Database() {
		this.students = new ArrayList<>();
	}
	
	public Student findStudent(int id) {
		for (int i = 0; i < this.students.size(); i++) {
			if (this.students.get(i).getID() == id) {
				return this.students.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public void addStudent(Student newStudent) {
		this.students.add(newStudent);
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
	
	public void printStudyProgrammeSortedStudents() {
		students.sort(Comparator.comparing(Student::getLastName));

        for (StudyProgramme sp : StudyProgramme.values()) {
        	int programmeStudentCount = 0;
        	System.out.println("Zobrazeni vsech studentu ze studijniho programu: " + sp + ":");
        	
	        for (Student s : this.students) {
				if (s.getStudyProgramme() != sp) {
					continue;
				}
				programmeStudentCount++;
	            System.out.println(s);
	        }
	        
	        if (programmeStudentCount == 0) {
	        	System.out.println("\tTento studijni program (" + sp + ") nema zadne studenty.");
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
			
			System.out.println("Celkovy pocet studentu ze studijniho programu " + sp + ": " + programmeStudentCount);
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
				System.out.println("Celkovy obecny studijni prumer v oboru " + sp + ": " + 0);
			}
			
			else {
				System.out.println("Celkovy obecny studijni prumer v oboru " + sp + ": " + (float)totalGradeSum / totalGradeCount);
			}
		}
	}
	
	public boolean saveToFile(int id, String filename) {
		Student student = findStudent(id);
		
		if (student == null) {
			return false;
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			bw.write(student.getFirstName());
			bw.newLine();
			bw.write(student.getLastName());
			bw.newLine();
			bw.write(String.valueOf(student.getYearBorn()));
			bw.newLine();
			bw.write(student.getStudyProgramme().name());
			bw.newLine();
			
			for (int grade : student.getGrades()) {
				bw.write(String.valueOf(grade));
				bw.newLine();
			}
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}
	
	public Student loadFromFile(String fileName) {
		String gradeLine;
		System.out.println(fileName);

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String firstName = br.readLine();
			String lastName = br.readLine();
			int yearBorn = Integer.parseInt(br.readLine());
			StudyProgramme studyProgramme = StudyProgramme.valueOf(br.readLine());
			
			
			if (studyProgramme == StudyProgramme.IBE) {
				StudentIBE studentIBE = new StudentIBE(firstName, lastName, yearBorn);
	            while ((gradeLine = br.readLine()) != null) {
	            	studentIBE.addGrade(Integer.parseInt(gradeLine));
	            }
	            return studentIBE;
			}
			
			else if (studyProgramme == StudyProgramme.TLI) {
				StudentTLI studentTLI = new StudentTLI(firstName, lastName, yearBorn);
	            while ((gradeLine = br.readLine()) != null) {
	            	studentTLI.addGrade(Integer.parseInt(gradeLine));
	            }
	            return studentTLI;
			}
			return null;
		} 
		catch (FileNotFoundException e) {
			return null;
		} 
		catch (IOException e) {
			return null;
		}
	}
}
