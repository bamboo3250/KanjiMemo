import java.awt.CardLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class KanjiMemo {

	public static final String MY_KANJI_SCENE = "myKanjiScene";
	public static final String MAIN_SCENE = "mainScene";
	public static final String KANJI_QUIZ_SCENE = "kanjiQuizScene";
	public static final String KUN_ON_QUIZ_SCENE = "kunOnQuizScene";
	
	private static final int START_POS_Y = 30;
	private static final int START_POS_X = 250;
	public static final int WINDOW_HEIGHT = 640;
	public static final int WINDOW_WIDTH = 960;
	
	private static JFrame frame;
	private static JPanel cards;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		frame = new JFrame();
		frame.setLocation(START_POS_X, START_POS_Y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		try {
			KjDataManager.loadKanjiFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		createCardLayout();
		frame.pack();
	}
	
	private static void createCardLayout() {
		cards = new JPanel(new CardLayout());
		cards.add(KjMainScene.getInstance(),MAIN_SCENE);
		cards.add(KjMyKanjiScene.getInstance(),MY_KANJI_SCENE);
		cards.add(KjKanjiQuizScene.getInstance(),KANJI_QUIZ_SCENE);
		cards.add(KjKunOnQuizScene.getInstance(),KUN_ON_QUIZ_SCENE);
		
		showPanel(MAIN_SCENE);
		frame.setContentPane(cards);
	}

	public static void showPanel(String name){
		CardLayout layout = (CardLayout) cards.getLayout();
		layout.show(cards, name);
		if (name.equals(MAIN_SCENE)){
			KjMainScene.getInstance();
		} else if (name.equals(MY_KANJI_SCENE)){
			KjMyKanjiScene.getInstance();
		}  
	}

	public static JFrame getInstance(){
		return frame;
	}
}
