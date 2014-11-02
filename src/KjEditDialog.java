import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class KjEditDialog extends JDialog {

	private KjEditDialog editDialog;
	private final JPanel contentPanel = new JPanel();
	private JTextField kanjiTextField;
	private JTextField kunyomiTextField;
	private JTextField onyomiTextField;
	private KjDrawingPanel drawingPanel;
	private KjKanji editKanji;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KjDataManager.loadKanjiFromFile();
			KjEditDialog dialog = new KjEditDialog(KjStorage.getInstance().getKanji(0));//.getInstance();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public KjEditDialog(KjKanji word) {
		if (word!=null){
			editKanji = word;
			editDialog = this;
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setModalityType(JDialog.ModalityType.APPLICATION_MODAL );
			
			setResizable(false);
			setTitle("Add a new Kanji");
			setBounds(100, 100, 450, 300);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			
			createDrawingPanel(word);
			createTextField(word);
			createLabels(word);
			createButtonPanel();
		} else {
			this.dispose();
		}
	}

	
	
	private void createDrawingPanel(KjKanji word) {
		drawingPanel = new KjDrawingPanel();
		drawingPanel.setBounds(254, 28, 180, 180);
		drawingPanel.setSimplified(word.getSimplifiedStrokeList());
		drawingPanel.setRawStroke(word.getRawStrokeList());
		drawingPanel.repaint();
		contentPanel.add(drawingPanel);
	}

	private void createLabels(KjKanji word) {
		JLabel lblKanji = new JLabel("Kanji");
		lblKanji.setBounds(27, 7, 46, 14);
		contentPanel.add(lblKanji);
		
		JLabel lblAnswer = new JLabel("Correct: " + word.getCorrectAnswer() + "   Total: " + word.getTotalAnswer());
		lblAnswer.setBounds(67, 7, 106, 14);
		contentPanel.add(lblAnswer);
		
		JLabel lblKunyomi = new JLabel("Kunyomi");
		lblKunyomi.setBounds(27, 101, 75, 14);
		contentPanel.add(lblKunyomi);
		
		JLabel lblOnyomi = new JLabel("Onyomi");
		lblOnyomi.setBounds(27, 163, 46, 14);
		contentPanel.add(lblOnyomi);
		
		JLabel lblDrawKanjiHere = new JLabel("Draw Kanji here");
		lblDrawKanjiHere.setBounds(242, 7, 101, 14);
		contentPanel.add(lblDrawKanjiHere);
		
		JLabel lblStrokes = drawingPanel.getStrokeLabel();
		lblStrokes.setBounds(242, 213, 129, 14);
		contentPanel.add(lblStrokes);
		
		
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
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				KjKanji newKanji = new KjKanji();
				newKanji.setPresentString(kanjiTextField.getText());
				newKanji.setRawStrokeList(drawingPanel.getRawStroke());
				newKanji.setSimplifiedStrokeList(drawingPanel.getSimplified());
				
				String[] kunList = kunyomiTextField.getText().split(",");
				for(int i=0;i<kunList.length;i++){
					newKanji.addKunyomi(kunList[i].trim());
				}
				
				String[] onList = onyomiTextField.getText().split(",");
				for(int i=0;i<onList.length;i++){
					newKanji.addOnyomi(onList[i].trim());
				}
				
				int selected = KjStorage.getInstance().getList().indexOf(editKanji);
				if (selected>-1){
					newKanji.setId(editKanji.getId());
					newKanji.setCorrectAnswer(editKanji.getCorrectAnswer());
					newKanji.setTotalAnswer(editKanji.getTotalAnswer());
					KjStorage.getInstance().deleteKanji(selected);
					KjStorage.getInstance().addKanji(selected, newKanji);
					KjMyKanjiScene.getInstance().updateList();
				}
				editDialog.dispose();
			}
		});
		//okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editDialog.dispose();
			}
		});
		//cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				drawingPanel.undo();
			}
		});
		btnUndo.setBounds(359, 211, 75, 23);
		contentPanel.add(btnUndo);
	}

	private void createTextField(KjKanji word) {
		kanjiTextField = new JTextField();
		kanjiTextField.setHorizontalAlignment(SwingConstants.CENTER);
		kanjiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 36));
		kanjiTextField.setBounds(27, 28, 170, 66);
		kanjiTextField.setColumns(10);
		kanjiTextField.setText(word.getPresentString());
		contentPanel.add(kanjiTextField);
		
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
		kunyomiTextField.setColumns(10);
		
		String text = "";
		for(int i=0;i<word.getKunyomiList().size()-1;i++){
			text = text + word.getKunyomiList().get(i) + ",";
		}
		if (word.getKunyomiList().size()>0) {
			text = text + word.getKunyomiList().get(word.getKunyomiList().size()-1);
		}
		kunyomiTextField.setText(text);
		contentPanel.add(kunyomiTextField);
		
		
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
		text = "";
		for(int i=0;i<word.getOnyomiList().size()-1;i++){
			text = text + word.getOnyomiList().get(i) + ",";
		}
		if (word.getOnyomiList().size()>0){
			text = text + word.getOnyomiList().get(word.getOnyomiList().size()-1);
		}
		onyomiTextField.setText(text);
	}

}
