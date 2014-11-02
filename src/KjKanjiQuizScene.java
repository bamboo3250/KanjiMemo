import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;


public class KjKanjiQuizScene extends JPanel {
	private static final int ICON_POS_Y2 = 118;
	private static final int ICON_POS_Y1 = 70;
	private static final int ICON_POS_X = 911;
	private static final int BUTTON_FONT_SIZE = 20;
	private static final Color ACTIVATED_BUTTON_COLOR =  KjColor.FERN_COLOR;
	private static final Color DEACTIVATED_BUTTON_COLOR =  KjColor.IRON_COLOR;

	private static KjKanjiQuizScene kanjiQuizScene;
	private JTextField onyomiTextField;
	private JTextField kunyomiTextField;
	private JTextPane kanjiTextPane;
	private KjKanji chosenWord;
	private JLabel lblKunyomiCheck;
	private JLabel lblOnyomiCheck;
	private JTextPane kunAnswer;
	private JTextPane onAnswer;
	private JButton btnCheck;
	private JButton btnAnswer;
	private JLabel lblKun;
	private JLabel lblOn;
	private boolean checked;
	
	public static KjKanjiQuizScene getInstance(){
		if (kanjiQuizScene==null){
			kanjiQuizScene = new KjKanjiQuizScene();
		}
		return kanjiQuizScene;
	}
	/**
	 * Create the panel.
	 */
	private KjKanjiQuizScene() {
		setPreferredSize(new Dimension(KanjiMemo.WINDOW_WIDTH,KanjiMemo.WINDOW_HEIGHT));
		setLayout(null);
		
		kanjiTextPane = new JTextPane();
		kanjiTextPane.setEditable(false);
		kanjiTextPane.setBounds(510, 65, 140, 140);
		kanjiTextPane.setFont(new Font("MS Mincho", Font.PLAIN, 134));
		add(kanjiTextPane);
		
		kunyomiTextField = new KjHiraganaTextField();
		kunyomiTextField.setColumns(10);
		kunyomiTextField.setBounds(698, 65, 203, 37);
		kunyomiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		add(kunyomiTextField);
		
		onyomiTextField = new KjKatakanaTextField();
		onyomiTextField.setBounds(698, 113, 203, 37);
		onyomiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		onyomiTextField.setColumns(10);
		add(onyomiTextField);
		
		btnCheck = new JButton("Check");
		btnCheck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnCheck.setName("check");
		btnCheck.addMouseListener(new KjMouseListener());
		btnCheck.setBounds(685, 161, 100, 44);
		btnCheck.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnCheck.setBackground(ACTIVATED_BUTTON_COLOR);
		btnCheck.setBorderPainted(false);
		btnCheck.setFocusPainted(false);
		add(btnCheck);
		
		JButton btnNext = new JButton("Next");
		btnNext.addMouseListener(new KjMouseListener());
		btnNext.setName("next");
		btnNext.setBounds(801, 161, 100, 44);
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnNext.setBackground(ACTIVATED_BUTTON_COLOR);
		btnNext.setBorderPainted(false);
		btnNext.setFocusPainted(false);
		add(btnNext);
		
		JButton btnBack = new JButton("Back");
		btnBack.setName("back");
		btnBack.addMouseListener(new KjMouseListener());
		btnBack.setBounds(801, 565, 100, 44);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnBack.setBackground(ACTIVATED_BUTTON_COLOR);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		add(btnBack);
		
		btnAnswer = new JButton("Answer");
		btnAnswer.setName("answer");
		btnAnswer.addMouseListener(new KjMouseListener());
		btnAnswer.setBounds(685, 216, 100, 44);
		btnAnswer.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnAnswer.setBackground(ACTIVATED_BUTTON_COLOR);
		btnAnswer.setBorderPainted(false);
		btnAnswer.setFocusPainted(false);
		add(btnAnswer);
		
		lblKunyomiCheck = new JLabel("");
		lblKunyomiCheck.setBounds(ICON_POS_X, ICON_POS_Y1, 32, 32);
		add(lblKunyomiCheck);
		
		lblOnyomiCheck = new JLabel("");
		lblOnyomiCheck.setBounds(ICON_POS_X, ICON_POS_Y2, 32, 32);
		add(lblOnyomiCheck);
		
		kunAnswer = new JTextPane();
		kunAnswer.setBounds(510, 216, 140, 63);
		kunAnswer.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		kunAnswer.setBackground(KjColor.WHITE_SMOKE_COLOR);
		kunAnswer.setEditable(false);
		add(kunAnswer);
		
		onAnswer = new JTextPane();
		onAnswer.setBounds(510, 290, 140, 63);
		onAnswer.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		onAnswer.setBackground(KjColor.WHITE_SMOKE_COLOR);
		onAnswer.setEditable(false);
		add(onAnswer);
		
		lblKun = new JLabel("Kun:");
		lblKun.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblKun.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKun.setBounds(654, 64, 43, 37);
		add(lblKun);
		
		lblOn = new JLabel("On:");
		lblOn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOn.setBounds(654, 112, 43, 37);
		add(lblOn);
		
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
			kanjiTextPane.setText(chosenWord.getPresentString());
			
			kunyomiTextField.setText("");
			onyomiTextField.setText("");
			
			lblKunyomiCheck.setIcon(null);
			lblOnyomiCheck.setIcon(null);
			kunAnswer.setText("");
			onAnswer.setText("");
			
			btnCheck.setEnabled(true);
			btnCheck.setBackground(ACTIVATED_BUTTON_COLOR);
			btnAnswer.setEnabled(true);
			btnAnswer.setBackground(ACTIVATED_BUTTON_COLOR);
			
			checked = false;
		}
	}

	private class KjMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			if (button.getName().equalsIgnoreCase("back")){
				KanjiMemo.showPanel(KanjiMemo.MAIN_SCENE);
			} else if (button.getName().equalsIgnoreCase("next")){
				if (!checked){
					chosenWord.increaseTotalAnswer();
				}
				chooseRandomKanji();
			} else if (button.getName().equalsIgnoreCase("check")){
				if (btnCheck.isEnabled()){
					String[] kunTextList = kunyomiTextField.getText().split(",");
					String[] onTextList = onyomiTextField.getText().split(",");
					
					boolean checkKun = checkKunyomi(chosenWord, kunTextList);
					boolean checkOn = checkOnyomi(chosenWord, onTextList);
					
					if (checkKun){
						lblKunyomiCheck.setIcon(new ImageIcon("./images/correct_32.png"));
					} else {
						lblKunyomiCheck.setIcon(new ImageIcon("./images/wrong_32.png"));
					}
					
					if (checkOn){
						lblOnyomiCheck.setIcon(new ImageIcon("./images/correct_32.png"));
					} else {
						lblOnyomiCheck.setIcon(new ImageIcon("./images/wrong_32.png"));
					}
					
					if (!checked){
						chosenWord.increaseTotalAnswer();
						if (checkKun && checkOn){
							chosenWord.increaseCorrectAnswer();
						}
						checked = true;
					}
				}
			} else if (button.getName().equalsIgnoreCase("answer")){
				btnCheck.setEnabled(false);
				btnCheck.setBackground(DEACTIVATED_BUTTON_COLOR);
				btnAnswer.setEnabled(false);
				btnAnswer.setBackground(DEACTIVATED_BUTTON_COLOR);
				
				kunAnswer.setText(chosenWord.kunListToString());
				onAnswer.setText(chosenWord.onListToString());
				
				if (!checked){
					chosenWord.increaseTotalAnswer();
					checked = true;
				}
			}
		}

		private boolean checkKunyomi(KjKanji chosenWord, String[] kunTextList) {
			if ((chosenWord.getKunyomiList().size()>0) && (kunTextList.length==0)){
				return false;
			}
			for(int i=0;i<kunTextList.length;i++){
				if (!chosenWord.hasKunyomi(kunTextList[i])){
					return false;
				}
			}
			return true;
		}
		
		private boolean checkOnyomi(KjKanji chosenWord, String[] onTextList) {
			if ((chosenWord.getOnyomiList().size()>0) && (onTextList.length==0)){
				return false;
			}
			for(int i=0;i<onTextList.length;i++){
				if (!chosenWord.hasOnyomi(onTextList[i])){
					return false;
				}
			}
			return true;
		}
	}
}
