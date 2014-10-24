
public class KjConverter {
	private static String[][] MAP_ROMAJI_HIRA = {
		{"a","あ"},
		{"i","い"},
		{"u","う"},
		{"e","え"},
		{"o","お"},
		{"ka","か"},
		{"ki","き"},
		{"ku","く"},
		{"ke","け"},
		{"ko","こ"},
		{"ga","が"},
		{"gi","ぎ"},
		{"gu","ぐ"},
		{"ge","げ"},
		{"go","ご"},
		{"sa","さ"},
		{"shi","し"},
		{"si","し"},
		{"su","す"},
		{"se","せ"},
		{"so","そ"},
		{"za","ざ"},
		{"zi","じ"},
		{"ji","じ"},
		{"zu","ず"},
		{"ze","ぜ"},
		{"zo","ぞ"},
		{"ta","た"},
		{"chi","ち"},
		{"ti","ち"},
		{"tsu","つ"},
		{"tu","つ"},
		{"te","て"},
		{"to","と"},
		{"da","だ"},
		{"di","ぢ"},
		{"du","づ"},
		{"de","で"},
		{"do","ど"},
		{"na","な"},
		{"ni","に"},
		{"nu","ぬ"},
		{"ne","ね"},
		{"no","の"},
		{"ha","は"},
		{"hi","ひ"},
		{"hu","ふ"},
		{"fu","ふ"},
		{"he","へ"},
		{"ho","ほ"},
		{"ba","ば"},
		{"bi","び"},
		{"bu","ぶ"},
		{"be","べ"},
		{"bo","ぼ"},
		{"pa","ぱ"},
		{"pi","ぴ"},
		{"pu","ぷ"},
		{"pe","ぺ"},
		{"po","ぽ"},
		{"ma","ま"},
		{"mi","み"},
		{"mu","む"},
		{"me","め"},
		{"mo","も"},
		{"ya","や"},
		{"yu","ゆ"},
		{"yo","よ"},
		{"ra","ら"},
		{"ri","り"},
		{"ru","る"},
		{"re","れ"},
		{"ro","ろ"},
		{"wa","わ"},
		{"wo","を"},
		{"n","ｎ"},
		{"kya","きゃ"},
		{"kyu","きゅ"},
		{"kyo","きょ"},
		{"sha","しゃ"},
		{"shu","しゅ"},
		{"sho","しょ"},
		{"cha","ちゃ"},
		{"chu","ちゅ"},
		{"cho","ちょ"},
		{"nya","にゃ"},
		{"nyu","にゅ"},
		{"nyo","にょ"},
		{"hya","ひゃ"},
		{"hyu","ひゅ"},
		{"hyo","ひょ"},
		{"mya","みゃ"},
		{"myu","みゅ"},
		{"myo","みょ"},
		{"rya","りゃ"},
		{"ryu","りゅ"},
		{"ryo","りょ"},
		{"gya","ぎゃ"},
		{"gyu","ぎゅ"},
		{"gyo","ぎょ"},
		{"ja","じゃ"},
		{"ju","じゅ"},
		{"jo","じょ"},
		{"bya","びゃ"},
		{"byu","びゅ"},
		{"byo","びょ"},
		{"pya","ぴゃ"},
		{"pyu","ぴゅ"},
		{"pyo","ぴょ"}
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
