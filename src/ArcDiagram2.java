import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Model.Couple;
import Model.Triplet;

import processing.core.PApplet;
import processing.core.PGraphics;

public class ArcDiagram2 {

	private String genreHL;
	private boolean HL = false;
	private Set<Triplet> genres = new HashSet<Triplet>();
	private float radius;
	private Point2D.Float center = new Point2D.Float();
	private PGraphics pg;
	private HashMap<String, Point2D.Float> genrePoints = new HashMap<String, Point2D.Float>();
	private ArrayList<String> genreNames = new ArrayList<String>();
	private Point2D.Float HLPoint;

	public ArcDiagram2(PGraphics pg, Set<Triplet> genres, float radius,
			Point2D.Float center, ArrayList<String> genreNames) {
		super();
		this.genres = genres;
		this.radius = radius;
		this.pg = pg;
		this.center = center;
		this.genreNames = genreNames;

		int nbGenres = genreNames.size();
		float angle = ((float) 360 / (float) nbGenres) * PApplet.PI / 180;

		float lastAngle = 0;
		// Create a link between the name og the genre and his position on the
		// circle
		for (String s : genreNames) {
			genrePoints.put(s, new Point2D.Float((float) center.getX() + radius
					/ 2 * PApplet.cos(lastAngle), (float) center.getY()
					- radius / 2 * PApplet.sin(lastAngle)));
			lastAngle += angle;
		}
	}

	public Set<Triplet> getGenres() {
		return genres;
	}

	public void setGenres(HashSet<Triplet> genres) {
		this.genres = genres;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Point2D.Float getCenter() {
		return center;
	}

	public void setCenter(Point2D.Float center) {
		this.center = center;
	}

	public PGraphics getPg() {
		return pg;
	}

	public void setPg(PGraphics pg) {
		this.pg = pg;
	}

	public HashMap<String, Point2D.Float> getGenrePoints() {
		return genrePoints;
	}

	public void setGenrePoints(HashMap<String, Point2D.Float> genrePoints) {
		this.genrePoints = genrePoints;
	}

	public Point2D.Float getHLPoint() {
		return HLPoint;
	}

	public void setHLPoint(Point2D.Float hLPoint) {
		HLPoint = hLPoint;
	}

	public String getGenreHL() {
		return genreHL;
	}

	public void setGenreHL(String genreHL) {
		this.genreHL = genreHL;
	}

	public boolean isHL() {
		return HL;
	}

	public void setHL(boolean hL) {
		HL = hL;
	}

	public ArrayList<String> getGenreNames() {
		return genreNames;
	}

	public void setGenreNames(ArrayList<String> genreNames) {
		this.genreNames = genreNames;
	}

	public void setGenres(Set<Triplet> genres) {
		this.genres = genres;
	}

	public int maxNumber() {
		int max = 0;
		for (Triplet t : genres) {
			if (t.getNumber() > max) {
				max = t.getNumber();
			}
		}
		return max;
	}

	public void drawArcDiagram() {
		pg.fill(255,255,255);
		pg.stroke(0, 0, 0);
		pg.ellipse((float) center.getX(), (float) center.getY(), radius, radius);

		// DRAW POINTS
		for (String keys : genrePoints.keySet()) {
			Point2D.Float p = genrePoints.get(keys);
			if (HL) {
				if (p.equals(HLPoint)) {
					pg.stroke(255,0,0);
					
				}
				else {
					pg.stroke(0,0,0);
				}
				pg.ellipse((float) p.getX(), (float) p.getY(), 20, 20);
			}
			else {
				pg.stroke(0,0,0);
				pg.ellipse((float) p.getX(), (float) p.getY(), 20, 20);
			}
		}

		Set<Triplet> withHLSet = new HashSet<Triplet>();
		for (Triplet t : genres) {
			// Get the position of the first point
			Point2D.Float beginPosition = genrePoints.get(t.getGenre1());
			// For each link, get the position of the second point
			Point2D endPosition = genrePoints.get(t.getGenre2());
			pg.noFill();


			// PApplet.println("HL : "+HL+" and genreHL = "+genreHL);
			if (HL) {
				if (t.getGenre1().equals(genreHL)
						|| t.getGenre2().equals(genreHL)) {
					withHLSet.add(t);
					// pg.stroke(255,0,0);
				} else {
					pg.stroke(200, 200, 200);
					pg.bezier((float) beginPosition.getX(),
							(float) beginPosition.getY(),
							(float) center.getX(), (float) center.getY(),
							(float) center.getX(), (float) center.getY(),
							(float) endPosition.getX(),
							(float) endPosition.getY());
				}
			}

			else {
				pg.stroke(0, 0, 0);
				pg.bezier((float) beginPosition.getX(),
						(float) beginPosition.getY(), (float) center.getX(),
						(float) center.getY(), (float) center.getX(),
						(float) center.getY(), (float) endPosition.getX(),
						(float) endPosition.getY());
			}

		}

		ArrayList<String> hlNAmes = new ArrayList<String>();
		for (Triplet t : withHLSet) {
			if (!hlNAmes.contains(t.getGenre1())) {
				hlNAmes.add(t.getGenre1());
			}
			if (!hlNAmes.contains(t.getGenre2())) {
				hlNAmes.add(t.getGenre2());
			}
			// Get the position of the first point
			Point2D.Float beginPosition = genrePoints.get(t.getGenre1());
			// For each link, get the position of the second point
			Point2D endPosition = genrePoints.get(t.getGenre2());
			pg.noFill();

			pg.stroke(255, 0, 0);

			pg.bezier((float) beginPosition.getX(),
					(float) beginPosition.getY(), (float) center.getX(),
					(float) center.getY(), (float) center.getX(),
					(float) center.getY(), (float) endPosition.getX(),
					(float) endPosition.getY());
		}
		
		drawCaptionARC(hlNAmes);

	}
	
	public void drawCaptionARC(ArrayList<String> names) {
		float newRadius = radius/2 + (float)40;
		// For each point
		for (String s : genrePoints.keySet()) {
			Point2D.Float p = genrePoints.get(s);
			float angle = angle(center, p);
			angle -= PApplet.HALF_PI;
			
			
			float newX = (float) center.getX() + newRadius*PApplet.cos(angle);
			float newY = (float) center.getY() + newRadius*PApplet.sin(angle);
			if (names.contains(s)) {
				pg.fill(255,0,0);
			}
			else {
				pg.fill(0,0,0);
			}
			pg.textSize(12);
			pg.textAlign(PApplet.CENTER);
			pg.text(s, newX, newY);
		}
	}
	
	//REtrun the angle between 12hour on the clock
	public float angle(Point2D.Float center, Point2D.Float p1) {
	    Point2D.Float p0 = new Point2D.Float((float)center.getX(), (float)center.getY() - (float) Math.sqrt(Math.abs((float)p1.getX() - (float)center.getX()) * Math.abs((float)p1.getX() - (float)center.getX())
	            + Math.abs((float)p1.getY() - (float)center.getY()) * Math.abs((float)p1.getY() - (float)center.getY())));
	    return (float) ((2 * Math.atan2(p1.getY() - p0.getY(), p1.getX() - p0.getX())));
	}
}
