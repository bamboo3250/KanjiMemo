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


public class KjAddDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField kanjiTextField;
	private JTextField kunyomiTextField;
	private JTextField onyomiTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KjAddDialog dialog = new KjAddDialog();
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
		setResizable(false);
		setTitle("Add a new Kanji");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		kanjiTextField = new JTextField();
		kanjiTextField.setHorizontalAlignment(SwingConstants.CENTER);
		kanjiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 36));
		kanjiTextField.setBounds(27, 28, 139, 66);
		contentPanel.add(kanjiTextField);
		kanjiTextField.setColumns(10);
		
		kunyomiTextField = new JTextField();
		kunyomiTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				switch (e.getKeyCode()){
				case KeyEvent.VK_BACK_SPACE:
					//kanjiTextField.setText("BACKSPACE PRESSED");
					e.consume();
					break;
				default:
					e.consume();
					break;
				}
				
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				String text;
				//kanjiTextField.setText(String.valueOf((int)e.getKeyChar()) + "_" + String.valueOf(e.getKeyCode()) + "_" + String.valueOf(KeyEvent.VK_BACK_SPACE));
				if ((!e.isControlDown()) && (!e.isAltDown())){
					switch (e.getKeyCode()){
					case KeyEvent.VK_BACK_SPACE:
					
						break;
					default:
						//kanjiTextField.setText("BACKSPACE PRESSED");
						text = kunyomiTextField.getText() + e.getKeyChar();
						kunyomiTextField.setText(KjConverter.convertToHiragana(text));
						e.consume();
						break;
					}
				}
			}
		});
		kunyomiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		kunyomiTextField.setBounds(27, 122, 139, 34);
		contentPanel.add(kunyomiTextField);
		kunyomiTextField.setColumns(10);
		
		onyomiTextField = new JTextField();
		onyomiTextField.setFont(new Font("MS Mincho", Font.PLAIN, 16));
		onyomiTextField.setBounds(27, 184, 139, 34);
		contentPanel.add(onyomiTextField);
		onyomiTextField.setColumns(10);
		
		JLabel lblKanji = new JLabel("Kanji");
		lblKanji.setBounds(27, 7, 46, 14);
		contentPanel.add(lblKanji);
		
		JLabel lblKunyomi = new JLabel("Kunyomi");
		lblKunyomi.setBounds(27, 101, 46, 14);
		contentPanel.add(lblKunyomi);
		
		JLabel lblOnyomi = new JLabel("Onyomi");
		lblOnyomi.setBounds(27, 163, 46, 14);
		contentPanel.add(lblOnyomi);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
}
