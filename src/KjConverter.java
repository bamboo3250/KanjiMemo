
public class KjConverter {
	private static String[][] MAP_ROMAJI_HIRA = {
		{"a","��"},
		{"i","��"},
		{"u","��"},
		{"e","��"},
		{"o","��"},
		{"ka","��"},
		{"ki","��"},
		{"ku","��"},
		{"ke","��"},
		{"ko","��"},
		{"ga","��"},
		{"gi","��"},
		{"gu","��"},
		{"ge","��"},
		{"go","��"},
		{"sa","��"},
		{"shi","��"},
		{"su","��"},
		{"se","��"},
		{"so","��"},
		{"za","��"},
		{"zi","��"},
		{"ji","��"},
		{"zu","��"},
		{"ze","��"},
		{"zo","��"},
		{"ta","��"},
		{"chi","��"},
		{"tsu","��"},
		{"te","��"},
		{"to","��"},
		{"da","��"},
		{"di","��"},
		{"du","��"},
		{"de","��"},
		{"do","��"},
		{"na","��"},
		{"ni","��"},
		{"nu","��"},
		{"ne","��"},
		{"no","��"},
		{"ha","��"},
		{"hi","��"},
		{"hu","��"},
		{"fu","��"},
		{"he","��"},
		{"ho","��"},
		{"ba","��"},
		{"bi","��"},
		{"bu","��"},
		{"be","��"},
		{"bo","��"},
		{"pa","��"},
		{"pi","��"},
		{"pu","��"},
		{"pe","��"},
		{"po","��"},
		{"ma","��"},
		{"mi","��"},
		{"mu","��"},
		{"me","��"},
		{"mo","��"},
		{"ya","��"},
		{"yu","��"},
		{"yo","��"},
		{"ra","��"},
		{"ri","��"},
		{"ru","��"},
		{"re","��"},
		{"ro","��"},
		{"wa","��"},
		{"wo","��"},
		{"n","��"},
		{"kya","����"},
		{"kyu","����"},
		{"kyo","����"},
		{"sha","����"},
		{"shu","����"},
		{"sho","����"},
		{"cha","����"},
		{"chu","����"},
		{"cho","����"},
		{"nya","�ɂ�"},
		{"nyu","�ɂ�"},
		{"nyo","�ɂ�"},
		{"hya","�Ђ�"},
		{"hyu","�Ђ�"},
		{"hyo","�Ђ�"},
		{"mya","�݂�"},
		{"myu","�݂�"},
		{"myo","�݂�"},
		{"rya","���"},
		{"ryu","���"},
		{"ryo","���"},
		{"gya","����"},
		{"gyu","����"},
		{"gyo","����"},
		{"ja","����"},
		{"ju","����"},
		{"jo","����"},
		{"bya","�т�"},
		{"byu","�т�"},
		{"byo","�т�"},
		{"pya","�҂�"},
		{"pyu","�҂�"},
		{"pyo","�҂�"}
	};
	
	private static final char[] CONSONANT = {'k','s','t','n','h','m','y','r','w','c','f','j','d','z','b','p'};
	
	private static boolean isConsonant(char c){
		for(int i=0;i<CONSONANT.length;i++){
			if (c == CONSONANT[i]) {
				return true;
			}
		}
		return false;
	}
	
	private static String mapToHiragana(String word){
		for(int i=0;i<MAP_ROMAJI_HIRA.length;i++){
			if (word.equalsIgnoreCase(MAP_ROMAJI_HIRA[i][0])){
				return MAP_ROMAJI_HIRA[i][1];
			}
		}
		return "";
	}
	
	private static String getLongestWord(String input, int startPos){
		String result = "";
		if ((startPos>=input.length()) || (startPos<0)){
			return result;
		}
		
		for(int i=0;i<MAP_ROMAJI_HIRA.length;i++){
			if (startPos + MAP_ROMAJI_HIRA[i][0].length() <= input.length()){
				if (input.substring(startPos, startPos + MAP_ROMAJI_HIRA[i][0].length()).equalsIgnoreCase(MAP_ROMAJI_HIRA[i][0])){
					if (result.length() < MAP_ROMAJI_HIRA[i][0].length()){
						result = MAP_ROMAJI_HIRA[i][0];
					}
				}
			}
		}
		
		return result;
	}
	
	public static String convertToHiragana(String input){
		String result = "";
		for(int i=0;i<input.length();i++){
			char c = input.charAt(i);
			if (isConsonant(c)){
				if (c=='n'){
					if ((i+2 == input.length()) 
							&& ((input.charAt(i+1) == 'n') || (input.charAt(i+1) == '\''))){ 	// string ends by "nn" or "n'"
						result += mapToHiragana("n");
						i++; 
					} else if (i+1 == input.length()){
						result += 'n';
					} else {
						String temp = getLongestWord(input,i);
						if (temp.length()>0){
							result += mapToHiragana(temp);
							i += temp.length();
						} else {
							result += c;
						}
					}
				} else {
					String temp = getLongestWord(input,i);
					if (temp.length()>0){
						result += mapToHiragana(temp);
						i += temp.length();
					} else {
						result += c;
					}
				}
			} else {
				String temp = getLongestWord(input,i);
				if (temp.length()>0){
					result += mapToHiragana(temp);
					i += temp.length();
				} else {
					result += c;
				}
			}
		}
		return result;
	}
}
