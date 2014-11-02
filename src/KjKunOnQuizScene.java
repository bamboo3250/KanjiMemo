import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JLabel;


public class KjKunOnQuizScene extends JPanel {
	private static final int BUTTON_FONT_SIZE = 20;
	private static final Color ACTIVATED_BUTTON_COLOR =  KjColor.FERN_COLOR;
	//private static final Color DEACTIVATED_BUTTON_COLOR =  KjColor.IRON_COLOR;
	
	private static KjKunOnQuizScene kunOnQuiz;
	private KjDrawingPanel drawingPanel;
	private KjKanji chosenWord;
	private JTextPane kunText;
	private JTextPane onText;
	private JLabel checkIcon;
	private JTextPane answerText;
	private JLabel checkNumber;
	private KjDrawingPanel answerDrawing;
	private boolean correct;
	
	public static KjKunOnQuizScene getInstance(){
		if (kunOnQuiz==null){
			kunOnQuiz = new KjKunOnQuizScene();
		}
		return kunOnQuiz;
	}
	
	/**
	 * Create the panel.
	 */
	private KjKunOnQuizScene() {
		setPreferredSize(new Dimension(KanjiMemo.WINDOW_WIDTH,KanjiMemo.WINDOW_HEIGHT));
		setLayout(null);
		
		kunText = new JTextPane();
		kunText.setEditable(false);
		kunText.setBounds(545, 94, 184, 27);
		kunText.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		add(kunText);
		
		onText = new JTextPane();
		onText.setEditable(false);
		onText.setBounds(545, 147, 184, 27);
		onText.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		add(onText);
		
		drawingPanel = new KjDrawingPanel();
		drawingPanel.setBounds(739, 94, 180, 180);
		add(drawingPanel);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.addMouseListener(new KjMouseListener());
		btnCheck.setName("check");
		btnCheck.setBounds(545, 185, 89, 23);
		btnCheck.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCheck.setBackground(ACTIVATED_BUTTON_COLOR);
		btnCheck.setBorderPainted(false);
		btnCheck.setFocusPainted(false);
		add(btnCheck);
		
		JButton btnNext = new JButton("Next");
		btnNext.addMouseListener(new KjMouseListener());
		btnNext.setName("next");
		btnNext.setBounds(644, 185, 89, 23);
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext.setBackground(ACTIVATED_BUTTON_COLOR);
		btnNext.setBorderPainted(false);
		btnNext.setFocusPainted(false);
		add(btnNext);
		
		JButton btnAnswer = new JButton("Answer");
		btnAnswer.addMouseListener(new KjMouseListener());
		btnAnswer.setName("answer");
		btnAnswer.setBounds(545, 219, 89, 23);
		btnAnswer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAnswer.setBackground(ACTIVATED_BUTTON_COLOR);
		btnAnswer.setBorderPainted(false);
		btnAnswer.setFocusPainted(false);
		add(btnAnswer);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.addMouseListener(new KjMouseListener());
		btnUndo.setName("undo");
		btnUndo.setBounds(739, 285, 89, 23);
		btnUndo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUndo.setBackground(ACTIVATED_BUTTON_COLOR);
		btnUndo.setBorderPainted(false);
		btnUndo.setFocusPainted(false);
		add(btnUndo);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setName("clear");
		btnClear.addMouseListener(new KjMouseListener());
		btnClear.setBounds(830, 285, 89, 23);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear.setBackground(ACTIVATED_BUTTON_COLOR);
		btnClear.setBorderPainted(false);
		btnClear.setFocusPainted(false);
		add(btnClear);
		
		JButton btnBack = new JButton("Back");
		btnBack.setName("back");
		btnBack.addMouseListener(new KjMouseListener());
		btnBack.setBounds(808, 545, 111, 53);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnBack.setBackground(ACTIVATED_BUTTON_COLOR);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		add(btnBack);
		
		checkIcon = new JLabel("");
		checkIcon.setBounds(654, 219, 62, 46);
		add(checkIcon);
		
		answerText = new JTextPane();
		answerText.setBounds(739, 332, 180, 180);
		answerText.setFont(new Font("MS Mincho", Font.PLAIN, 174));
		add(answerText);
		
		checkNumber = new JLabel("");
		checkNumber.setBounds(630, 276, 99, 14);
		add(checkNumber);
		
		answerDrawing = new KjDrawingPanel();
		answerDrawing.setBounds(545, 332, 180, 180);
		add(answerDrawing);
		
		chooseRandomKanji();
	}
	
	private void chooseRandomKanji() {
		int size = KjStorage.getInstance().size();
		if (size>0){
			KjKanji word;
			do {
				word = KjStorage.getInstance().getRandomWithPriority();
			} while ((chosenWord == word) && (size!=1));
			chosenWord = word;
			
			kunText.setText(chosenWord.kunListToString());
			onText.setText(chosenWord.onListToString());
			answerText.setText("");
			drawingPanel.clearCanvas();
			answerDrawing.clearCanvas();
			checkIcon.setIcon(null);
			checkNumber.setText("");
			
			correct = false;
		}
		
	}

	private class KjMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			if (button.getName().equalsIgnoreCase("back")){
				KanjiMemo.showPanel(KanjiMemo.MAIN_SCENE);
			} else if (button.getName().equalsIgnoreCase("undo")){
				drawingPanel.undo();
			} else if (button.getName().equalsIgnoreCase("clear")){
				drawingPanel.clearCanvas();
			} else if (button.getName().equalsIgnoreCase("check")){
				ArrayList<KjStroke> answer = chosenWord.getSimplifiedStrokeList();
				if (KjKanjiComparator.compareKanji(answer, drawingPanel.getSimplified())<KjKanjiComparator.MAXIMUM_DIFFERENCE){
					correct = true;
					checkIcon.setIcon(new ImageIcon("./images/correct_32.png"));
				} else {
					checkIcon.setIcon(new ImageIcon("./images/wrong_32.png"));
				}
				checkNumber.setText(""+KjKanjiComparator.compareKanji(answer, drawingPanel.getSimplified()));
			} else if (button.getName().equalsIgnoreCase("next")){
				chosenWord.increaseTotalAnswer();
				if (correct){
					chosenWord.increaseCorrectAnswer();
				}
				
				chooseRandomKanji();
			} else if (button.getName().equalsIgnoreCase("answer")){
				answerText.setText(chosenWord.getPresentString());
				answerDrawing.setRawStroke(chosenWord.getRawStrokeList());
				answerDrawing.setSimplified(chosenWord.getSimplifiedStrokeList());
				answerDrawing.repaint();
			}
		}
	}
}
