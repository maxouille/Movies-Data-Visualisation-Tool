import java.util.ArrayList;

import Model.RGB;
import processing.core.PApplet;

public class PieChart {

	private float radius;
	private float[] angles;
	private final PApplet p;
	private ArrayList<RGB> color;

	public PieChart(PApplet p, float radius, float[] angles) {
		super();
		this.angles = angles;
		this.radius = radius;
		this.p = p;
		color.add(new RGB(204, 51, 255));
		color.add(new RGB(0, 0, 255));
		color.add(new RGB(51, 204, 255));
		color.add(new RGB(0, 204, 51));
		color.add(new RGB(204, 255, 51));
		color.add(new RGB(255, 255, 0));
		color.add(new RGB(255, 128, 0));
		color.add(new RGB(255, 0, 0));
	}

	public float[] getAngles() {
		return angles;
	}

	public void setAngles(float[] angles) {
		this.angles = angles;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public PApplet getP() {
		return p;
	}

	public void drawPieChart() {
		float lastAngle = 0;
		for (int i = 0; i < angles.length; i++) {
			//float gray = p.map(i, 0, angles.length, 0, 255);
			RGB c = color.get(i);
			p.fill(c.getRed(), c.getGreen(), c.getBlue());
			p.arc(p.width / 2, p.height / 2, radius, radius, lastAngle,
					lastAngle + p.radians(angles[i]));
			lastAngle += p.radians(angles[i]);
		}
	}

}
