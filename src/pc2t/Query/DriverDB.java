package pc2t.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
        String sql = "CREATE TABLE IF NOT EXISTS students (" 	+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," 		+
                "first_name TEXT," 								+
                "last_name TEXT,"								+
                "year_born INT,"								+
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
        String sql = "CREATE TABLE IF NOT EXISTS grades (" 			+
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, "		+
                     "student_id INT, " 							+
                     "grade INT,"									+ 
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
    
    public static boolean insertStudents(Connection conn, HashMap<Integer, Student> students) {
        String sql = "INSERT INTO students (id, first_name, last_name, year_born, study_programme) VALUES (?, ?, ?, ?, ?)";
        for (var student : students.entrySet()) {
            try (PreparedStatement prepare = conn.prepareStatement(sql)) {
            	prepare.setInt(1, student.getKey());
            	prepare.setString(2, student.getValue().getFirstName());
            	prepare.setString(3, student.getValue().getLastName());
            	prepare.setInt(4, student.getValue().getYearBorn());
            	prepare.setString(5, student.getValue().getStudyProgramme().name());
            	prepare.executeUpdate();
            } 
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    	}
    	
        sql = "INSERT INTO grades (student_id, grade) VALUES (?, ?)";
        for (var student : students.entrySet()) {
    		for (Integer grade : student.getValue().getGrades()) {
    	        try (PreparedStatement prepare = conn.prepareStatement(sql)) {
    	        	prepare.setInt(1, student.getKey());
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
    
    public static HashMap<Integer, Student> loadStudents(Connection conn) {
        HashMap<Integer, Student> students = new HashMap<>();
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
                    students.put(id, student);
                }
                
                else if (StudyProgramme.valueOf(studyProgramme) == StudyProgramme.TLI) {
                    StudentTLI student = new StudentTLI(firstName, lastName, yearBorn);
                    student.setID(id);
                    students.put(id, student);
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

                for (var student : students.entrySet()) {
					if (student.getKey() == id) {
						student.getValue().addGrade(grade);
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
