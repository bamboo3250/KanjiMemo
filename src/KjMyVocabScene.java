import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

public class KjMyVocabScene extends JPanel {

	private static final int BUTTON_HEIGHT = 60;
	private static final int BUTTON_WIDTH = 135;
	private static final int BUTTON_PANEL_WIDTH = 150;
	private static final int BUTTON_FONT_SIZE = 20;
	private static final Color BUTTON_COLOR =  KjColor.FERN_COLOR;;

	private static KjMyVocabScene myVocabScene;
	/**
	 * Create the panel.
	 */
	private KjMyVocabScene() {
		setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(KanjiMemo.WINDOW_WIDTH,KanjiMemo.WINDOW_HEIGHT));
		
		createButtonPanel();
		createScrollPanel();
		
	}

	public static KjMyVocabScene getInstance(){
		if (myVocabScene==null){
			myVocabScene = new KjMyVocabScene();
		}
		return myVocabScene;
	}
	
	private void createScrollPanel() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.setBorder(null);
		
		
		add(scrollPane, BorderLayout.CENTER);
		
		JList<String> list = new JList<String>();
		scrollPane.add(list);
	}

	private void createButtonPanel() {
		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, KanjiMemo.WINDOW_HEIGHT));
		
		JButton btnAddNewKanji = new JButton("Add");
		btnAddNewKanji.setName("add");
		btnAddNewKanji.setBounds(10, 115, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnAddNewKanji.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnAddNewKanji.setBackground(BUTTON_COLOR);
		btnAddNewKanji.setBorderPainted(false);
		btnAddNewKanji.setFocusPainted(false);
		btnAddNewKanji.addMouseListener(new KjMouseListener());
		panel.add(btnAddNewKanji);
		
		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.setName("clear");
		btnClearAll.setBounds(10, 290, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnClearAll.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnClearAll.setBackground(BUTTON_COLOR);
		btnClearAll.setBorderPainted(false);
		btnClearAll.setFocusPainted(false);
		btnClearAll.addMouseListener(new KjMouseListener());
		panel.add(btnClearAll);
		
		JButton btnBack = new JButton("Back");
		btnBack.setName("back");
		btnBack.setBounds(10, 465, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnBack.setBackground(BUTTON_COLOR);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.addMouseListener(new KjMouseListener());
		panel.add(btnBack);
	}
	
	private class KjMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			if (button.getName().equalsIgnoreCase("back")){
				KanjiMemo.showPanel(KanjiMemo.MAIN_SCENE);
			} else if (button.getName().equalsIgnoreCase("add")){
				JDialog addPanel = new JDialog(KanjiMemo.getInstance(), "Add Kanji", false);
				
				addPanel.pack();
				addPanel.setVisible(true);
			}
		}
	}
}
