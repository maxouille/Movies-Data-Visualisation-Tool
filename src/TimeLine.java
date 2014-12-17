import java.awt.geom.Point2D;

import processing.core.PApplet;
import processing.core.PGraphics;


public class TimeLine {
	private int beginDate;
	private int endDate;
	private int currentDate;
	private Point2D.Float beginPosition;
	private Point2D.Float endPosition;
	private Point2D.Float currentPosition;
	private int color;
	private int cursorColor;
	private PGraphics pg;

	public TimeLine(PGraphics pg, int beginDate, int endDate, int currentDate,
			Point2D.Float beginPosition, Point2D.Float endPosition,
			Point2D.Float currentPosition, int color, int cursorColor) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.currentDate = currentDate;
		this.beginPosition = beginPosition;
		this.endPosition = endPosition;
		this.currentPosition = currentPosition;
		this.color = color;
		this.cursorColor = cursorColor;
		this.pg = pg;
	}

	public int getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(int beginDate) {
		this.beginDate = beginDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public int getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(int currentDate) {
		this.currentDate = currentDate;
	}

	public Point2D getBeginPosition() {
		return beginPosition;
	}

	public void setBeginPosition(Point2D.Float beginPosition) {
		this.beginPosition = beginPosition;
	}

	public Point2D getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Point2D.Float endPosition) {
		this.endPosition = endPosition;
	}

	public Point2D getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Point2D.Float currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getCursorColor() {
		return cursorColor;
	}

	public void setCursorColor(int cursorColor) {
		this.cursorColor = cursorColor;
	}
	
	public void drawTimeLine () {
		pg.line((float) beginPosition.getX(), (float) beginPosition.getY(), (float) endPosition.getX(), (float) endPosition.getY());
		pg.textSize(20);
		pg.textAlign(PApplet.CENTER);
		pg.fill(0,102,153);
		pg.text(beginDate+"", (float) beginPosition.getX(), (float) beginPosition.getY()-15);
		pg.text(endDate+"", (float) endPosition.getX(), (float) endPosition.getY()-15);
		pg.text(currentDate+"", (float) currentPosition.getX(), (float) currentPosition.getY()-15);
		pg.ellipse((float) currentPosition.getX(), (float) currentPosition.getY(), 20, 20);
	}
	
	public void updateDate() {
		float d = (float) (currentPosition.getX() - beginPosition.getX());
		float d2 = (float) (endPosition.getX() - beginPosition.getX());
		float rapport = d/d2;
		currentDate = beginDate + Math.round((endDate - beginDate)*rapport); 
	}
	
	
	
	
	
	
	

}
