package pc2t;

import java.util.HashMap;

public class TLIStudent extends Student {
	private HashMap<Character, String> morseMap;

	public TLIStudent(String firstName, String lastName, int yearBorn) {
		super(firstName, lastName, yearBorn);
		this.morseMap = new HashMap<>();
		initializeMorseMap();
	}
	
	@Override
	public String toString() {
		return super.toString() + "\n\tFull name in Morse code: " + this.getMorseCodeOfName();
	}
	
	public String getMorseCodeOfName() {
		return convertToMorseCode(this.getFullName())
				.toString()
				.trim();
	}
	
	private StringBuilder convertToMorseCode(final String input) {
		StringBuilder morseName = new StringBuilder();
		
		for (char c : input.toUpperCase().toCharArray()) {
			String current = this.morseMap.get(c);
			
			if (current != null) {
				morseName.append(current).append(" ");
			}
			else {
				morseName.append("? ");
			}
		}
		return morseName;
	}
	
	private void initializeMorseMap() {
        this.morseMap.put('A', ".-");
        this.morseMap.put('B', "-...");
        this.morseMap.put('C', "-.-.");
        this.morseMap.put('D', "-..");
        this.morseMap.put('E', ".");
        this.morseMap.put('F', "..-.");
        this.morseMap.put('G', "--.");
        this.morseMap.put('H', "....");
        this.morseMap.put('I', "..");
        this.morseMap.put('J', ".---");
        this.morseMap.put('K', "-.-");
        this.morseMap.put('L', ".-..");
        this.morseMap.put('M', "--");
        this.morseMap.put('N', "-.");
        this.morseMap.put('O', "---");
        this.morseMap.put('P', ".--.");
        this.morseMap.put('Q', "--.-");
        this.morseMap.put('R', ".-.");
        this.morseMap.put('S', "...");
        this.morseMap.put('T', "-");
        this.morseMap.put('U', "..-");
        this.morseMap.put('V', "...-");
        this.morseMap.put('W', ".--");
        this.morseMap.put('X', "-..-");
        this.morseMap.put('Y', "-.--");
        this.morseMap.put('Z', "--..");
        this.morseMap.put(' ', "/");
	}
	
}
