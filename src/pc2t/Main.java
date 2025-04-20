package pc2t;

import java.sql.Connection;
import java.util.Scanner;

import pc2t.Query.ConnectDB;
import pc2t.Query.DriverDB;
import pc2t.Student.Database;
import pc2t.Student.Student;
import pc2t.Student.StudentIBE;
import pc2t.Student.StudentTLI;

public class Main {

	public static void main(String[] args) {
	    Connection conn = ConnectDB.connect();
	    	    
	    printProgramHelp();
	    programLoop(conn);
	    
	    ConnectDB.disconnect();
	}
	
	private static void programLoop(Connection conn) {
		final String FILE_DIRECTORY = "save/";
		final String FILE_EXTENSION = ".log";
	    String fileName;

	    Scanner sc = new Scanner(System.in);
	    boolean programRunning = true;
	    
	    Student.setNextID(DriverDB.getNextID(conn));
	    Database studentDatabase = new Database();
	    studentDatabase.setStudents(DriverDB.loadStudents(conn));
	    
	    Student student;
	    int studentID;
	    String firstName;
	    String lastName;
	    int yearBorn;
	    int studyProgramme;
	    int grade;

	    while (programRunning) {
	    	System.out.println("\n[Pro zobrazeni vsech moznosti, vyberte moznost 0]");
	    	System.out.println("Vyberte si moznost: ");
	    	
		    int choice = nextInt(sc);
		    
		    switch(choice) {
		    	case 0:
		    		printProgramHelp();
		    		break;
		    	
		    	case 1:
		    		System.out.println("Vyberte si studijni program dle cisla:\n\tInformacni bezpecnost [1]\n\tTelekomunikace [2]\nVase volba:");
		    		studyProgramme = nextInt(sc);
		    		
		    		while (studyProgramme != 1 && studyProgramme != 2) {
			    		System.out.println("Nespravna volba!");
			    		System.out.println("Vyberte si studijni program dle cisla:\n\tInformacni bezpecnost [1]\n\tTelekomunikace [2]\nVase volba:");
			    		studyProgramme = nextInt(sc);
		    		}
		    		
		    		System.out.println("Zadejte jmeno: ");
		    		firstName = sc.next();
		    		
		    		System.out.println("Zadejte prijmeni: ");
		    		lastName = sc.next();
		    		
		    		System.out.println("Zadejte rok narozeni: ");
		    		yearBorn = nextInt(sc);
		    		
		    		if (studyProgramme == 1) {
		    			StudentIBE studentIBE = new StudentIBE(firstName, lastName, yearBorn);
		    			studentDatabase.addStudent(studentIBE);
		    			studentID = studentIBE.getID();
		    		}
		    		else if (studyProgramme == 2) {
		    			StudentTLI studentTLI = new StudentTLI(firstName, lastName, yearBorn);
		    			studentDatabase.addStudent(studentTLI);
		    			studentID = studentTLI.getID();
		    		}
		    		
		    		else {
			    		System.out.println("Doslo k chybe pri zadavani studenta do databaze!");
			    		break;
		    		}
		    		
		    		System.out.println("Student " + firstName + " " + lastName + " byl uspesne pridan. ID studenta: " + studentID);
		    		break;
		    		
		    	case 2:
		    		System.out.println("Zadejte ID studenta pro zadani znamky: ");
		    		studentID = nextInt(sc);
		    		student = studentDatabase.findStudent(studentID);
		    		
		    		if (student == null) {
			    		System.out.println("Hledane ID studenta neexistuje!");
			    		break;
		    		}
		    		
		    		System.out.println("Zadejte znamku v rozmezi 1-5:");
		    		grade = nextInt(sc);
		    		
		    		while (grade < 1 || grade > 5) {
			    		System.out.println("Nespravna volba! Zadejte znamku v rozmezi 1-5.");
			    		grade = nextInt(sc);
		    		}
		    		
		    		if (student.addGrade(grade)) {
		    			System.out.println("Znamka byla uspesne pridana.");
		    		}
		    		
		    		else {
		    			System.out.println("Doslo k chybe, nepodarilo se pridat znamku!");
		    		}
		    		break;
	    		
		    	case 3:	
		    		System.out.println("Zadejte ID pro odstraneni studenta: ");
		    		studentID = nextInt(sc);
		    		student = studentDatabase.findStudent(studentID);
		    		
		    		if (student == null) {
			    		System.out.println("Hledane ID studenta neexistuje!");
			    		break;
		    		}
		    		
		    		if (!studentDatabase.removeStudent(studentID)) {
		    			System.out.println("Doslo k chybe, studenta nelze odstranit!");
		    		}
		    		else {
		    			System.out.println("Student byl uspesne odstranen.");
		    		}
		    		
		    		
	    		case 4:
		    		System.out.println("Zadejte ID studenta pro vypsani informaci: ");
		    		studentID = nextInt(sc);
		    		student = studentDatabase.findStudent(studentID);
		    		
		    		if (student == null) {
			    		System.out.println("Hledane ID studenta neexistuje!");
			    		break;
		    		}
		    		
		    		System.out.println(student);
		    		break;
			    	
	    		case 5:
		    		System.out.println("Zadejte ID studenta pro pouziti jeho dovednosti: ");
		    		studentID = nextInt(sc);
		    		student = studentDatabase.findStudent(studentID);
		    		
		    		if (student == null) {
			    		System.out.println("Hledane ID studenta neexistuje!");
			    		break;
		    		}
		    		
		    		System.out.println("Dovednost studenta " + student.getFullName() + " z oboru " + student.getStudyProgramme() + ": " + student.specialAbility());
		    		break;
		    		
	    		case 6:
	    			System.out.println("Abecedne razeny vypis vsech studentu v jednotlivych oborech: ");
	    			studentDatabase.printStudyProgrammeSortedStudents();
	    			break;
	    			
	    		case 7:
	    			System.out.println("Vypis obecneho studijniho prumeru v jednotlivych oborech: ");
	    			studentDatabase.printStudyProgrammeGradeAverage();
	    			break;
	    			
	    		case 8:
	    			System.out.println("Vypis celkoveho poctu studentu v jednotlivych oborech: ");
	    			studentDatabase.printStudyProgrammeStudentCount();
	    			break;
	    			
	    		case 9:
	    			System.out.println("Zadejte ID studenta pro ulozeni do souboru: ");
		    		studentID = nextInt(sc);
		    		student = studentDatabase.findStudent(studentID);
		    		
		    		if (student == null) {
			    		System.out.println("Hledane ID studenta neexistuje!");
			    		break;
		    		}
		    		
		    		fileName = student.getFirstName() + "_" + student.getLastName() + FILE_EXTENSION;
		    		
		    		if (studentDatabase.saveToFile(studentID, FILE_DIRECTORY + fileName)) {
		    			System.out.println("Student byl uspesne ulozen do souboru: " + fileName);
		    		}
		    		else {
		    			System.out.println("Doslo k chybe, soubor nebyl ulozen!");
		    		}
		    		break;
		    		
	    		case 10:
	    			System.out.println("Zadejte nazev souboru: ");
	    			fileName = sc.next();

	    			try {
		    			student = studentDatabase.loadFromFile(FILE_DIRECTORY + fileName + FILE_EXTENSION);
	    			}
	    			catch (Exception e) {
	    				System.out.println("Doslo k chybe, nepodarilo se zpracovat soubor!");
	    				break;
	    			}
	    			
	    			if (student == null) {
	    				System.out.println("Doslo k chybe, nepodarilo se nahrat studenta ze souboru!");
	    			}
	    			else {
		    			System.out.println("Student byl uspesne nahran do databaze. ID studenta: " + student.getID());
		    			studentDatabase.addStudent(student);
	    			}
	    			break;

		    	case 11:
		    		System.out.println("Ukladani do SQL databaze...");
		    		DriverDB.resetDatabase(conn);
		    		if (!DriverDB.insertStudents(conn, studentDatabase.getStudents())) {
			    		System.out.println("Doslo k chybe, nepodarilo se ulozit do databaze!");
		    		}
		    		else {
			    		System.out.println("Databaze byla uspesne ulozena.");
		    		}
		    		
		    		System.out.println("Program ukoncen.");
		    		
		    		programRunning = false;
		    		break;
		    		
		    	default:
		    		System.out.println("Vyberte validni moznost.");
		    		break;
		    }
	    }
	}
	
	private static int nextInt(Scanner sc) {
		int result;
		while (true) {
			if (sc.hasNextInt()) {
				result = sc.nextInt();
				sc.nextLine();
				break;
			}
			else {
				System.out.println("Nespravny vstup! Zadejte vstup typu cislo.");
				sc.nextLine();
			}
		}
		return result;
    }
	
	private static void printProgramHelp() {
	    System.out.println("<==> Program databaze studentu <==>");
	    System.out.println("\t[1] Pridani noveho studenta");
	    System.out.println("\t[2] Zadani znamky studentovi");
	    System.out.println("\t[3] Odstraneni studenta z databaze");
	    System.out.println("\t[4] Vypis informaci o studentovi");
	    System.out.println("\t[5] Zobrazit specialni dovednost studenta");
	    System.out.println("\t[6] Abecedne vypsat vsechny studenty dle studijniho programu");
	    System.out.println("\t[7] Vypis obecneho studijniho prumeru dle studijniho programu");
	    System.out.println("\t[8] Vypis celkoveho poctu studentu ve studijnich programech");
	    System.out.println("\t[9] Ulozeni studenta z databaze do souboru");
	    System.out.println("\t[10] Nacteni studenta ze souboru do databaze");
	    System.out.println("\t[11] Ukonceni programu");
	}
}
