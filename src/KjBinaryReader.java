import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class KjBinaryReader extends FileInputStream {

	/**
	 * @param arg0
	 * @throws FileNotFoundException
	 */
	public KjBinaryReader(String arg0) throws FileNotFoundException {
		super(arg0);
	}
	
	public int read(int numBytes) throws IOException{
		int result = 0;
		for(int i=0;i<numBytes;i++){
			try{
				result = (result<<8) + read();
			}
			catch  (IOException e){
				throw e;
			}
		}
		return result;
	}
	
	public String readJapString(int length) throws IOException{
		if (length>0){
			byte[] result = new byte[length];
			read(result);
			return new String(result, "Shift_JIS");
		} else {
			throw new IOException();
		}
	}
	
	
}
