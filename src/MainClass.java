import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import Model.Movie;

import processing.core.*;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class MainClass extends PApplet {

	private PieChart pie;
	private TimeLine timeLine;
	private boolean pressed = false;
	private JSONObject movies;
	private JSONObject peoples;
	private Thread t;
	private ArrayList<Movie> movieList = new ArrayList<Movie>();
	private JSONArray movs;
	private ArrayList<String> test = new ArrayList<String>();

	public void setup() {
		// Fill test
		test.add("bo");
		test.add("title");
		test.add("genres");
		test.add("year");
		test.add("budjet");
		test.add("mid");
		test.add("peoplesID");
		test.add("rating");
		test.add("date");

		size(800, 600);
		background(255);

		// Set the timeline
		timeLine = new TimeLine(this, 1990, 2013, 2005, new Point2D.Float(30,
				50), new Point2D.Float(770, 50), new Point2D.Float(700, 50), 0,
				0);

		// load data in background
		t = new Thread() {
			public void run() {
				readData();
			}
		};
		t.start();

		while (t.isAlive()) {
		}

		movs = movies.getJSONArray("movies");

		// println(movs);

		pie = new PieChart(this, (float) 300, new float[1]);

	}

	public void sortMoviesbyBudget() {
		Collections.sort(movieList, new MovieComparator());
	}

	private class MovieComparator implements Comparator<Movie> {
		@Override
		public int compare(Movie m1, Movie m2) {
			return ((Integer) m1.getBudget()).compareTo(m2.getBudget());
		}
	}

	public void draw() {
		background(255);
		timeLine.drawTimeLine();
		updatePieChart();
		pie.drawPieChart();

	}

	public void updatePieChart() {
		movieList.clear();
		for (int i = 0; i < movs.size(); i++) {
			JSONObject mov = movs.getJSONObject(i);

			if (mov.keys().containsAll(test)
					&& timeLine.getCurrentDate() == Integer.parseInt(mov
							.getString("date").substring(0, 4))) {
				movieList.add(new Movie(mov.getInt("bo"), mov
						.getString("title"), mov.getJSONArray("genres"), mov
						.getInt("year"), mov.getInt("budjet"), mov
						.getInt("mid"), mov.getJSONObject("peoplesID"), mov
						.getJSONObject("rating"), mov.getString("date")));
			}
		}

		sortMoviesbyBudget();
		int nbMovies = movieList.size();

		// Calculate the max budjet of all the films
		int maxBudjet = 0;
		for (int i = 0; i < movieList.size(); i++) {
			if (maxBudjet < movieList.get(i).getBudget()) {
				maxBudjet = movieList.get(i).getBudget();
			}
		}

		// Divide in 8 parts
		double part = maxBudjet / 8;
		int nbPart1 = 0;
		int nbPart2 = 0;
		int nbPart3 = 0;
		int nbPart4 = 0;
		int nbPart5 = 0;
		int nbPart6 = 0;
		int nbPart7 = 0;
		int nbPart8 = 0;
		for (int i = 0; i < nbMovies - 1; i++) {
			int movieBudjet = movieList.get(i).getBudget();
			if (movieBudjet < part) {
				nbPart1++;
			} else if (movieBudjet >= part && movieBudjet < 2 * part) {
				nbPart2++;
			}

			else if (movieBudjet >= 2 * part && movieBudjet < 3 * part) {
				nbPart3++;
			}

			else if (movieBudjet >= 3 * part && movieBudjet < 4 * part) {
				nbPart4++;
			}

			else if (movieBudjet >= 4 * part && movieBudjet < 5 * part) {
				nbPart5++;
			}

			else if (movieBudjet >= 5 * part && movieBudjet < 6 * part) {
				nbPart6++;
			}

			else if (movieBudjet >= 6 * part && movieBudjet < 7 * part) {
				nbPart7++;
			}

			else if (movieBudjet >= 7 * part) {
				nbPart8++;
			}
		}

		float angle1, angle2, angle3, angle4, angle5, angle6, angle7, angle8;

		angle1 = (float) (nbPart1 * 360) / nbMovies;
		angle2 = (float) (nbPart2 * 360) / nbMovies;
		angle3 = (float) (nbPart3 * 360) / nbMovies;
		angle4 = (float) (nbPart4 * 360) / nbMovies;
		angle5 = (float) (nbPart5 * 360) / nbMovies;
		angle6 = (float) (nbPart6 * 360) / nbMovies;
		angle7 = (float) (nbPart7 * 360) / nbMovies;
		angle8 = (float) (nbPart8 * 360) / nbMovies;

		float[] angles = { angle1, angle2, angle3, angle4, angle5, angle6,
				angle7, angle8 };
		pie.setAngles(angles);

	}

	public void mousePressed() {
		if (Math.abs(mouseX - timeLine.getCurrentPosition().getX()) < 20
				&& Math.abs(mouseY - timeLine.getCurrentPosition().getY()) < 20) {
			pressed = true;
		}
	}

	public void mouseReleased() {
		pressed = false;
	}

	public void mouseDragged() {
		if (pressed) {
			if (mouseX >= timeLine.getBeginPosition().getX()
					&& mouseX <= timeLine.getEndPosition().getX()) {
				timeLine.setCurrentPosition(new Point2D.Float(mouseX, 50));
				timeLine.updateDate();
			}
		}

		// redraw
		redraw();
	}

	private void readData() {
		movies = loadJSONObject("./imdb-movies.json");
		// println(movies);
		peoples = loadJSONObject("./imdb-peoples.json");
		// println(peoples);
	}
}
