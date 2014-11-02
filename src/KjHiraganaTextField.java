import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;


public class KjHiraganaTextField extends JTextField {
	private KjHiraganaTextField hiraganaTextField;
	
	public KjHiraganaTextField(){
		hiraganaTextField = this;
		
		hiraganaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//onyomiTextField.setText("Typed " + (int) e.getKeyChar() + " " +e.getKeyCode() + " " + KeyEvent.VK_RIGHT);
				if ((!e.isControlDown()) && (!e.isAltDown())){
					char c = e.getKeyChar();
					if (('a'<=c) && (c<='z')){
						c = (char) (c - 'a' + 'A');
					}
					switch (c){
					case KeyEvent.VK_A: case KeyEvent.VK_B: case KeyEvent.VK_C: case KeyEvent.VK_D: case KeyEvent.VK_E: 
					case KeyEvent.VK_F: case KeyEvent.VK_G: case KeyEvent.VK_H: case KeyEvent.VK_I: case KeyEvent.VK_J: 
					case KeyEvent.VK_K: case KeyEvent.VK_L: case KeyEvent.VK_M: case KeyEvent.VK_N: case KeyEvent.VK_O: 
					case KeyEvent.VK_P: case KeyEvent.VK_Q: case KeyEvent.VK_R: case KeyEvent.VK_S: case KeyEvent.VK_T: 
					case KeyEvent.VK_U: case KeyEvent.VK_V: case KeyEvent.VK_W: case KeyEvent.VK_X: case KeyEvent.VK_Y: 
					case KeyEvent.VK_Z: case KeyEvent.VK_RIGHT: 
						String text = hiraganaTextField.getText() + e.getKeyChar();
						hiraganaTextField.setText(KjConverter.convertToHiragana(text.toLowerCase()));
						e.consume();
						break;
					}
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				//onyomiTextField.setText("Typed " + (int) e.getKeyChar() + " " +e.getKeyCode() + " " + KeyEvent.VK_RIGHT);
				if ((!e.isControlDown()) && (!e.isAltDown())){
					char c = e.getKeyChar();
					if (('a'<=c) && (c<='z')){
						c = (char) (c - 'a' + 'A');
					}
					switch (c){
					case KeyEvent.VK_A: case KeyEvent.VK_B: case KeyEvent.VK_C: case KeyEvent.VK_D: case KeyEvent.VK_E: 
					case KeyEvent.VK_F: case KeyEvent.VK_G: case KeyEvent.VK_H: case KeyEvent.VK_I: case KeyEvent.VK_J: 
					case KeyEvent.VK_K: case KeyEvent.VK_L: case KeyEvent.VK_M: case KeyEvent.VK_N: case KeyEvent.VK_O: 
					case KeyEvent.VK_P: case KeyEvent.VK_Q: case KeyEvent.VK_R: case KeyEvent.VK_S: case KeyEvent.VK_T: 
					case KeyEvent.VK_U: case KeyEvent.VK_V: case KeyEvent.VK_W: case KeyEvent.VK_X: case KeyEvent.VK_Y: 
					case KeyEvent.VK_Z: case KeyEvent.VK_RIGHT:
						e.consume();
						break;
					}
				}
			}
		});
	}
}
