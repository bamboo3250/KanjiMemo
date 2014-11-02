import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;


public class KjDataManager {
	private static final String VOCABULARY_FILE_NAME = "vocabulary.dat";

	public static void saveKanjiToFile() throws IOException{
		KjBinaryWriter writer = new KjBinaryWriter(VOCABULARY_FILE_NAME);
		
		ArrayList<KjKanji> tempStorage = KjStorage.getInstance().getList();
		writer.write(tempStorage.size(), 4);
		for(int i = 0; i < tempStorage.size(); i++) {
			KjKanji word = tempStorage.get(i);
			
			//writer.write(7,1);
			// 0: present string
			// 1: kunyomi list
			// 2: onyomi list
			// 3: raw strokes
			// 4: simplified strokes
			// 5: total answer
			// 6: correct answer
			// 7: id
			
			// 0: present string
			writer.write(0,1);
			byte[] bytes = word.getPresentString().getBytes("Shift_JIS");
			writer.writeBytes(bytes);
			
			// 1: kunyomi list
			writer.write(1,1);
			ArrayList<String> list = word.getKunyomiList();
			
			writer.write(list.size(),1);
			for(int j=0;j<list.size();j++){
				writer.writeBytes(list.get(j).getBytes("Shift_JIS"));
			}
			
			// 2: onyomi list
			writer.write(2,1);
			list = word.getOnyomiList();
			
			writer.write(list.size(),1);
			for(int j=0;j<list.size();j++){
				writer.writeBytes(list.get(j).getBytes("Shift_JIS"));
			}
			
			// 3: raw strokes
			writer.write(3,1);
			ArrayList<KjStroke> listStroke = word.getRawStrokeList();
			
			writer.write(listStroke.size(),1);
			for(int j=0;j<listStroke.size();j++){
				KjStroke stroke = listStroke.get(j);
				writer.write(stroke.size(),2);
				for(int k=0;k<stroke.size();k++){
					writer.write(stroke.getPoint(k).x);
					writer.write(stroke.getPoint(k).y);
				}
			}
			
			// 4: simplified strokes
			writer.write(4,1);
			listStroke = word.getSimplifiedStrokeList();
			writer.write(listStroke.size(),1);
			for(int j=0;j<listStroke.size();j++){
				KjStroke stroke = listStroke.get(j);
				writer.write(stroke.size(),2);
				for(int k=0;k<stroke.size();k++){
					writer.write(stroke.getPoint(k).x);
					writer.write(stroke.getPoint(k).y);
				}
			}
			
			// 5: total answer
			writer.write(5,1);
			writer.write(word.getTotalAnswer(), 3);
			
			// 6: correct answer
			writer.write(6,1);
			writer.write(word.getCorrectAnswer(), 3);
			
			// 7: id
			writer.write(7,1);
			writer.write(word.getId(), 2);
						
			// 255: end of task
			writer.write(255,1);	
						
		}
		writer.close();
	}
	
	public static void loadKanjiFromFile() throws IOException{
		try{
			KjBinaryReader rd = new KjBinaryReader(VOCABULARY_FILE_NAME);
			if (rd.available() > 0){
				int numWord = rd.read(4);
				try {
					for (int i = 0; i < numWord; i++){
						KjKanji temp = readKanji(rd);
						KjStorage.getInstance().addKanji(temp);
					}
				}
				catch (IOException e)
				{
					
				}
			}
			rd.close();
		} 
		catch (IOException e)
		{
			KjBinaryWriter write = new KjBinaryWriter(VOCABULARY_FILE_NAME);
			write.close();
		}
	}

	private static KjKanji readKanji(KjBinaryReader rd) throws IOException {
		KjKanji temp = new KjKanji();
		//int numAttribute = rd.read(1);
		
		//for(int j = 0; j < numAttribute; j++) {
		int typeAtt;
		do {
			typeAtt = rd.read(1);
			int length, num, numPoint;
			ArrayList<KjStroke> strokeList;
			// 0: present string
			// 1: kunyomi list
			// 2: onyomi list
			// 3: raw strokes
			// 4: simplified strokes
			// 5: total answer
			// 6: correct answer
			// 7: id
			
			switch(typeAtt) {
			case 0:
				length = rd.read(1);
				temp.setPresentString(rd.readJapString(length));
				break;
			case 1:
				num = rd.read(1);
				for(int k=0;k<num;k++){
					length = rd.read(1);
					temp.addKunyomi(rd.readJapString(length));
				}
				break;
			case 2:
				num = rd.read(1);
				for(int k=0;k<num;k++){
					length = rd.read(1);
					temp.addOnyomi(rd.readJapString(length));
				}
				break;
			case 3:
				num = rd.read(1);
				strokeList = new ArrayList<KjStroke>();
				for(int k=0;k<num;k++){
					numPoint = rd.read(2);
					KjStroke stroke = new KjStroke();
					for(int l=0;l<numPoint;l++){
						int x = rd.read(1);
						int y = rd.read(1);
						stroke.addPoint(new Point(x,y));
					}
					strokeList.add(stroke);
				}
				temp.setRawStrokeList(strokeList);
				break;
			case 4:
				num = rd.read(1);
				strokeList = new ArrayList<KjStroke>();
				for(int k=0;k<num;k++){
					numPoint = rd.read(2);
					KjStroke stroke = new KjStroke();
					for(int l=0;l<numPoint;l++){
						int x = rd.read(1);
						int y = rd.read(1);
						stroke.addPoint(new Point(x,y));
					}
					strokeList.add(stroke);
				}
				temp.setSimplifiedStrokeList(strokeList);
				break;
			case 5:
				temp.setTotalAnswer(rd.read(3));
				break;
			case 6:
				temp.setCorrectAnswer(rd.read(3));
				break;
			case 7:
				temp.setId(rd.read(2));
				break;
			}
		} while (typeAtt!=255);
		return temp;
	}
}
