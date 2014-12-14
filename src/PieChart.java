import java.util.ArrayList;

import Model.RGB;
import processing.core.PApplet;

public class PieChart {

	private float diam;
	private float[] angles;
	private final PApplet p;
	private float centerX;
	private float centerY;
	private ArrayList<RGB> color = new ArrayList<RGB>();
	private ArrayList<Float> diams = new ArrayList<Float>();

	public PieChart(PApplet p, float diam, float[] angles, float centerX, float centerY) {
		super();
		this.angles = angles;
		this.diam = diam;
		this.p = p;
		this.centerX = centerX;
		this.centerY = centerY;
		color.add(new RGB(204, 51, 255));
		color.add(new RGB(0, 0, 255));
		color.add(new RGB(51, 204, 255));
		color.add(new RGB(0, 204, 51));
		color.add(new RGB(204, 255, 51));
		color.add(new RGB(255, 255, 0));
		color.add(new RGB(255, 128, 0));
		color.add(new RGB(255, 0, 0));
		for (int i = 0; i < 8; i++) {
			diams.add(diam);
		}
	}

	public float[] getAngles() {
		return angles;
	}

	public void setAngles(float[] angles) {
		this.angles = angles;
	}

	public float getDiam() {
		return diam;
	}

	public void setDiam(float diam) {
		this.diam = diam;
	}

	public PApplet getP() {
		return p;
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	public ArrayList<RGB> getColor() {
		return color;
	}

	public void setColor(ArrayList<RGB> color) {
		this.color = color;
	}

	public ArrayList<Float> getDiams() {
		return diams;
	}

	public void setDiams(ArrayList<Float> diams) {
		this.diams = diams;
	}
	
	public void setDiam(float diam, int position) {
		if (position < 8) {
			this.diams.set(position, diam);
		}
		else {
			p.println("Error : positon needs to be < 8");
		}
	}
	
	public void resetDiams(float diam) {
		diams.clear();
		for (int i = 0; i < 8; i++) {
			diams.add(diam);
		}
	}

	public void drawPieChart() {
		float lastAngle = 0;
		for (int i = 0; i < angles.length; i++) {
			RGB c = color.get(i);
			p.fill(c.getRed(), c.getGreen(), c.getBlue());
			p.arc(centerX, centerY, diams.get(i), diams.get(i), lastAngle,
					lastAngle + p.radians(angles[i]), p.PIE);
			lastAngle += p.radians(angles[i]);
		}
	}

}
