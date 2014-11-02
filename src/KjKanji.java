import java.util.ArrayList;


public class KjKanji {
	private String presentString;
	private ArrayList<String> kunyomiList = new ArrayList<String>();
	private ArrayList<String> onyomiList = new ArrayList<String>();
	private ArrayList<KjStroke> rawStrokeList;
	private ArrayList<KjStroke> simplifiedStrokeList;
	private int totalAnswer;
	private int id;
	
	public int getTotalAnswer() {
		return totalAnswer;
	}

	public void setTotalAnswer(int totalAnswer) {
		this.totalAnswer = totalAnswer;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	private int correctAnswer;
	private boolean lastTimeAnswer;
	
	public String getPresentString() {
		return presentString;
	}

	public void setPresentString(String presentString) {
		this.presentString = presentString;
	}
	
	public ArrayList<String> getKunyomiList(){
		return kunyomiList;
	}
	
	public void addKunyomi(String kun){
		kunyomiList.add(kun);
	}

	public void setKunyomiList(ArrayList<String> kunyomiList) {
		this.kunyomiList = kunyomiList;
	}

	public void setOnyomiList(ArrayList<String> onyomiList) {
		this.onyomiList = onyomiList;
	}

	public ArrayList<String> getOnyomiList(){
		return onyomiList;
	}
	
	public void addOnyomi(String on){
		onyomiList.add(on);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<KjStroke> getRawStrokeList() {
		return (ArrayList<KjStroke>) rawStrokeList.clone();
	}

	public void setRawStrokeList(ArrayList<KjStroke> rawStrokeList) {
		this.rawStrokeList = rawStrokeList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<KjStroke> getSimplifiedStrokeList() {
		return (ArrayList<KjStroke>) simplifiedStrokeList.clone();
	}

	public void setSimplifiedStrokeList(ArrayList<KjStroke> simplifiedStrokeList) {
		this.simplifiedStrokeList = simplifiedStrokeList;
	}
	
	public String toString(){
		String result = "";
		if (isMastered()){
			result = "✓ ";
		} else {
			result = " ";
		}
		result += "" + id + ". " + presentString;
		
		if (kunyomiList.size()>0){
			result = result + " | Kun: ";
			for(int i=0;i<kunyomiList.size();i++){
				if (i<kunyomiList.size()-1){
					result = result + kunyomiList.get(i) + ","; 
				} else {
					result = result + kunyomiList.get(i) + " ";
				}
			}
		}
		
		if (onyomiList.size()>0){
			result = result + " | On: ";
			for(int i=0;i<onyomiList.size();i++){
				if (i<onyomiList.size()-1){
					result = result + onyomiList.get(i) + ",";
				} else {
					result = result + onyomiList.get(i) + " ";
				}
			}
		}
		
		if (rawStrokeList.size()>0){
			result += "(drew)";
		}
		return result;
	}
	
	public boolean hasKunyomi(String text){
		for(int i=0;i<kunyomiList.size();i++){
			if (text.equals(kunyomiList.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasOnyomi(String text){
		for(int i=0;i<onyomiList.size();i++){
			if (text.equals(onyomiList.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public String kunListToString(){
		String text = "";
		for(int i=0;i<kunyomiList.size()-1;i++){
			text = text + kunyomiList.get(i) + ",";
		}
		if (kunyomiList.size()>0) {
			text = text + kunyomiList.get(kunyomiList.size()-1);
		}
		return text;
	}
	
	public String onListToString(){
		String text = "";
		for(int i=0;i<onyomiList.size()-1;i++){
			text = text + onyomiList.get(i) + ",";
		}
		if (onyomiList.size()>0) {
			text = text + onyomiList.get(onyomiList.size()-1);
		}
		return text;
	}
	
	public void increaseTotalAnswer(){
		totalAnswer++;
	}
	
	public void increaseCorrectAnswer(){
		correctAnswer++;
	}
	
	public boolean isMastered(){
		if ((totalAnswer>5) && ((1.0*correctAnswer)/totalAnswer>0.75) && (lastTimeAnswer)){
			return true;
		}
		return false;
	}

	public boolean getLastTimeAnswer() {
		return lastTimeAnswer;
	}

	public void setLastTimeAnswer(boolean lastTimeAnswer) {
		this.lastTimeAnswer = lastTimeAnswer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
