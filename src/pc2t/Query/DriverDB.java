package pc2t.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pc2t.Student.Student;
import pc2t.Student.Student.StudyProgramme;
import pc2t.Student.StudentIBE;
import pc2t.Student.StudentTLI;

public class DriverDB {
	
	public static void resetDatabase(Connection conn) {
	    dropStudents(conn);
	    dropGrades(conn);
	    initGrades(conn);
	    initStudents(conn);
	}
	
    public static boolean dropStudents(Connection conn) {
        String sql = "DROP TABLE IF EXISTS students";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean dropGrades(Connection conn) {
        String sql = "DROP TABLE IF EXISTS grades";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public static boolean initStudents(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY," 				+
                "first_name TEXT," 					+
                "last_name TEXT,"					+
                "year_born INT,"					+
                "study_programme VARCHAR(50))";
        
        try (Statement stmt = conn.createStatement()) {
        	stmt.execute(sql);
        	
        	return true;
        } 
        catch (SQLException e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
    public static boolean initGrades(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS grades (" +
                     "id INT PRIMARY KEY, "		+
                     "student_id INT, " 		+
                     "grade INT,"				+ 
                     "FOREIGN KEY (student_id) REFERENCES students(id))";

        try (Statement stmt = conn.createStatement()) {
        	stmt.execute(sql);
        	return true;
        }     
        catch (SQLException e) {
        	e.printStackTrace();
        	return false;
        }
    }
    
    public static boolean insertStudents(Connection conn, ArrayList<Student> students) {
        String sql = "INSERT INTO students (id, first_name, last_name, year_born, study_programme) VALUES (?, ?, ?, ?, ?)";
    	for (Student student : students) {
            try (PreparedStatement prepare = conn.prepareStatement(sql)) {
            	prepare.setInt(1, student.getID());
            	prepare.setString(2, student.getFirstName());
            	prepare.setString(3, student.getLastName());
            	prepare.setInt(4, student.getYearBorn());
            	prepare.setString(5, student.getStudyProgramme().name());
            	prepare.executeUpdate();
            } 
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    	}
    	
        sql = "INSERT INTO grades (student_id, grade) VALUES (?, ?)";
    	for (Student student : students) {
    		for (Integer grade : student.getGrades()) {
    	        try (PreparedStatement prepare = conn.prepareStatement(sql)) {
    	        	prepare.setInt(1, student.getID());
    	        	prepare.setInt(2, grade);
    	        	prepare.executeUpdate();
    	        } 
    	        catch (SQLException e) {
    	            e.printStackTrace();
    	            return false;
    	        }
    		}
    	}
    	return true;
    }
    
    public static ArrayList<Student> loadStudents(Connection conn) {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int yearBorn = rs.getInt("year_born");
                String studyProgramme = rs.getString("study_programme");
                
              
                if (StudyProgramme.valueOf(studyProgramme) == StudyProgramme.IBE) {
                    StudentIBE student = new StudentIBE(firstName, lastName, yearBorn);
                    student.setID(id);
                    students.add(student);
                }
                
                else if (StudyProgramme.valueOf(studyProgramme) == StudyProgramme.TLI) {
                    StudentTLI student = new StudentTLI(firstName, lastName, yearBorn);
                    student.setID(id);
                    students.add(student);
                }
                
                else {
                    throw new IllegalArgumentException("Doslo k chybe, byl nacten neznamy studijni program!");
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
        sql = "SELECT * FROM grades";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("student_id");
                int grade = rs.getInt("grade");

                for (Student student : students) {
					if (student.getID() == id) {
						student.addGrade(grade);
					}
				}
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
        return students;
    }
    
    public static int getNextID(Connection conn) {
        String sql = "SELECT MAX(id) FROM students";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
}
