import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;


public class KjStroke {
	private static final int REDUCE_NOISE_LEVEL = 5;
	
	private int order;
	private ArrayList<Point> pointList;
	
	public KjStroke(){
		pointList = new ArrayList<Point>();
	}
	
	public void addPoint(Point p){
		pointList.add(p);
	}
	
	public Point getPoint(int i){
		return pointList.get(i);
	}
	
	public int size(){
		return pointList.size();
	}
	
	public void setOrder(int u){
		order = u;
	}
	
	public int getOrder(){
		return order;
	}
	
	public KjStroke getSimplified(){
		KjStroke result = new KjStroke();
		KjStroke reduced = getReducedNoise();
		
		if (reduced.size()<=2){
			for(int i=0;i<reduced.size();i++){
				result.addPoint(reduced.getPoint(i));
			}
		} else {
			result.addPoint(reduced.getPoint(0));
			
			int i3 = 0;
			Point p1, p2, p3;
			for(int i=2;i<reduced.size();i++){
				p1 = reduced.getPoint(i);
				p3 = reduced.getPoint(i3);
				
				if (i3+1<i){
					Line2D line = new Line2D.Double();
					line.setLine(p1, p3);
					int maxJ = i3+1;
					double maxDist = line.ptLineDist(reduced.getPoint(maxJ));
					for(int j=i3+1;j<i;j++){
						double dist = line.ptLineDist(reduced.getPoint(j));
						if (maxDist<dist){
							maxDist = dist;
							maxJ = j;
						}
					}
					p2 = reduced.getPoint(maxJ);
					double angle1 = Math.atan2(p2.y-p3.y, p2.x-p3.x);
					double angle2 = Math.atan2(p1.y-p2.y, p1.x-p2.x);
					double angle = Math.abs(Math.toDegrees(angle2 - angle1));
					angle = Math.min(angle, 360-angle);
					if (angle>60){
						i3 = i;
						result.addPoint(p2);
					}
				}
			}
			result.addPoint(reduced.getPoint(reduced.size()-1));
		}
		//if (result.size()!=this.size()){
			return result;//.getSimplified2();
		//}
		//return result;
	}
	
	public KjStroke getSimplified2(){
		KjStroke result = new KjStroke();
		//KjStroke reduced = this;
		
		if (this.size()<=2){
			for(int i=0;i<this.size();i++){
				result.addPoint(this.getPoint(i));
			}
		} else {
			result.addPoint(this.getPoint(0));
			
			int i3 = 0;
			Point p1, p2, p3;
			for(int i=1;i<this.size()-1;i++){
				p1 = this.getPoint(i+1);
				p2 = this.getPoint(i);
				p3 = this.getPoint(i3);
				
				double angle1 = Math.abs(Math.atan2(p2.y-p3.y, p2.x-p3.x));
				double angle2 = Math.abs(Math.atan2(p1.y-p2.y, p1.x-p2.x));
				double angle = Math.toDegrees(Math.abs(angle2 - angle1));
				if (angle>60){
					i3 = i;
					result.addPoint(p2);
				}
			}
			result.addPoint(this.getPoint(this.size()-1));
		}
		return result;
	}
	
	public KjStroke getReducedNoise(){
		KjStroke result = new KjStroke();
		if (pointList.size()<=2){
			for(int i=0;i<pointList.size();i++){
				result.addPoint(pointList.get(i));
			}
		} else {
			result.addPoint(pointList.get(0));
			for(int i=1;i<pointList.size()-1;i++){
				ArrayList<Point> temp = new ArrayList<Point>();
				temp.add(pointList.get(i));
				for (int j=1;j<=REDUCE_NOISE_LEVEL;j++){
					if (i-j>0) {
						temp.add(pointList.get(i-j));
					}
					if (i+j<pointList.size()-1){
						temp.add(pointList.get(i+j));
					}
				}
				int x=0,y=0;
				for(int j=0;j<temp.size();j++){
					x += temp.get(j).x;
					y += temp.get(j).y;
				}
				result.addPoint(new Point(x/temp.size(),y/temp.size()));
			}
			result.addPoint(pointList.get(pointList.size()-1));
		}
		
		return result;
	}
}
