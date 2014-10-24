
public class KjConverter {
	private static String[][] MAP_ROMAJI_HIRA = {
		{"a","‚ "},
		{"i","‚¢"},
		{"u","‚¤"},
		{"e","‚¦"},
		{"o","‚¨"},
		{"ka","‚©"},
		{"ki","‚«"},
		{"ku","‚­"},
		{"ke","‚¯"},
		{"ko","‚±"},
		{"ga","‚ª"},
		{"gi","‚¬"},
		{"gu","‚®"},
		{"ge","‚°"},
		{"go","‚²"},
		{"sa","‚³"},
		{"shi","‚µ"},
		{"su","‚·"},
		{"se","‚¹"},
		{"so","‚»"},
		{"za","‚´"},
		{"zi","‚¶"},
		{"ji","‚¶"},
		{"zu","‚¸"},
		{"ze","‚º"},
		{"zo","‚¼"},
		{"ta","‚½"},
		{"chi","‚¿"},
		{"tsu","‚Â"},
		{"te","‚Ä"},
		{"to","‚Æ"},
		{"da","‚¾"},
		{"di","‚À"},
		{"du","‚Ã"},
		{"de","‚Å"},
		{"do","‚Ç"},
		{"na","‚È"},
		{"ni","‚É"},
		{"nu","‚Ê"},
		{"ne","‚Ë"},
		{"no","‚Ì"},
		{"ha","‚Í"},
		{"hi","‚Ð"},
		{"hu","‚Ó"},
		{"fu","‚Ó"},
		{"he","‚Ö"},
		{"ho","‚Ù"},
		{"ba","‚Î"},
		{"bi","‚Ñ"},
		{"bu","‚Ô"},
		{"be","‚×"},
		{"bo","‚Ú"},
		{"pa","‚Ï"},
		{"pi","‚Ò"},
		{"pu","‚Õ"},
		{"pe","‚Ø"},
		{"po","‚Û"},
		{"ma","‚Ü"},
		{"mi","‚Ü"},
		{"mu","‚Þ"},
		{"me","‚ß"},
		{"mo","‚à"},
		{"ya","‚â"},
		{"yu","‚ä"},
		{"yo","‚æ"},
		{"ra","‚ç"},
		{"ri","‚è"},
		{"ru","‚é"},
		{"re","‚ê"},
		{"ro","‚ë"},
		{"wa","‚í"},
		{"wo","‚ð"},
		{"n","‚ñ"},
		{"kya","‚«‚á"},
		{"kyu","‚«‚ã"},
		{"kyo","‚«‚å"},
		{"sha","‚µ‚á"},
		{"shu","‚µ‚ã"},
		{"sho","‚µ‚å"},
		{"cha","‚¿‚á"},
		{"chu","‚¿‚ã"},
		{"cho","‚¿‚å"},
		{"nya","‚É‚á"},
		{"nyu","‚É‚ã"},
		{"nyo","‚É‚å"},
		{"hya","‚Ð‚á"},
		{"hyu","‚Ð‚ã"},
		{"hyo","‚Ð‚å"},
		{"mya","‚Ý‚á"},
		{"myu","‚Ý‚ã"},
		{"myo","‚Ý‚å"},
		{"rya","‚è‚á"},
		{"ryu","‚è‚ã"},
		{"ryo","‚è‚å"},
		{"gya","‚¬‚á"},
		{"gyu","‚¬‚ã"},
		{"gyo","‚¬‚å"},
		{"ja","‚¶‚á"},
		{"ju","‚¶‚ã"},
		{"jo","‚¶‚å"},
		{"bya","‚Ñ‚á"},
		{"byu","‚Ñ‚ã"},
		{"byo","‚Ñ‚å"},
		{"pya","‚Ò‚á"},
		{"pyu","‚Ò‚ã"},
		{"pyo","‚Ò‚å"}
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
