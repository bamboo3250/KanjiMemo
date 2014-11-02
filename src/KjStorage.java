import java.util.ArrayList;
import java.util.Random;


public class KjStorage {
	private static KjStorage storage;
	private ArrayList<KjKanji> kanjiList = new ArrayList<KjKanji>();
	private Random random = new Random(System.currentTimeMillis());
	
	public static KjStorage getInstance(){
		if (storage==null){
			storage = new KjStorage();
		}
		return storage;
	}
	
	public int size(){
		return kanjiList.size();
	}
	
	public KjKanji getKanji(int i){
		return kanjiList.get(i);
	}
	
	public void addKanji(KjKanji word){
		if (word.getId()==0){
			word.setId(getHighestId()+1);
		}
		kanjiList.add(word);
	}
	
	public void addKanji(int pos, KjKanji word){
		if (word.getId()==0){
			word.setId(getHighestId()+1);
		}
		kanjiList.add(pos, word);
	}
	
	public void deleteKanji(int index){
		kanjiList.remove(index);
	}
	
	public void deleteKanji(KjKanji word){
		kanjiList.remove(word);
	}
	
	public ArrayList<KjKanji> getList(){
		return kanjiList;
	}
	
	public KjKanji getRandomWithPriority(){
		ArrayList<KjKanji> unmasteredList = new ArrayList<KjKanji>();
		for(int i=0;i<(kanjiList.size()+1)/2;i++){	// get list in first haft
			if (!kanjiList.get(i).isMastered()){
				unmasteredList.add(kanjiList.get(i));
			}
		}
		KjKanji chosenWord;
		if (unmasteredList.size()>0){
			chosenWord = unmasteredList.get(random.nextInt(unmasteredList.size()));
		} else {
			chosenWord = kanjiList.get(random.nextInt((kanjiList.size()+1)/2));
		}
		kanjiList.remove(chosenWord);
		kanjiList.add(chosenWord);
		return chosenWord;
	}
	
	public int getHighestId(){
		int result = 0;
		for(int i=0;i<kanjiList.size();i++){
			if (kanjiList.get(i).getId()>result){
				result = kanjiList.get(i).getId();
			}
		}
		return result;
	}
	
	public KjKanji find(int id){
		for(int i=0;i<kanjiList.size();i++){
			if (kanjiList.get(i).getId() == id){
				return kanjiList.get(i);
			}
		}
		return null;
	}
}
