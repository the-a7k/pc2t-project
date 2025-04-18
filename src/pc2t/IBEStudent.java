package pc2t;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IBEStudent extends Student {
	
	public enum HashVariant {
		MD5,
		SHA1,
		SHA256,
		SHA384,
		SHA512,
		DEFAULT
	}

	public IBEStudent(String firstName, String lastName, int yearBorn) {
		super(firstName, lastName, yearBorn);
	}
	
	@Override
	public String toString() {
		return super.toString() + "\n\tFull name in SHA-256 hash: " + this.getHashOfName(HashVariant.SHA256);
	}
	
	public String getHashOfName(HashVariant hashChoice)  {
		final String HEXFORMAT = "%02x";
		StringBuilder hexString = new StringBuilder();
		String hashVariant;

		switch(hashChoice) {
			case MD5:
				hashVariant = "MD5";
				break;
			case SHA1:
				hashVariant = "SHA1";
				break;
			case SHA384:
				hashVariant = "SHA-384";
				break;
			case SHA512:
				hashVariant = "SHA-512";
				break;
			case SHA256:
			default:
				hashVariant = "SHA-256";
				break;
		}
		
		try {
			MessageDigest digest = MessageDigest.getInstance(hashVariant);
			byte[] hashBytes = digest.digest(this.getFullName().getBytes());
			
			for (byte b : hashBytes) {
				hexString.append(String.format(HEXFORMAT, b));
			}
			
			return hexString.toString();
		}
		
		catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException("Invalid hashing algorithm: " + hashVariant);
		}
		catch (Exception ex) {
			throw new RuntimeException("Unknown error while converting name to string.");
		}
		
	}

}
