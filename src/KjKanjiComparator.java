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
import java.awt.Font;
import javax.swing.SwingConstants;


public class KjKanjiComparator extends JDialog {

	public static final int MAXIMUM_DIFFERENCE = 20;
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
			dialog.setTitle("Compare Kanji");
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
		setBounds(100, 100, 503, 300);
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
			drawingPanel2.setBounds(297, 11, 180, 180);
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
			btnClear2.setBounds(408, 194, 69, 23);
			contentPanel.add(btnClear2);
		}
		{
			JButton btnCompare = new JButton("Compare");
			btnCompare.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					double result = compareKanji(drawingPanel1.getSimplified(),drawingPanel2.getSimplified()); 
					if (result<MAXIMUM_DIFFERENCE){
						lblResult.setText("CORRECT");
						lblDifference.setText("" + result);
					} else if (result<MAXIMUM_DIFFERENCE*1.5){
						lblResult.setText("ALMOST");
						lblDifference.setText("" + result);
					} else {
						lblResult.setText("WRONG");
						lblDifference.setText("" + result);
					}
				}
			});
			btnCompare.setBounds(199, 194, 89, 23);
			contentPanel.add(btnCompare);
		}
		{
			lblResult = new JLabel("Result");
			lblResult.setHorizontalAlignment(SwingConstants.CENTER);
			lblResult.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblResult.setBounds(200, 79, 87, 14);
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
			btnUndo2.setBounds(334, 194, 69, 23);
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
			lblDifference.setHorizontalAlignment(SwingConstants.CENTER);
			lblDifference.setBounds(200, 108, 87, 14);
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

	public static double compareKanji(ArrayList<KjStroke> word1, ArrayList<KjStroke> word2) {
		
		
		if (word1.size()!=word2.size()){
			return 10000;
			//return false;
		} else {
			int numStrokes = word2.size();
			double difference = 0;
			
			for(int i=0;i<numStrokes;i++){
				if (word1.get(i).size() != word2.get(i).size()){
					//return false;
					return 990000 + word1.get(i).size()*100 + word2.get(i).size();
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
			
			double difference2 = 0;
			for(int i=0;i<numStrokes;i++){
				difference2 += word1.get(i).getPoint(0).distance(word2.get(i).getPoint(0));
			}
			difference2 = difference2/(numStrokes)*2;
			
			difference = (difference+difference2)/2;
			
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
