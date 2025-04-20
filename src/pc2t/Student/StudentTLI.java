package pc2t.Student;

import java.util.HashMap;


public class StudentTLI extends Student {
    private static final HashMap<Character, String> MORSE_MAP = initializeMorseMap();

	public StudentTLI(String firstName, String lastName, int yearBorn) {
		super(firstName, lastName, yearBorn, StudyProgramme.TLI);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public String specialAbility() {
		return convertToMorseCode(this.getFullName());
	}
	
	public String convertToMorseCode(String input) {
		StringBuilder morseName = new StringBuilder();
		
		for (char c : input.toUpperCase().toCharArray()) {
			String current = StudentTLI.MORSE_MAP.get(c);
			
			if (current != null) {
				morseName.append(current).append(" ");
			}
			else {
				morseName.append("? ");
			}
		}
		return morseName.toString().trim();
	}
	
	private static HashMap<Character, String> initializeMorseMap() {
		HashMap<Character, String> map = new HashMap<>();
		
		map.put('A', ".-");
		map.put('B', "-...");
		map.put('C', "-.-.");
		map.put('D', "-..");
		map.put('E', ".");
		map.put('F', "..-.");
		map.put('G', "--.");
		map.put('H', "....");
		map.put('I', "..");
		map.put('J', ".---");
		map.put('K', "-.-");
		map.put('L', ".-..");
		map.put('M', "--");
		map.put('N', "-.");
		map.put('O', "---");
		map.put('P', ".--.");
		map.put('Q', "--.-");
		map.put('R', ".-.");
		map.put('S', "...");
		map.put('T', "-");
		map.put('U', "..-");
		map.put('V', "...-");
		map.put('W', ".--");
		map.put('X', "-..-");
		map.put('Y', "-.--");
		map.put('Z', "--..");
		map.put(' ', "/");
		
		return map;
	}
}
