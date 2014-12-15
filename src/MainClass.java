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

	private float theta;
	private float ro;

	private RGB backgroundColor = new RGB(255, 255, 255);

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
	private boolean setupFinished = false;

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
		timeLine = new TimeLine(this, 1990, 2013, 2010, new Point2D.Float(30,
				50), new Point2D.Float(770, 50), new Point2D.Float(700, 50), 0,
				0);

		readData();

		movs = movies.getJSONArray("movies");

		pie = new PieChart(this, pieDiameter, new float[1], width / 2,
				height / 2);

		setupFinished = true;

	}

	public void draw() {
		background(backgroundColor.getRed(), backgroundColor.getGreen(),
				backgroundColor.getBlue());
		// If first actvity
		if (!secondActivity) {
			timeLine.drawTimeLine();
			if (!anim) {
				setPieChartAngles();
			}
			pie.drawPieChart();

		} else if (secondActivity) {
			ArrayList<Movie> ml = new ArrayList<Movie>();
			switch (partClicked) {
			case 0:
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
				break;
			case 1:
				for (int i = nbPart1; i < nbPart1 + nbPart2; i++) {
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
				break;
			case 2:
				for (int i = nbPart1 + nbPart2; i < nbPart1 + nbPart2 + nbPart3; i++) {
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
				break;
			case 3:
				for (int i = nbPart1 + nbPart2 + nbPart3; i < nbPart1 + nbPart2
						+ nbPart3 + nbPart4; i++) {
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
				break;
			case 4:
				for (int i = nbPart1 + nbPart2 + nbPart3 + nbPart4; i < nbPart1
						+ nbPart2 + nbPart3 + nbPart4 + nbPart5; i++) {
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
				break;
			case 5:
				for (int i = nbPart1 + nbPart2 + nbPart3 + nbPart4 + nbPart5; i < nbPart1
						+ nbPart2 + nbPart3 + nbPart4 + nbPart5 + nbPart6; i++) {
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
				break;
			case 6:
				for (int i = nbPart1 + nbPart2 + nbPart3 + nbPart4 + nbPart5
						+ nbPart6; i < nbPart1 + nbPart2 + nbPart3 + nbPart4
						+ nbPart5 + nbPart6 + nbPart7; i++) {
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
				break;
			case 7:
				for (int i = nbPart1 + nbPart2 + nbPart3 + nbPart4 + nbPart5
						+ nbPart6 + nbPart7; i < nbPart1 + nbPart2 + nbPart3
						+ nbPart4 + nbPart5 + nbPart6 + nbPart7 + nbPart8; i++) {
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
				break;
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
					&& timeLine.getCurrentDate() == Integer.parseInt(mov
							.getString("date").substring(0, 4))
					&& mov.getInt("budjet") != 0) {
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
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 0);
			} else if (theta >= angle1 && theta < angle1 + angle2) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 1);
			} else if (theta >= angle1 + angle2
					&& theta < angle1 + angle2 + angle3) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 2);
			} else if (theta >= angle1 + angle2 + angle3
					&& theta < angle1 + angle2 + angle3 + angle4) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 3);
			} else if (theta >= angle1 + angle2 + angle3 + angle4
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 4);
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 5);
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6 + angle7) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 6);
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6 + angle7) {
				pie.setDiams(pieDiameter);
				pie.setDiam(pieDiameter + 50, 7);
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
				anim = true;
				partClicked = 0;
			} else if (theta >= angle1 && theta < angle1 + angle2) {
				anim = true;
				partClicked = 1;
			} else if (theta >= angle1 + angle2
					&& theta < angle1 + angle2 + angle3) {
				anim = true;
				partClicked = 2;
			} else if (theta >= angle1 + angle2 + angle3
					&& theta < angle1 + angle2 + angle3 + angle4) {
				anim = true;
				partClicked = 3;
			} else if (theta >= angle1 + angle2 + angle3 + angle4
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5) {
				anim = true;
				partClicked = 4;
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6) {
				anim = true;
				partClicked = 5;
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6
					&& theta < angle1 + angle2 + angle3 + angle4 + angle5
							+ angle6 + angle7) {
				anim = true;
				partClicked = 6;
			} else if (theta >= angle1 + angle2 + angle3 + angle4 + angle5
					+ angle6 + angle7) {
				anim = true;
				partClicked = 7;
			}
		}
	}

	public void mouseReleased() {
		pressed = false;
		if (anim) {
			float[] angles = { angle1, angle2, angle3, angle4, angle5, angle6,
					angle7, angle8 };
			switch (partClicked) {
			case 0:
				while (angle1 < 360) {
					delay(3);
					angle1 += 1;
					angles[0] = angle1;
					if (angle2 > 0) {
						angle2 -= 1;
						angles[1] = angle2;
					} else if (angle3 > 0) {
						angle3 -= 1;
						angles[1] = 0;
						angles[2] = angle3;
					} else if (angle4 > 0) {
						angle4 -= 1;
						angles[2] = 0;
						angles[3] = angle4;
					} else if (angle5 > 0) {
						angle5 -= 1;
						angles[3] = 0;
						angles[4] = angle5;
					} else if (angle6 > 0) {
						angle6 -= 1;
						angles[4] = 0;
						angles[5] = angle6;
					} else if (angle7 > 0) {
						angle7 -= 1;
						angles[5] = 0;
						angles[6] = angle7;
					} else if (angle8 > 0) {
						angle8 -= 1;
						angles[6] = 0;
						angles[7] = angle8;
					}
					angles[7] = 0;
					pie.setAngles(angles);
					redraw();
				}

				angles[0] = 360;
				pie.setAngles(angles);
				redraw();

				pieZoom();

				backgroundColor = pie.getColor().get(0);
				break;
			case 1:
				while (angle2 < 360) {
					delay(3);
					angle2 += 1;
					angles[1] = angle2;
					if (angle3 > 0) {
						angle3 -= 1;
						angles[1] = 0;
						angles[2] = angle3;
					} else if (angle4 > 0) {
						angle4 -= 1;
						angles[2] = 0;
						angles[3] = angle4;
					} else if (angle5 > 0) {
						angle5 -= 1;
						angles[3] = 0;
						angles[4] = angle5;
					} else if (angle6 > 0) {
						angle6 -= 1;
						angles[4] = 0;
						angles[5] = angle6;
					} else if (angle7 > 0) {
						angle7 -= 1;
						angles[5] = 0;
						angles[6] = angle7;
					} else if (angle8 > 0) {
						angle8 -= 1;
						angles[6] = 0;
						angles[7] = angle8;
					} else if (angle1 > 0) {
						angle1 -= 1;
						angles[7] = 0;
						angles[0] = angle1;
					} else
						angles[0] = 0;
					pie.setAngles(angles);
					redraw();
				}

				angles[1] = 360;
				pie.setAngles(angles);
				redraw();

				pieZoom();

				backgroundColor = pie.getColor().get(1);
				break;
			case 2:
				while (angle3 < 360) {
					delay(3);
					angle3 += 1;
					angles[2] = angle3;
					if (angle4 > 0) {
						angle4 -= 1;
						angles[2] = 0;
						angles[3] = angle4;
					} else if (angle5 > 0) {
						angle5 -= 1;
						angles[3] = 0;
						angles[4] = angle5;
					} else if (angle6 > 0) {
						angle6 -= 1;
						angles[4] = 0;
						angles[5] = angle6;
					} else if (angle7 > 0) {
						angle7 -= 1;
						angles[5] = 0;
						angles[6] = angle7;
					} else if (angle8 > 0) {
						angle8 -= 1;
						angles[6] = 0;
						angles[7] = angle8;
					} else if (angle1 > 0) {
						angle1 -= 1;
						angles[7] = 0;
						angles[0] = angle1;
					} else if (angle2 > 0) {
						angle2 -= 1;
						angles[0] = 0;
						angles[1] = angle2;
					} else
						angles[1] = 0;
					pie.setAngles(angles);
					redraw();
				}

				angles[2] = 360;
				pie.setAngles(angles);
				redraw();

				pieZoom();

				backgroundColor = pie.getColor().get(2);
				break;
			case 3:
				while (angle4 < 360) {
					delay(3);
					angle4 += 1;
					angles[3] = angle4;
					if (angle5 > 0) {
						angle5 -= 1;
						angles[3] = 0;
						angles[4] = angle5;
					} else if (angle6 > 0) {
						angle6 -= 1;
						angles[4] = 0;
						angles[5] = angle6;
					} else if (angle7 > 0) {
						angle7 -= 1;
						angles[5] = 0;
						angles[6] = angle7;
					} else if (angle8 > 0) {
						angle8 -= 1;
						angles[6] = 0;
						angles[7] = angle8;
					} else if (angle1 > 0) {
						angle1 -= 1;
						angles[7] = 0;
						angles[0] = angle1;
					} else if (angle2 > 0) {
						angle2 -= 1;
						angles[0] = 0;
						angles[1] = angle2;
					} else if (angle3 > 0) {
						angle3 -= 1;
						angles[1] = 0;
						angles[2] = angle3;
					} else
						angles[2] = 0;
					pie.setAngles(angles);
					redraw();
				}

				angles[3] = 360;
				pie.setAngles(angles);
				redraw();

				pieZoom();

				backgroundColor = pie.getColor().get(3);
				break;
			case 4:
				while (angle5 < 360) {
					delay(3);
					angle5 += 1;
					angles[4] = angle5;
					if (angle6 > 0) {
						angle6 -= 1;
						angles[4] = 0;
						angles[5] = angle6;
					} else if (angle7 > 0) {
						angle7 -= 1;
						angles[5] = 0;
						angles[6] = angle7;
					} else if (angle8 > 0) {
						angle8 -= 1;
						angles[6] = 0;
						angles[7] = angle8;
					} else if (angle1 > 0) {
						angle1 -= 1;
						angles[7] = 0;
						angles[0] = angle1;
					} else if (angle2 > 0) {
						angle2 -= 1;
						angles[0] = 0;
						angles[1] = angle2;
					} else if (angle3 > 0) {
						angle3 -= 1;
						angles[1] = 0;
						angles[2] = angle3;
					} else if (angle4 > 0) {
						angle4 -= 1;
						angles[2] = 0;
						angles[3] = angle4;
					} else
						angles[3] = 0;
					pie.setAngles(angles);
					redraw();
				}

				angles[4] = 360;
				pie.setAngles(angles);
				redraw();

				pieZoom();

				backgroundColor = pie.getColor().get(4);
				break;
			case 5:
				while (angle6 < 360) {
					delay(3);
					angle6 += 1;
					angles[5] = angle6;
					if (angle7 > 0) {
						angle7 -= 1;
						angles[5] = 0;
						angles[6] = angle7;
					} else if (angle8 > 0) {
						angle8 -= 1;
						angles[6] = 0;
						angles[7] = angle8;
					} else if (angle1 > 0) {
						angle1 -= 1;
						angles[7] = 0;
						angles[0] = angle1;
					} else if (angle2 > 0) {
						angle2 -= 1;
						angles[0] = 0;
						angles[1] = angle2;
					} else if (angle3 > 0) {
						angle3 -= 1;
						angles[1] = 0;
						angles[2] = angle3;
					} else if (angle4 > 0) {
						angle4 -= 1;
						angles[2] = 0;
						angles[3] = angle4;
					} else if (angle5 > 0) {
						angle5 -= 1;
						angles[3] = 0;
						angles[4] = angle5;
					} else
						angles[4] = 0;
					pie.setAngles(angles);
					redraw();
				}

				angles[5] = 360;
				pie.setAngles(angles);
				redraw();

				pieZoom();

				backgroundColor = pie.getColor().get(5);
				break;
			case 6:
				while (angle7 < 360) {
					delay(3);
					angle7 += 1;
					angles[6] = angle7;
					if (angle8 > 0) {
						angle8 -= 1;
						angles[6] = 0;
						angles[7] = angle8;
					} else if (angle1 > 0) {
						angle1 -= 1;
						angles[7] = 0;
						angles[0] = angle1;
					} else if (angle2 > 0) {
						angle2 -= 1;
						angles[0] = 0;
						angles[1] = angle2;
					} else if (angle3 > 0) {
						angle3 -= 1;
						angles[1] = 0;
						angles[2] = angle3;
					} else if (angle4 > 0) {
						angle4 -= 1;
						angles[2] = 0;
						angles[3] = angle4;
					} else if (angle5 > 0) {
						angle5 -= 1;
						angles[3] = 0;
						angles[4] = angle5;
					} else if (angle6 > 0) {
						angle6 -= 1;
						angles[4] = 0;
						angles[5] = angle6;
					} else
						angles[5] = 0;
					pie.setAngles(angles);
					redraw();
				}

				angles[6] = 360;
				pie.setAngles(angles);
				redraw();

				pieZoom();

				backgroundColor = pie.getColor().get(6);
				break;
			case 7:
				while (angle8 < 360) {
					delay(3);
					angle8 += 1;
					angles[7] = angle8;
					if (angle1 > 0) {
						angle1 -= 1;
						angles[7] = 0;
						angles[0] = angle1;
					} else if (angle2 > 0) {
						angle2 -= 1;
						angles[0] = 0;
						angles[1] = angle2;
					} else if (angle3 > 0) {
						angle3 -= 1;
						angles[1] = 0;
						angles[2] = angle3;
					} else if (angle4 > 0) {
						angle4 -= 1;
						angles[2] = 0;
						angles[3] = angle4;
					} else if (angle5 > 0) {
						angle5 -= 1;
						angles[3] = 0;
						angles[4] = angle5;
					} else if (angle6 > 0) {
						angle6 -= 1;
						angles[4] = 0;
						angles[5] = angle6;
					} else if (angle7 > 0) {
						angle7 -= 1;
						angles[5] = 0;
						angles[6] = angle7;
					} else
						angles[6] = 0;
					pie.setAngles(angles);
					redraw();
				}

				angles[7] = 360;
				pie.setAngles(angles);
				redraw();

				pieZoom();

				backgroundColor = pie.getColor().get(6);
				break;
			}// fin switch
			secondActivity = true;
			anim = false;
		}// fin if
	}

	public void pieZoom() {
		while (pie.maxDiams() < width + 300 || pie.maxDiams() < height + 300) {
			delay(1);
			pie.setDiams((float) (pie.getDiam() + 2));
			redraw();
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
		if (setupFinished) {
			pieInteraction();
		}
	}

	public void keyPressed() {
		secondActivity = false;
		redraw();
		backgroundColor.setRed(255);
		backgroundColor.setGreen(255);
		backgroundColor.setBlue(255);
	}
}
