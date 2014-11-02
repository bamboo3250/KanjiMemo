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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KjAddDialog extends JDialog {
	
	private KjAddDialog addDialog;
	private final JPanel contentPanel = new JPanel();
	private JTextField kanjiTextField;
	private JTextField kunyomiTextField;
	private JTextField onyomiTextField;
	private KjDrawingPanel drawingPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KjAddDialog dialog = new KjAddDialog();//.getInstance();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public KjAddDialog() {
		
		addDialog = this;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(JDialog.ModalityType.APPLICATION_MODAL );
		
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
		
		JLabel lblStrokes = drawingPanel.getStrokeLabel();
		lblStrokes.setBounds(242, 213, 129, 14);
		contentPanel.add(lblStrokes);
		
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
					String word = kunList[i].trim();
					if (!word.equals("")){
						newKanji.addKunyomi(word);
					}
				}
				
				String[] onList = onyomiTextField.getText().split(",");
				for(int i=0;i<onList.length;i++){
					String word = onList[i].trim();
					if (!word.equals("")){
						newKanji.addOnyomi(word);
					}
				}
				KjStorage.getInstance().addKanji(newKanji);
				KjMyKanjiScene.getInstance().updateList();
				KjMyKanjiScene.getInstance().selectTheLast();
				addDialog.dispose();
			}
		});
		//okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addDialog.dispose();
			}
		});
		//cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	private void createTextField() {
		kanjiTextField = new JTextField();
		kanjiTextField.setHorizontalAlignment(SwingConstants.CENTER);
		kanjiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 36));
		kanjiTextField.setBounds(27, 28, 170, 66);
		contentPanel.add(kanjiTextField);
		kanjiTextField.setColumns(10);
		
		kunyomiTextField = new KjHiraganaTextField();
		kunyomiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		kunyomiTextField.setBounds(27, 122, 170, 34);
		kunyomiTextField.setColumns(10);
		contentPanel.add(kunyomiTextField);
		
		onyomiTextField = new KjKatakanaTextField();
		onyomiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		onyomiTextField.setBounds(27, 184, 170, 34);
		onyomiTextField.setColumns(10);
		contentPanel.add(onyomiTextField);
		
	}
}
