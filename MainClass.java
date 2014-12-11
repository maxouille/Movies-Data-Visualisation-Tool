import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import processing.core.*;

public class MainClass extends PApplet {

	private PieChart pie;
	private TimeLine timeLine;
	private boolean pressed = false;
	
  public void setup() {
    size(800,600);
    background(255);
    float[] angles = { 30, 10, 45, 35, 60, 38, 75, 67 };
    pie = new PieChart(this, (float) 300, angles);
    timeLine = new TimeLine(this, 1990, 2015, 2005, 
    		new Point2D.Float(30,50), 
    		new Point2D.Float(770, 50), 
    		new Point2D.Float(700, 50), 0, 0);
  }

  public void draw() {
	  
	  background(255);
	  pie.drawPieChart();
	  timeLine.drawTimeLine();
	  
  }
  
  public void mousePressed() {
	  if (Math.abs(mouseX - timeLine.getCurrentPosition().getX()) < 20 && 
			  Math.abs(mouseY - timeLine.getCurrentPosition().getY()) < 20) {
		  pressed = true;
	  }
  }
  
  public void mouseReleased() {
	  pressed = false;
  }
  
  public void mouseDragged() {
	  if (pressed) {
		  if (mouseX >= timeLine.getBeginPosition().getX() && mouseX <= timeLine.getEndPosition().getX()) {
			  timeLine.setCurrentPosition(new Point2D.Float(mouseX, 50));
			  timeLine.updateDate();
		  }
	  }

	  //redraw
	  redraw();
  }
  
  
}
