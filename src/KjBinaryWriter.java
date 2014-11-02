import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class KjBinaryWriter extends FileOutputStream {

	/**
	 * @param arg0
	 * @throws FileNotFoundException
	 */
	public KjBinaryWriter(String arg0) throws FileNotFoundException {
		super(arg0);
	}

	public void write(int data, int numBytes) throws IOException{
		if (numBytes==1){
			write(data%256);
		} else {
			write(data>>8,numBytes-1);
			write(data%256);
		}
	}
	
	public void writeString(String data) throws IOException{
		if (data!=null){
			for (int i=0;i<data.length();i++){
				write(data.charAt(i));
			}
		}
	}
	
	public void writeBytes(byte[] data) throws IOException{
		write(data.length);
		write(data);
	}
}
