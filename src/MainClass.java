import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import Model.Movie;
import Model.RGB;

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
	private float angle1, angle2, angle3, angle4, angle5, angle6, angle7,
			angle8;
	private static final float pieDiameter = 300;
	private boolean secondActivity = false;
	private boolean anim = false;

	private RGB backgroundColor = new RGB(255, 255, 255);
	
	private static final long serialVersionUID = 1L;

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
		background(backgroundColor.getRed(), backgroundColor.getGreen(),
				backgroundColor.getBlue());

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

		pie = new PieChart(this, pieDiameter, new float[1], width / 2,
				height / 2);

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
		background(backgroundColor.getRed(), backgroundColor.getGreen(),
				backgroundColor.getBlue());
		// If first actvity
		if (!secondActivity) {
			if (!anim) {
			timeLine.drawTimeLine();
			setPieChartAngles();
			pieInteraction();
			}
			pie.drawPieChart();
			
		} else if (secondActivity) {
			// TODO : draw things for the graph
			/* ici pour récupérer les datas des movies :
			 * suivant la partie cliqué : 1/2/3/4/5/6/7/8
			 * les films associés sont dans movieList triés dans l'odre croissant des budgets
			 * EX : si on est dans la partie 4
			 * les films associés sont les elements de movieList de nbPart1+nbPart2+nbPart3 -1 à nbPart1 +nbPart2+nbPart3+nbPart4-1 
			 */
		}

	}

	public void setPieChartAngles() {
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

		angle1 = (float) (nbPart1 * 360) / nbMovies;
		angle2 = (float) (nbPart2 * 360) / nbMovies;
		angle3 = (float) (nbPart3 * 360) / nbMovies;
		angle4 = (float) (nbPart4 * 360) / nbMovies;
		angle5 = (float) (nbPart5 * 360) / nbMovies;
		angle6 = (float) (nbPart6 * 360) / nbMovies;
		angle7 = (float) (nbPart7 * 360) / nbMovies;
		angle8 = 360 - angle1 - angle2 - angle3 - angle4 - angle5 - angle6
				- angle7;

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
				anim = false;
			}
		}

		// redraw
		redraw();
	}

	private void launchAnimation(int nb) {
		switch (nb) {
		case 0:

			while (angle1 < 360) {
				angle1 += 1;
				if (angle2 > 0) {
					angle2 -= 1;
				} else if (angle3 > 0) {
					angle2 = 0;
					angle3 -= 1;
				} else if (angle4 > 0) {
					angle3 = 0;
					angle4 -= 1;
				} else if (angle5 > 0) {
					angle4 = 0;
					angle5 -= 1;
				} else if (angle6 > 0) {
					angle5 = 0;
					angle6 -= 1;
				} else if (angle7 > 0) {
					angle6 = 0;
					angle7 -= 1;
				} else if (angle8 > 0) {
					angle7 = 0;
					angle8 -= 1;
				}
				angle8 = 0;
				float[] angles = { angle1, angle2, angle3, angle4, angle5,
						angle6, angle7, angle8 };
				pie.setAngles(angles);
				redraw();
			}
			
			float[] angles = { 360, 0, 0, 0, 0, 0, 0, 0 };
			pie.setAngles(angles);
			redraw();

			while (pie.maxDiams() < width+300 || pie.maxDiams() < height+300) {
				pie.setDiams((float) (pie.getDiam() + 0.0001));
				redraw();
			}

			 backgroundColor = pie.getColor().get(0);
			 secondActivity = true;
			anim = false;
			break;
		case 1:
			backgroundColor = pie.getColor().get(1);
			secondActivity = true;
			break;
		case 2:
			backgroundColor = pie.getColor().get(2);
			secondActivity = true;
			break;
		case 3:
			backgroundColor = pie.getColor().get(3);
			secondActivity = true;
			break;
		case 4:
			backgroundColor = pie.getColor().get(4);
			secondActivity = true;
			break;
		case 5:
			backgroundColor = pie.getColor().get(5);
			secondActivity = true;
			break;
		case 6:
			backgroundColor = pie.getColor().get(6);
			secondActivity = true;
			break;
		case 7:
			backgroundColor = pie.getColor().get(7);
			secondActivity = true;
			break;
		default:
			break;
		}

	}

	/**
	 * When the mouse is moved and a mouse button is not pressed
	 */
	public void pieInteraction() {
		// Get polar coords
		float[] newCoords = CartesianToPolar(mouseX, mouseY);
		// convert theta from radians to degrees
		float theta = (float) ((newCoords[1] * 180) / Math.PI);
		if (dist(mouseX, mouseY, pie.getCenterX(), pie.getCenterY()) < pie
				.getDiam() / 2) {
			if (theta > 0 && theta < angle1) {
				if (mousePressed) {
					launchAnimation(0);
					anim = true;
				}
				if (!anim) {
					pie.setDiams(pieDiameter);
					pie.setDiam(pieDiameter + 50, 0);
				}
			} else if (theta >= angle1 && theta < angle1 + angle2) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 1);
				if (mousePressed) {
					launchAnimation(1);
				}
			} else if (theta >= angle1 + angle2
					&& theta < angle1 + angle2 + angle3) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 2);
				if (mousePressed) {
					launchAnimation(2);
				}
			} else if (theta >= angle1 + angle2 + angle3
					&& theta < angle1 + angle2 + angle3 + angle4) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 3);
				if (mousePressed) {
					launchAnimation(3);
				}
			} else if (theta >= angle1 + angle2 + angle3 + angle4
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 4);
				if (mousePressed) {
					launchAnimation(4);
				}
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 5);
				if (mousePressed) {
					launchAnimation(5);
				}
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6 + angle7) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 6);
				if (mousePressed) {
					launchAnimation(6);
				}
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6 + angle7) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 7);
				if (mousePressed) {
					launchAnimation(7);
				}
			}
		} else {
			pie.setDiams(pieDiameter);
		}
	}

	/**
	 * Change coords from M(x,y) to M(ro, theta)
	 * 
	 * @param x
	 * @param y
	 * @return [ro, theta]
	 */
	public float[] CartesianToPolar(float x, float y) {
		// express M(x,y) in the centered frame
		float newx = x - pie.getCenterX();
		float newy = pie.getCenterY() - y;
		// calculate ro
		double ro = sqrt(newx * newx + newy * newy);
		float theta = 0;
		if (newx > 0 && newy >= 0) {
			theta = atan(newy / newx);
		} else if (newx > 0 && newy < 0) {
			theta = atan(newy / newx) + 2 * PI;
		} else if (newx < 0) {
			theta = atan(newy / newx) + PI;
		} else if (newx == 0 && newy > 0) {
			theta = PI / 2;
		} else if (newx == 0 && newy < 0) {
			theta = (3 * PI) / 2;
		}
		// convert theta from trigo way to watch way
		theta = 2 * PI - theta;
		float[] res = new float[2];
		res[0] = (float) ro;
		res[1] = theta;
		return res;
	}

	private void readData() {
		movies = loadJSONObject("../imdb_1990/imdb-movies.json");
		peoples = loadJSONObject("../imdb_1990/imdb-peoples.json");
	}
}
