import java.awt.Point;
import java.util.ArrayList;


public class KjStroke {
	private ArrayList<Point> pointList;
	
	public KjStroke(){
		pointList = new ArrayList<Point>();
	}
	
	public void addPoint(Point p){
		pointList.add(p);
	}
	
	public Point get(int i){
		return pointList.get(i);
	}
	
}
