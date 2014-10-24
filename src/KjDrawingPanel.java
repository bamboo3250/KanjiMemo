import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

class KjDrawingPanel extends JPanel {

	    private int startX = -2;
	    private int startY = -2;
	    private ArrayList<KjStroke> strokeList = new ArrayList<KjStroke>();
	    private ArrayList<KjStroke> simplifiedStrokeList = new ArrayList<KjStroke>();
	    private KjStroke currentStroke;
	    private int numStrokes = 0;
		
	    
	    public KjDrawingPanel() {
	    	setBackground(Color.WHITE);
			
	        addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {
	            	startX = e.getX();
		            startY = e.getY();
		            currentStroke = new KjStroke();
	            }
	            
	            public void mouseReleased(MouseEvent e) {
	            	if (currentStroke.size()>2){
		            	increaseNumStroke();
		            	//drawReducedStroke();
		            	drawSimplifiedStroke();
	            	}
	            }
	        });

	        addMouseMotionListener(new MouseAdapter() {
	            public void mouseDragged(MouseEvent e) {
	            	currentStroke.addPoint(new Point(e.getX(),e.getY()));
	            	moveSquare(e.getX(),e.getY());
	            }
	        });
	        
	    }
	    
	    public ArrayList<KjStroke> getSimplified(){
	    	return simplifiedStrokeList;
	    }
	    
	    public void increaseNumStroke(){
			numStrokes++;
			KjAddDialog.getInstance().setStrokeLabel(numStrokes);
			currentStroke.setOrder(numStrokes);
			strokeList.add(currentStroke);
		}
		
		public void resetNumStroke(){
			numStrokes = 0;
			KjAddDialog.getInstance().setStrokeLabel(numStrokes);
			strokeList.clear();
			simplifiedStrokeList.clear();
		}
		
	    protected void drawReducedStroke() {
	    	KjStroke reduced = currentStroke.getReducedNoise();
	    	Graphics g = this.getGraphics(); 
        	g.setColor(Color.red);
			for(int i=0;i<reduced.size()-1;i++){
	        	drawLine(reduced.getPoint(i).x, reduced.getPoint(i).y
	        			, reduced.getPoint(i+1).x, reduced.getPoint(i+1).y, g);
			}
			
		}
	    
	    protected void drawSimplifiedStroke() {
	    	KjStroke simplified = currentStroke.getSimplified();
	    	simplifiedStrokeList.add(simplified);
	    	
	    	Graphics g = this.getGraphics(); 
        	
	    	g.setColor(Color.green);
	    	g.fillOval(simplified.getPoint(0).x, simplified.getPoint(0).y,5 , 5);
	    	g.setColor(Color.DARK_GRAY);
	    	g.drawOval(simplified.getPoint(0).x, simplified.getPoint(0).y,5 , 5);
	    	
        	
        	for(int i=1;i<simplified.size()-1;i++){
        		g.setColor(Color.yellow);
	        	g.fillOval(simplified.getPoint(i).x, simplified.getPoint(i).y, 5, 5);
	        	g.setColor(Color.DARK_GRAY);
        		g.drawOval(simplified.getPoint(i).x, simplified.getPoint(i).y, 5, 5);
        	}
        	g.setColor(Color.red);
        	g.fillOval(simplified.getPoint(simplified.size()-1).x, simplified.getPoint(simplified.size()-1).y,5,5);
        	g.setColor(Color.DARK_GRAY);
        	g.drawOval(simplified.getPoint(simplified.size()-1).x, simplified.getPoint(simplified.size()-1).y,5,5);
        	
		}

		private void moveSquare(int x, int y) {
	        //int OFFSET = 1;
	        if ((startX!=x) || (startY!=y)) {
	            //repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
	        	Graphics g = this.getGraphics(); 
	        	g.setColor(Color.BLACK);
	        	drawLine(startX, startY, x, y, g);
	        	startX=x;
	            startY=y;
	            //repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
	        } 
	    }

		private void drawLine(int x1, int y1, int x2, int y2, Graphics g) {
			g.drawLine(x1  , y1  , x2  , y2);
			g.drawLine(x1+1, y1  , x2+1, y2);
			g.drawLine(x1-1, y1  , x2-1, y2);
			g.drawLine(x1  , y1+1, x2  , y2+1);
			g.drawLine(x1  , y1-1, x2  , y2-1);
		}
	    	
		public void undo(){
			if (numStrokes>0){
				numStrokes--;
				strokeList.remove(strokeList.size()-1);
				simplifiedStrokeList.remove(simplifiedStrokeList.size()-1);
				
				Graphics g = this.getGraphics();
		    	g.setColor(Color.white);
		    	g.fillRect(0, 0, this.getWidth(), this.getHeight());
	
		    	drawRawAndSimplifiedStroke();
			}
		}
		
	    private void drawRawAndSimplifiedStroke() {
	    	Graphics g = this.getGraphics();
	    	for(int i=0;i<strokeList.size();i++){
	    		g.setColor(Color.BLACK);
	    		for(int j=0;j<strokeList.get(i).size()-1;j++){
	    			Point p1 = strokeList.get(i).getPoint(j);
	    			Point p2 = strokeList.get(i).getPoint(j+1);
	    			drawLine(p1.x, p1.y, p2.x, p2.y, g);
	    		}
	    		
	    		KjStroke simplified = simplifiedStrokeList.get(i);
		    	g.setColor(Color.green);
		    	g.fillOval(simplified.getPoint(0).x, simplified.getPoint(0).y,5 , 5);
		    	g.setColor(Color.DARK_GRAY);
		    	g.drawOval(simplified.getPoint(0).x, simplified.getPoint(0).y,5 , 5);
		    	
	        	
	        	for(int j=1;j<simplified.size()-1;j++){
	        		g.setColor(Color.yellow);
		        	g.fillOval(simplified.getPoint(j).x, simplified.getPoint(j).y, 5, 5);
		        	g.setColor(Color.DARK_GRAY);
	        		g.drawOval(simplified.getPoint(j).x, simplified.getPoint(j).y, 5, 5);
	        	}
	        	g.setColor(Color.red);
	        	g.fillOval(simplified.getPoint(simplified.size()-1).x, simplified.getPoint(simplified.size()-1).y,5,5);
	        	g.setColor(Color.DARK_GRAY);
	        	g.drawOval(simplified.getPoint(simplified.size()-1).x, simplified.getPoint(simplified.size()-1).y,5,5);
	    	}
		}

		protected void paintComponent(Graphics g) {
	        super.paintComponent(g);       
	        //g.drawString("This is my custom Panel!",10,20);
	        //g.setColor(Color.BLACK);
	        //g.fillRect(squareX,squareY,squareW,squareH);
	        //g.setColor(Color.BLACK);
	        //g.drawRect(squareX,squareY,squareW,squareH);
	    }  
	    
	    public void clearCanvas(){
	    	Graphics g = this.getGraphics();
	    	g.setColor(Color.white);
	    	g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    	resetNumStroke();
	    }
	}