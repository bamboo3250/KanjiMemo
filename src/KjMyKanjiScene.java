import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class KjMyKanjiScene extends JPanel {

	private static final int BUTTON_HEIGHT = 60;
	private static final int BUTTON_WIDTH = 135;
	private static final int BUTTON_PANEL_WIDTH = 170;
	private static final int BUTTON_FONT_SIZE = 20;
	private static final Color ACTIVATED_BUTTON_COLOR =  KjColor.FERN_COLOR;
	private static final Color DEACTIVATED_BUTTON_COLOR =  KjColor.IRON_COLOR;
	
	private static KjMyKanjiScene myVocabScene;
	private JList<String> kanjiList;
	private JButton btnDelete;
	private JButton btnUp;
	private JButton btnDown;
	private ArrayList<KjKanji> showList;
	/**
	 * Create the panel.
	 */
	private KjMyKanjiScene() {
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(KanjiMemo.WINDOW_WIDTH,KanjiMemo.WINDOW_HEIGHT));
		
		createButtonPanel();
		createScrollPanel();
	}

	public static KjMyKanjiScene getInstance(){
		if (myVocabScene==null){
			myVocabScene = new KjMyKanjiScene();
		}
		myVocabScene.refresh();
		return myVocabScene;
	}
	
	public int getSelectedIndex(){
		return kanjiList.getSelectedIndex();
	}
	
	public int getSelectedId(){
		return showList.get(kanjiList.getSelectedIndex()).getId();
	}
	
	private void createScrollPanel() {
		kanjiList = new JList<String>();
		kanjiList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount()==2){
					int selected = kanjiList.getSelectedIndex();
					if (selected >= 0){
						KjEditDialog editDialog = new KjEditDialog(showList.get(selected));
						editDialog.setVisible(true);
					}
				}
			}
		});
		kanjiList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				int selected = kanjiList.getSelectedIndex();
				if (selected != -1){
					btnDelete.setEnabled(true);
					btnDelete.setBackground(ACTIVATED_BUTTON_COLOR);
					if (selected>0){
						btnUp.setEnabled(true);
						btnUp.setBackground(ACTIVATED_BUTTON_COLOR);
					} else {
						btnUp.setEnabled(false);
						btnUp.setBackground(DEACTIVATED_BUTTON_COLOR);
					}
					if (selected < kanjiList.getModel().getSize()-1){
						btnDown.setEnabled(true);
						btnDown.setBackground(ACTIVATED_BUTTON_COLOR);
					} else {
						btnDown.setEnabled(false);
						btnDown.setBackground(DEACTIVATED_BUTTON_COLOR);
					}
				}
			}
		});
		kanjiList.setBackground(Color.white);
		kanjiList.setFont(new Font("MS Mincho",Font.PLAIN,34));
		updateList();
		
		JScrollPane scrollPane = new JScrollPane(kanjiList);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.setBorder(null);
		
		add(scrollPane, BorderLayout.CENTER);
		
	}

	private void createButtonPanel() {
		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, 640));
		
		JButton btnAddNewKanji = new JButton("Add");
		btnAddNewKanji.setName("add");
		btnAddNewKanji.setBounds(10, 56, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnAddNewKanji.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnAddNewKanji.setBackground(ACTIVATED_BUTTON_COLOR);
		btnAddNewKanji.setBorderPainted(false);
		btnAddNewKanji.setFocusPainted(false);
		btnAddNewKanji.addMouseListener(new KjMouseListener());
		panel.add(btnAddNewKanji);
		
		btnDelete = new JButton("Delete");
		btnDelete.setName("delete");
		btnDelete.setBounds(10, 404, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnDelete.setBackground(DEACTIVATED_BUTTON_COLOR);
		btnDelete.setEnabled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setFocusPainted(false);
		btnDelete.addMouseListener(new KjMouseListener());
		panel.add(btnDelete);
		
		JButton btnBack = new JButton("Back");
		btnBack.setName("back");
		btnBack.setBounds(10, 520, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnBack.setBackground(ACTIVATED_BUTTON_COLOR);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.addMouseListener(new KjMouseListener());
		panel.add(btnBack);
		
		btnUp = new JButton("Up");
		btnUp.setName("up");
		btnUp.setBounds(10, 172, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnUp.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnUp.setBackground(DEACTIVATED_BUTTON_COLOR);
		btnUp.setEnabled(false);
		btnUp.setBorderPainted(false);
		btnUp.setFocusPainted(false);
		btnUp.addMouseListener(new KjMouseListener());
		panel.add(btnUp);
		
		btnDown = new JButton("Down");
		btnDown.setName("down");
		btnDown.setBounds(10, 288, BUTTON_WIDTH, BUTTON_HEIGHT);
		btnDown.setFont(new Font("Tahoma", Font.PLAIN, BUTTON_FONT_SIZE));
		btnDown.setBackground(DEACTIVATED_BUTTON_COLOR);
		btnDown.setEnabled(false);
		btnDown.setBorderPainted(false);
		btnDown.setFocusPainted(false);
		btnDown.addMouseListener(new KjMouseListener());
		panel.add(btnDown);
	}
	
	private class KjMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton button = (JButton) arg0.getSource();
			if (button.getName().equalsIgnoreCase("back")){
				KanjiMemo.showPanel(KanjiMemo.MAIN_SCENE);
			} else if (button.getName().equalsIgnoreCase("add")){
				KjAddDialog addPanel = new KjAddDialog();//(KanjiMemo.getInstance(), "Add Kanji", false);
				addPanel.setVisible(true);
			} else if (button.getName().equalsIgnoreCase("up")){
				int selected = kanjiList.getSelectedIndex();
				if ((selected != -1) && (selected>0)){
					ArrayList<KjKanji> list = KjStorage.getInstance().getList();
					KjKanji selectedWord = list.get(selected);
					int id1 = selectedWord.getId();
					int id2 = list.get(selected-1).getId();
					selectedWord.setId(id2);
					list.get(selected-1).setId(id1);
					
					list.remove(selected);
					list.add(selected-1, selectedWord);
					updateList();
					kanjiList.setSelectedIndex(selected-1);
				}
			} else if (button.getName().equalsIgnoreCase("down")){
				int selected = kanjiList.getSelectedIndex();
				if ((selected != -1) && (selected<kanjiList.getModel().getSize()-1)){
					ArrayList<KjKanji> list = KjStorage.getInstance().getList();
					KjKanji selectedWord = list.get(selected);
					int id1 = selectedWord.getId();
					int id2 = list.get(selected+1).getId();
					selectedWord.setId(id2);
					list.get(selected+1).setId(id1);
					
					list.remove(selected);
					list.add(selected+1, selectedWord);
					updateList();
					kanjiList.setSelectedIndex(selected+1);
				}
			} else if (button.getName().equalsIgnoreCase("delete")){
				int selected = kanjiList.getSelectedIndex();
				if (selected != -1){
					ArrayList<KjKanji> list = KjStorage.getInstance().getList();
					KjKanji selectedWord = list.get(selected);
					int n = JOptionPane.showConfirmDialog(KanjiMemo.getInstance(), 
							"Do you want to delete " + selectedWord.getPresentString() + "?", "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
					if (n==JOptionPane.OK_OPTION){
						list.remove(selected);
						for(int i=selected;i<list.size();i++){
							list.get(i).setId(list.get(i).getId()-1);
						}
						updateList();
					}
				}
			}
		}
	}

	private void saveKanjiToFile() {
		try {
			KjDataManager.saveKanjiToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateList() {
		int size = KjStorage.getInstance().size();
		String[] list = new String[size];
		int index = 0;
		showList = new ArrayList<KjKanji>();
		
		for(int i=1;i<=KjStorage.getInstance().getHighestId();i++){
			KjKanji word = KjStorage.getInstance().find(i);
			if (word!=null){
				if (index<size){
					showList.add(word);
					list[index] = word.toString();
					index++; 
				}
			}
		}
		kanjiList.setListData(list);
		saveKanjiToFile();
	}
	
	public void refresh(){
		updateList();
	}
	
	public void select(int pos){
		kanjiList.setSelectedIndex(pos);
		kanjiList.ensureIndexIsVisible(pos);
	}
	
	public void selectTheLast(){
		select(kanjiList.getModel().getSize()-1);
	}
}
