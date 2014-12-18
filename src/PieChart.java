import java.util.ArrayList;

import Model.RGB;
import processing.core.PApplet;
import processing.core.PGraphics;

public class PieChart {

	private float diam;
	private float[] angles = new float[5];
	private float[] savedAngles = new float[5];
	private PGraphics pg;
	private float centerX;
	private float centerY;
	private ArrayList<RGB> color = new ArrayList<RGB>();
	private float[] diams = new float[5];
	private float mouseX;
	private float mouseY;
	private String textToDisplay;
	private boolean clicked = false;
	private boolean anim = false;

	public PieChart(PGraphics pg, float diam, float centerX, float centerY) {
		super();
		this.pg = pg;
		this.centerX = centerX;
		this.centerY = centerY;
		color.add(new RGB(55, 162, 220));
		color.add(new RGB(140, 191, 76));
		color.add(new RGB(255,235,60));
		color.add(new RGB(231,69,57));
		color.add(new RGB(90,68,148));
		this.diam = diam;
		for (int i = 0; i < 5; i++) {
			diams[i] = diam;
			savedAngles[i] = 0;
			angles[i] = 0;
		}
	}

	public PGraphics getPg() {
		return pg;
	}

	public void setPg(PGraphics pg) {
		this.pg = pg;
	}

	public float getMouseX() {
		return mouseX;
	}

	public void setMouseX(float mouseX) {
		this.mouseX = mouseX;
	}

	public float getMouseY() {
		return mouseY;
	}

	public void setMouseY(float mouseY) {
		this.mouseY = mouseY;
	}

	public String getTextToDisplay() {
		return textToDisplay;
	}

	public void setTextToDisplay(String textToDisplay) {
		this.textToDisplay = textToDisplay;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public void setDiam(float diam) {
		this.diam = diam;
	}

	public float[] getAngles() {
		return angles;
	}

	public void setAngles(float[] angles) {
		savedAngles = this.angles;
		this.angles = angles;
	}

	public float getCenterX() {
		return centerX;
	}

	
	public boolean isAnim() {
		return anim;
	}

	public void setAnim(boolean anim) {
		this.anim = anim;
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
		Thread t = new Thread () {
			public void run() {
				while (Math.abs(savedAngles[0] - angles[0]) > 0.1 && Math.abs(savedAngles[1] - angles[1]) > 0.1 &&
						Math.abs(savedAngles[2] - angles[2]) > 0.1 && Math.abs(savedAngles[3] - angles[3]) > 0.1 &&
						Math.abs(savedAngles[4] - angles[4]) > 0.1) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}		
					PApplet.println("in thread");
					savedAngles[0] += (angles[0] - savedAngles[0])*0.1;
					savedAngles[1] += (angles[1] - savedAngles[1])*0.1;
					savedAngles[2] += (angles[2] - savedAngles[2])*0.1;
					savedAngles[3] += (angles[3] - savedAngles[3])*0.1;
					savedAngles[4] += (angles[4] - savedAngles[4])*0.1;
				float lastAngle = 0;
				for (int i = 0; i < angles.length; i++) {
					RGB c = color.get(i);
					pg.fill(c.getRed(), c.getGreen(), c.getBlue());
					float diameter = diams[i];
					pg.arc(centerX, centerY, diameter, diameter, lastAngle,
							lastAngle + PApplet.radians(savedAngles[i]));
					lastAngle += PApplet.radians(savedAngles[i]);
				}
				
			}
			}};
			
			/*if (anim) {
			t.start();
			}else {*/
		
		
		float lastAngle = 0;
		for (int i = 0; i < angles.length; i++) {
			RGB c = color.get(i);
			pg.fill(c.getRed(), c.getGreen(), c.getBlue());
			float diameter = diams[i];
			pg.arc(centerX, centerY, diameter, diameter, lastAngle,
					lastAngle + PApplet.radians(angles[i]));
			lastAngle += PApplet.radians(angles[i]);
		}//}
		if (clicked) {
			pg.fill(0,0,0);
			pg.textSize(20);
			pg.strokeWeight(3);
			pg.textAlign(PApplet.CENTER);
			pg.text(textToDisplay, mouseX, mouseY-10);
		}
	}
}
