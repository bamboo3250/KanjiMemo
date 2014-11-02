import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;


public class KjKatakanaTextField extends JTextField {
	private KjKatakanaTextField katakanaTextField;
	
	public KjKatakanaTextField(){
		katakanaTextField = this;
		katakanaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
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
						String text = katakanaTextField.getText() + e.getKeyChar();
						katakanaTextField.setText(KjConverter.convertToKatakana(text.toLowerCase()));
						e.consume();
						break;
					}
				}
			}
			
			public void keyTyped(KeyEvent e) {
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
