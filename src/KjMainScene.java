import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class KjMainScene extends JPanel {

	private static final int BUTTON_FONT_SIZE = 20;
	private static final int BUTTON_SIZE_HEIGHT = 65;
	private static final int BUTTON_SIZE_WIDTH = 240;
	private static KjMainScene mainScene;
	private final Color BUTTON_COLOR = KjColor.FERN_COLOR;
	
	private JPanel buttonPanel;
	
	/**
	 * Create the panel.
	 */
	private KjMainScene() {
		setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(KanjiMemo.WINDOW_WIDTH,KanjiMemo.WINDOW_HEIGHT));
		
		createButtonPanel();

	}
	
	private void createButtonPanel(){
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		
		JButton btnMyVocab = new JButton("My Vocabulary");
		btnMyVocab.setName("vocab");
		btnMyVocab.addMouseListener(new KjMouseListener());
		btnMyVocab.setBounds(0, 52, BUTTON_SIZE_WIDTH, BUTTON_SIZE_HEIGHT);
		btnMyVocab.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnMyVocab.setBackground(BUTTON_COLOR);
		btnMyVocab.setBorderPainted(false);
		btnMyVocab.setFocusPainted(false);
		buttonPanel.add(btnMyVocab);
		
		JButton btnKanjiQuiz = new JButton("Kanji Quiz");
		btnKanjiQuiz.setName("kanji");
		btnKanjiQuiz.addMouseListener(new KjMouseListener());
		btnKanjiQuiz.setBounds(0, 169, BUTTON_SIZE_WIDTH, BUTTON_SIZE_HEIGHT);
		btnKanjiQuiz.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnKanjiQuiz.setBackground(BUTTON_COLOR);
		btnKanjiQuiz.setBorderPainted(false);
		btnKanjiQuiz.setFocusPainted(false);
		buttonPanel.add(btnKanjiQuiz);
		
		JButton btnKunyomiQuiz = new JButton("Kunyomi Quiz");
		btnKunyomiQuiz.setName("kunyomi");
		btnKunyomiQuiz.addMouseListener(new KjMouseListener());
		btnKunyomiQuiz.setBounds(0, 286, BUTTON_SIZE_WIDTH, BUTTON_SIZE_HEIGHT);
		btnKunyomiQuiz.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnKunyomiQuiz.setBackground(BUTTON_COLOR);
		btnKunyomiQuiz.setBorderPainted(false);
		btnKunyomiQuiz.setFocusPainted(false);
		buttonPanel.add(btnKunyomiQuiz);
		
		JButton btnOnyomiQuiz = new JButton("Onyomi Quiz");
		btnOnyomiQuiz.setName("onyomi");
		btnOnyomiQuiz.addMouseListener(new KjMouseListener());
		btnOnyomiQuiz.setBounds(0, 403, BUTTON_SIZE_WIDTH, BUTTON_SIZE_HEIGHT);
		btnOnyomiQuiz.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnOnyomiQuiz.setBackground(BUTTON_COLOR);
		btnOnyomiQuiz.setBorderPainted(false);
		btnOnyomiQuiz.setFocusPainted(false);
		buttonPanel.add(btnOnyomiQuiz);
		
		JButton btnRandomQuiz = new JButton("Random Quiz");
		btnRandomQuiz.setName("random");
		btnRandomQuiz.addMouseListener(new KjMouseListener());
		btnRandomQuiz.setBounds(0, 520, BUTTON_SIZE_WIDTH, BUTTON_SIZE_HEIGHT);
		btnRandomQuiz.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnRandomQuiz.setBackground(BUTTON_COLOR);
		btnRandomQuiz.setBorderPainted(false);
		btnRandomQuiz.setFocusPainted(false);
		buttonPanel.add(btnRandomQuiz);
		
		buttonPanel.setPreferredSize(new Dimension(BUTTON_SIZE_WIDTH + 40, BUTTON_SIZE_HEIGHT));
		add(buttonPanel, BorderLayout.EAST);
	}
	
	private class KjMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			if (button.getName().equalsIgnoreCase("vocab")){
				KanjiMemo.showPanel(KanjiMemo.MY_VOCAB_SCENE);
			}
		}
	}
	
	public static KjMainScene getInstance(){
		if (mainScene==null){
			mainScene = new KjMainScene();
		}
		return mainScene; 
	}
}
