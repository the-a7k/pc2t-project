package pc2t.Student;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StudentIBE extends Student {

	public StudentIBE(String firstName, String lastName, int yearBorn) {
		super(firstName, lastName, yearBorn, StudyProgramme.IBE);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public String specialAbility() {
		return convertToHash(this.getFullName(), HashVariant.SHA256);
	}
	
	public String convertToHash(String input, HashVariant hashChoice)  {
		final String HEX_FORMAT = "%02x";
		StringBuilder hexString = new StringBuilder();
		
		try {
			MessageDigest digest = MessageDigest.getInstance(hashChoice.name());
			byte[] hashBytes = digest.digest(this.getFullName().getBytes());
			
			for (byte b : hashBytes) {
				hexString.append(String.format(HEX_FORMAT, b));
			}
			return hexString.toString();
		}
		
		catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException("Invalid hashing algorithm: " + hashChoice.name());
		}
		catch (Exception ex) {
			throw new RuntimeException("Unknown error while converting to hash.");
		}
		
	}
	
	public enum HashVariant {
		MD5,
		SHA1,
		SHA256,
		SHA384,
		SHA512
	}

}
