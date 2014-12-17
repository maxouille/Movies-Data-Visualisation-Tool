import java.util.ArrayList;

import Model.RGB;
import processing.core.PApplet;
import processing.core.PGraphics;

public class PieChart {

	private float diam;
	private float[] angles;
	private float[] savedAngles;
	private PGraphics pg;
	private float centerX;
	private float centerY;
	private ArrayList<RGB> color = new ArrayList<RGB>();
	private float[] diams = new float[5];

	public PieChart(PGraphics pg, float diam, float[] angles, float centerX, float centerY) {
		super();
		this.angles = angles;
		this.pg = pg;
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
		this.diam = diam;
		for (int i = 0; i < 5; i++) {
			diams[i] = diam;
		}
	}

	public float[] getAngles() {
		return angles;
	}

	public void setAngles(float[] angles) {
		this.angles = angles;
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
	
	public float getDiam() {
		return diam;
	}

	public float[] getDiams() {
		return diams;
	}

	public void setDiams(float[] diams) {
		this.diams = diams;
	}

	public void setDiam(float diam, int position) {
		if (position < 5) {
			this.diams[position] = diam;
		}
		else {
			PApplet.println("Error : positon needs to be < 8");
		}
	}
	public float maxDiams() {
		return PApplet.max(diams);
	}
	
	public void setDiams(float diam) {
		for (int i = 0; i < 5; i++) {
			diams[i] = diam;
		}
		this.diam = diam;
	}

	public float[] getSavedAngles() {
		return savedAngles;
	}

	public void setSavedAngles(float[] savedAngles) {
		this.savedAngles = savedAngles;
	}

	public void drawPieChart() {
		float lastAngle = 0;
		for (int i = 0; i < angles.length; i++) {
			RGB c = color.get(i);
			pg.fill(c.getRed(), c.getGreen(), c.getBlue());
			float diameter = diams[i];
			pg.arc(centerX, centerY, diameter, diameter, lastAngle,
					lastAngle + PApplet.radians(angles[i]));
			lastAngle += PApplet.radians(angles[i]);
		}
	}
}
