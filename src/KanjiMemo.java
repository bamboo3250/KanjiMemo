import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class KanjiMemo {

	public static final String MY_VOCAB_SCENE = "myVocabScene";
	public static final String MAIN_SCENE = "mainScene";
	
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
		
		createCardLayout();
		frame.pack();
	}
	
	private static void createCardLayout() {
		cards = new JPanel(new CardLayout());
		cards.add(KjMainScene.getInstance(),MAIN_SCENE);
		cards.add(KjMyVocabScene.getInstance(),MY_VOCAB_SCENE);
		
		showPanel(MAIN_SCENE);
		frame.setContentPane(cards);
	}

	public static void showPanel(String name){
		CardLayout layout = (CardLayout) cards.getLayout();
		layout.show(cards, name);
	}

	public static JFrame getInstance(){
		return frame;
	}
}
