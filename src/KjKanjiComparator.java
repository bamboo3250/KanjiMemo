import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;


public class KjKanjiComparator extends JDialog {

	private static final int MAXIMUM_DIFFERENCE = 10;
	private final JPanel contentPanel = new JPanel();
	private KjDrawingPanel drawingPanel2;
	private KjDrawingPanel drawingPanel1;
	private JLabel lblResult;
	private JLabel lblDifference;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KjKanjiComparator dialog = new KjKanjiComparator();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public KjKanjiComparator() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			drawingPanel1 = new KjDrawingPanel();
			drawingPanel1.setBounds(10, 11, 180, 180);
			contentPanel.add(drawingPanel1);
		}
		{
			drawingPanel2 = new KjDrawingPanel();
			drawingPanel2.setBounds(244, 11, 180, 180);
			contentPanel.add(drawingPanel2);
		}
		{
			JButton btnClear1 = new JButton("Clear");
			btnClear1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					drawingPanel1.clearCanvas();
				}
			});
			btnClear1.setBounds(10, 194, 69, 23);
			contentPanel.add(btnClear1);
		}
		{
			JButton btnClear2 = new JButton("Clear");
			btnClear2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					drawingPanel2.clearCanvas();
				}
			});
			btnClear2.setBounds(355, 194, 69, 23);
			contentPanel.add(btnClear2);
		}
		{
			JButton btnCompare = new JButton("Compare");
			btnCompare.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					double result = compareKanji(drawingPanel1.getSimplified(),drawingPanel2.getSimplified()); 
					if (result<MAXIMUM_DIFFERENCE){
						lblResult.setText("O");
						lblDifference.setText("" + result);
					} else {
						lblResult.setText("X");
						lblDifference.setText("" + result);
					}
				}
			});
			btnCompare.setBounds(172, 202, 89, 23);
			contentPanel.add(btnCompare);
		}
		{
			lblResult = new JLabel("Result");
			lblResult.setBounds(200, 79, 46, 14);
			contentPanel.add(lblResult);
		}
		{
			JButton btnUndo1 = new JButton("Undo");
			btnUndo1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					drawingPanel1.undo();
				}
			});
			btnUndo1.setBounds(85, 194, 69, 23);
			contentPanel.add(btnUndo1);
		}
		{
			JButton btnUndo2 = new JButton("Undo");
			btnUndo2.setBounds(281, 194, 69, 23);
			btnUndo2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					drawingPanel2.undo();
				}
			});
			contentPanel.add(btnUndo2);
		}
		{
			lblDifference = new JLabel("Difference");
			lblDifference.setBounds(200, 108, 46, 14);
			contentPanel.add(lblDifference);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected double compareKanji(ArrayList<KjStroke> word1, ArrayList<KjStroke> word2) {
		double difference = 0;
		
		if (word1.size()!=word2.size()){
			return 10000;
			//return false;
		} else {
			int numStrokes = word2.size();
			
			for(int i=0;i<numStrokes;i++){
				if (word1.get(i).size() != word2.get(i).size()){
					//return false;
					return 9999;
				} else {
					double differenceInStroke = 0;
					int numPointsOfStroke = word2.get(i).size();
					for(int j=0;j<numPointsOfStroke-1;j++){
						double theta1 = Math.atan2(word1.get(i).getPoint(j+1).y - word1.get(i).getPoint(j).y, 
													word1.get(i).getPoint(j+1).x - word1.get(i).getPoint(j).x);
						double theta2 = Math.atan2(word2.get(i).getPoint(j+1).y - word2.get(i).getPoint(j).y, 
								word2.get(i).getPoint(j+1).x - word2.get(i).getPoint(j).x);
						differenceInStroke += Math.toDegrees(Math.abs(theta1-theta2));
					}
					differenceInStroke = differenceInStroke/(numPointsOfStroke-1);
					
					difference += differenceInStroke;
				}
			}
			difference = difference/numStrokes;
			if (difference<MAXIMUM_DIFFERENCE){
				//return true;
				return difference;
			} else {
				//return false;
				return difference;
			}
			
		}
	}

}
