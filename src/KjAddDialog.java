import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class KjAddDialog extends JDialog {

	private static KjAddDialog addDialog;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField kanjiTextField;
	private JTextField kunyomiTextField;
	private JTextField onyomiTextField;
	private KjDrawingPanel drawingPanel;
	private JLabel lblStrokes;
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KjAddDialog dialog = KjAddDialog.getInstance();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static KjAddDialog getInstance(){
		if (addDialog==null){
			addDialog = new KjAddDialog();
		}
		return addDialog;
	}
	
	/**
	 * Create the dialog.
	 */
	private KjAddDialog() {
		setResizable(false);
		setTitle("Add a new Kanji");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		createDrawingPanel();
		createTextField();
		createLabels();
		createButtonPanel();
	}

	
	
	private void createDrawingPanel() {
		drawingPanel = new KjDrawingPanel();
		drawingPanel.setBounds(254, 28, 180, 180);
		contentPanel.add(drawingPanel);
	}

	private void createLabels() {
		JLabel lblKanji = new JLabel("Kanji");
		lblKanji.setBounds(27, 7, 46, 14);
		contentPanel.add(lblKanji);
		
		JLabel lblKunyomi = new JLabel("Kunyomi");
		lblKunyomi.setBounds(27, 101, 75, 14);
		contentPanel.add(lblKunyomi);
		
		JLabel lblOnyomi = new JLabel("Onyomi");
		lblOnyomi.setBounds(27, 163, 46, 14);
		contentPanel.add(lblOnyomi);
		
		JLabel lblDrawKanjiHere = new JLabel("Draw Kanji here");
		lblDrawKanjiHere.setBounds(242, 7, 101, 14);
		contentPanel.add(lblDrawKanjiHere);
		
		lblStrokes = new JLabel("Stroke(s): 0");
		lblStrokes.setBounds(242, 213, 129, 14);
		contentPanel.add(lblStrokes);
	}
	
	public void setStrokeLabel(int numStrokes){
		lblStrokes.setText("Stroke(s): " + numStrokes);
	}
	
	private void createButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Clear Canvas");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawingPanel.clearCanvas();
			}
		});
		buttonPane.add(btnNewButton);
		
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	private void createTextField() {
		kanjiTextField = new JTextField();
		kanjiTextField.setHorizontalAlignment(SwingConstants.CENTER);
		kanjiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 36));
		kanjiTextField.setBounds(27, 28, 170, 66);
		contentPanel.add(kanjiTextField);
		kanjiTextField.setColumns(10);
		
		kunyomiTextField = new JTextField();
		kunyomiTextField.addKeyListener(new KeyAdapter() {
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
						String text = kunyomiTextField.getText() + e.getKeyChar();
						kunyomiTextField.setText(KjConverter.convertToHiragana(text.toLowerCase()));
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
		kunyomiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		kunyomiTextField.setBounds(27, 122, 170, 34);
		contentPanel.add(kunyomiTextField);
		kunyomiTextField.setColumns(10);
		
		onyomiTextField = new JTextField();
		onyomiTextField.addKeyListener(new KeyAdapter() {
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
						String text = onyomiTextField.getText() + e.getKeyChar();
						onyomiTextField.setText(KjConverter.convertToKatakana(text.toLowerCase()));
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
		onyomiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		onyomiTextField.setBounds(27, 184, 170, 34);
		contentPanel.add(onyomiTextField);
		onyomiTextField.setColumns(10);
	}
}
