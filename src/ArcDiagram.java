import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Couple;

import processing.core.PApplet;
import processing.core.PGraphics;


public class ArcDiagram {

	private HashMap<String, ArrayList<Couple>> genres = new HashMap<String, ArrayList<Couple>>();
	private float radius;
	private Point2D.Float center;
	private PGraphics pg;
	private HashMap<String, Point2D.Float> genrePoints = new HashMap<String, Point2D.Float>();
	
	public ArcDiagram(PGraphics pg, HashMap<String, ArrayList<Couple>> genres, float radius, Point2D.Float center) {
		super();
		this.genres = genres;
		this.radius = radius;
		this.pg = pg;
		this.center = center;
		
		int nbGenres = genres.size();
		float angle = ((float) 360 / (float) nbGenres)*PApplet.PI/180;
		
		float lastAngle = 0;		
		//Create a link between the name og the genre and his position on the circle
		for (String keys : genres.keySet()) {
			genrePoints.put(keys.toString(), new Point2D.Float((float)center.getX() + radius/2*PApplet.cos(lastAngle), (float)center.getY() - radius/2*PApplet.sin(lastAngle)));
			lastAngle += angle;
		}
	}
	
	public HashMap<String, ArrayList<Couple>> getGenres() {
		return genres;
	}

	public void setGenres(HashMap<String, ArrayList<Couple>> genres) {
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
	
	

	public void drawArcDiagram() {
		pg.fill(255);
		pg.ellipse((float)center.getX(), (float)center.getY(), radius, radius);
		
		for (String keys : genres.keySet()) {
			Point2D.Float p = genrePoints.get(keys);
			pg.ellipse((float) p.getX(), (float) p.getY(), 20, 20);
		}
		
		for (String keys : genres.keySet()) {
			//Get the list of couples associated
			ArrayList<Couple> couples = genres.get(keys);
			ArrayList<String> links = new ArrayList<String>();
			for (Couple c : couples) {
				links.add(c.getGenre());
			}
			//Get the position of the first point
			Point2D.Float beginPosition = genrePoints.get(keys);
			//For each link, get the position of the second point
			for (String name : links) {
				Point2D endPosition = genrePoints.get(name);
				pg.noFill();
				pg.bezier((float) beginPosition.getX(), (float) beginPosition.getY(), 
						(float) center.getX(), (float) center.getY(), 
						(float) center.getX(), (float) center.getY(),
						(float) endPosition.getX(), (float) endPosition.getY());
			}
		}
		
	}

	
}
