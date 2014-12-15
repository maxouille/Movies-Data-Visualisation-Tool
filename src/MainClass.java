import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.sound.midi.Patch;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import Model.Movie;
import Model.RGB;
import aurelienribon.tweenengine.*;

public class MainClass extends PApplet {

	private PieChart pie;
	private TimeLineViz timeLineViz;
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

	private float theta;
	private float ro;

	private RGB backgroundColor = new RGB(255, 255, 255);
	
	private static final long serialVersionUID = 1L;

	int nbPart1 = 0;
	int nbPart2 = 0;
	int nbPart3 = 0;
	int nbPart4 = 0;
	int nbPart5 = 0;
	int nbPart6 = 0;
	int nbPart7 = 0;
	int nbPart8 = 0;

	int originX = 100;
	int originY = 500;
	int endX = 500;
	int endY = 100;

	private int partClicked = -1;

	private ArrayList<Float> xCoord = new ArrayList<Float>();
	private ArrayList<Float> yCoord = new ArrayList<Float>();

	public void setup() {
		noLoop();
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
<<<<<<< HEAD
		timeLine = new TimeLine(this, 1990, 2013, 2010, new Point2D.Float(30,
=======
		timeLineViz = new TimeLineViz(this, 1990, 2013, 2005, new Point2D.Float(30,
>>>>>>> 90c17b5eef56600b9814b4d39dce6b1ccf54f6e3
				50), new Point2D.Float(770, 50), new Point2D.Float(700, 50), 0,
				0);

		// load data in background
		/*
		 * t = new Thread() { public void run() {
		 */
		readData();
		/*
		 * } }; t.start();
		 * 
		 * while (t.isAlive()) { }
		 */

		movs = movies.getJSONArray("movies");

		pie = new PieChart(this, pieDiameter, new float[1], width / 2,
				height / 2);

	}

	public void draw() {
		background(backgroundColor.getRed(), backgroundColor.getGreen(),
				backgroundColor.getBlue());
		// If first actvity
		if (!secondActivity) {
<<<<<<< HEAD
			timeLine.drawTimeLine();
			if (!anim) {
				setPieChartAngles();
=======
			if (!anim) {
			timeLineViz.drawTimeLineViz();
			setPieChartAngles();
			pieInteraction();
>>>>>>> 90c17b5eef56600b9814b4d39dce6b1ccf54f6e3
			}
			pie.drawPieChart();

		} else if (secondActivity) {
<<<<<<< HEAD
			if (partClicked == 0) { // create new array list with the movies
				ArrayList<Movie> ml = new ArrayList<Movie>();
				for (int i = 0; i < nbPart1; i++) {
					ml.add(movieList.get(i));
				}

				strokeWeight(2);
				stroke(0, 0, 0);
				line(originX, originY, originX, endY);
				line(originX, originY, endX, originY);
				textSize(32);
				fill(0, 102, 153);
				textAlign(CENTER);
				text("X-axis : Budjet ($)", width / 2, height - 10);
				writeVerticalText(30, height / 2, "Y-axis : Means (0-10)");
				xCoord.clear();
				yCoord.clear();
				drawData(ml);

				for (int i = 0; i < ml.size(); i++) {
					Movie m = ml.get(i);
					if (Math.abs(xCoord.get(i) - mouseX) <= 5
							&& Math.abs(yCoord.get(i) - mouseY) <= 5) {
						text(ml.get(i).getTitle(), mouseX, mouseY);
					}
				}
			}

		}
	}

	public void drawData(ArrayList<Movie> ml) {
		for (int i = 0; i < ml.size(); i++) {
			float maxBudjet = maxBudget(ml);
			Movie m = ml.get(i);
			float x1 = map(m.getBudget(), 0, maxBudjet, originX, endX);
			float y1 = map((float) m.getRating().get("mean"), 0, 10, originY,
					endY);
			// xCoord and yCoord at index i correspond to the movie at index i
			xCoord.add(x1);
			yCoord.add(y1);
			ellipse(x1, y1, 5, 5);
		}

	}

	public float maxBudget(ArrayList<Movie> ml) {
		float max = 0;
		for (int i = 0; i < ml.size(); i++) {
			float budg = ml.get(i).getBudget();
			if (budg > max) {
				max = budg;
			}
=======
			// TODO : draw things for the graph
			/* ici pour rŽcupŽrer les datas des movies :
			 * suivant la partie cliquŽe : 1/2/3/4/5/6/7/8
			 * les films associŽs sont dans movieList triŽs dans l'odre croissant des budgets
			 * EX : si on est dans la partie 4
			 * les films associÃ©s sont les elements de movieList de nbPart1+nbPart2+nbPart3 -1 Ã  nbPart1 +nbPart2+nbPart3+nbPart4-1 
			 */
>>>>>>> 90c17b5eef56600b9814b4d39dce6b1ccf54f6e3
		}
		return max;
	}

	void writeVerticalText(int x, int y, String text) {
		pushMatrix();
		translate(x, y);
		rotate(-HALF_PI);
		text(text, 0, 0);
		popMatrix();
	}

	public void setPieChartAngles() {
		movieList.clear();
		for (int i = 0; i < movs.size(); i++) {
			JSONObject mov = movs.getJSONObject(i);

			if (mov.keys().containsAll(test)
<<<<<<< HEAD
					&& timeLine.getCurrentDate() == Integer.parseInt(mov
							.getString("date").substring(0, 4))
					&& mov.getInt("budjet") != 0) {
=======
					&& timeLineViz.getCurrentDate() == Integer.parseInt(mov
							.getString("date").substring(0, 4))) {
>>>>>>> 90c17b5eef56600b9814b4d39dce6b1ccf54f6e3
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
		float maxBudjet = maxBudget(movieList);

		nbPart1 = 0;
		nbPart2 = 0;
		nbPart3 = 0;
		nbPart4 = 0;
		nbPart5 = 0;
		nbPart6 = 0;
		nbPart7 = 0;
		nbPart8 = 0;

		// Divide in 8 parts
		double part = maxBudjet / 8;
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

<<<<<<< HEAD
=======
	public void mousePressed() {
		if (Math.abs(mouseX - timeLineViz.getCurrentPosition().getX()) < 20
				&& Math.abs(mouseY - timeLineViz.getCurrentPosition().getY()) < 20) {
			pressed = true;
		}
	}

	public void mouseReleased() {
		pressed = false;
	}

	public void mouseDragged() {
		if (pressed) {
			if (mouseX >= timeLineViz.getBeginPosition().getX()
					&& mouseX <= timeLineViz.getEndPosition().getX()) {
				timeLineViz.setCurrentPosition(new Point2D.Float(mouseX, 50));
				timeLineViz.updateDate();
				anim = false;
			}
		}

		// redraw
		redraw();
	}

>>>>>>> 90c17b5eef56600b9814b4d39dce6b1ccf54f6e3
	private void launchAnimation(int nb) {
		switch (nb) {
		case 0:

			/*
			 * while (angle1 < 360) { angle1 += 1; if (angle2 > 0) { angle2 -=
			 * 1; } else if (angle3 > 0) { angle2 = 0; angle3 -= 1; } else if
			 * (angle4 > 0) { angle3 = 0; angle4 -= 1; } else if (angle5 > 0) {
			 * angle4 = 0; angle5 -= 1; } else if (angle6 > 0) { angle5 = 0;
			 * angle6 -= 1; } else if (angle7 > 0) { angle6 = 0; angle7 -= 1; }
			 * else if (angle8 > 0) { angle7 = 0; angle8 -= 1; } angle8 = 0;
			 * float[] angles = { angle1, angle2, angle3, angle4, angle5,
			 * angle6, angle7, angle8 }; pie.setAngles(angles); redraw(); }
			 * 
			 * float[] angles = { 360, 0, 0, 0, 0, 0, 0, 0 };
			 * pie.setAngles(angles); redraw();
			 * 
			 * while (pie.maxDiams() < width + 300 || pie.maxDiams() < height +
			 * 300) { pie.setDiams((float) (pie.getDiam() + 0.0001)); redraw();
			 * }
			 */

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
		theta = (float) ((newCoords[1] * 180) / Math.PI);
		ro = newCoords[0];
		if (ro < pie.getDiam() / 2) {
			if (theta > 0 && theta < angle1) {
				/*
				 * if (mousePressed && !pressed) { // launchAnimation(0); anim =
				 * true; partClicked = 0; }
				 */

				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 0);

			} else if (theta >= angle1 && theta < angle1 + angle2) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 1);
				if (mousePressed && !pressed) {
					launchAnimation(1);
					anim = true;
				}
			} else if (theta >= angle1 + angle2
					&& theta < angle1 + angle2 + angle3) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 2);
				if (mousePressed && !pressed) {
					launchAnimation(2);
					anim = true;
				}
			} else if (theta >= angle1 + angle2 + angle3
					&& theta < angle1 + angle2 + angle3 + angle4) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 3);
				if (mousePressed && !pressed) {
					launchAnimation(3);
					anim = true;
				}
			} else if (theta >= angle1 + angle2 + angle3 + angle4
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 4);
				if (mousePressed && !pressed) {
					launchAnimation(4);
					anim = true;
				}
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 5);
				if (mousePressed && !pressed) {
					launchAnimation(5);
					anim = true;
				}
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6 + angle7) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 6);
				if (mousePressed && !pressed) {
					launchAnimation(6);
					anim = true;
				}
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6 + angle7) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 7);
				if (mousePressed && !pressed) {
					launchAnimation(7);
					anim = true;
				}
			}
		} else {
			pie.setDiams(pieDiameter);
		}
		redraw();
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
		// peoples = loadJSONObject("../imdb_1990/imdb-peoples.json");
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

	/** LISTENERS */

	public void mousePressed() {
		if (Math.abs(mouseX - timeLine.getCurrentPosition().getX()) < 20
				&& Math.abs(mouseY - timeLine.getCurrentPosition().getY()) < 20) {
			pressed = true;
		}

		if (ro < pie.getDiam() / 2) {
			if (theta > 0 && theta < angle1) {
				// launchAnimation(0);
				anim = true;
				partClicked = 0;
			} else if (theta >= angle1 && theta < angle1 + angle2) {
				// launchAnimation(1);
				anim = true;
			} else if (theta >= angle1 + angle2
					&& theta < angle1 + angle2 + angle3) {
				// launchAnimation(2);
				anim = true;
			} else if (theta >= angle1 + angle2 + angle3
					&& theta < angle1 + angle2 + angle3 + angle4) {
				// launchAnimation(3);
				anim = true;
			} else if (theta >= angle1 + angle2 + angle3 + angle4
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5) {
				// launchAnimation(4);
				anim = true;
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6) {
				// launchAnimation(5);
				anim = true;
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6 + angle7) {
				// launchAnimation(6);
				anim = true;
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6 + angle7) {
				// launchAnimation(7);
				anim = true;
			}
		}
	}

	public void mouseReleased() {
		pressed = false;
		if (anim) {
			while (angle1 < 360) {
				delay(3);
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

			while (pie.maxDiams() < width + 300
					|| pie.maxDiams() < height + 300) {
				delay(1);
				pie.setDiams((float) (pie.getDiam() + 2));
				redraw();
			}

			backgroundColor = pie.getColor().get(0);
			secondActivity = true;
			anim = false;
		}
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

	public void mouseMoved() {
		pieInteraction();
	}

	public void keyPressed() {
		secondActivity = false;
		redraw();
		backgroundColor.setRed(255);
		backgroundColor.setGreen(255);
		backgroundColor.setBlue(255);
	}
}
