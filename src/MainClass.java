import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.JSONArray;
import processing.data.JSONObject;
import Model.Couple;
import Model.Movie;
import Model.RGB;
import Model.Triplet;

public class MainClass extends PApplet {

	private static final long serialVersionUID = 1L;

	private PieChart pie;
	private TimeLine timeLine;
	private boolean pressed = false;
	private JSONObject movies;
	private ArrayList<Movie> movieList = new ArrayList<Movie>();
	private JSONArray movs;
	private ArrayList<String> test = new ArrayList<String>();
	private float angle1, angle2, angle3, angle4, angle5;
	private boolean anim = false;

	private float theta;
	private float ro;

	private boolean clicked = false;

	float[] angles = new float[5];

	private RGB backgroundColor = new RGB(255, 255, 255);

	private PGraphics graphGraphic;
	private PGraphics pieGraphic;
	private PGraphics timeLineGraphic;
	private PGraphics arcDiagramGraphic;

	private int leftPanelWidth = 800;
	private int leftPanelHeight = 500;
	private int rightPanelWidth = 800;
	private int rightPanelHeight = 500;
	private int bottomPanelWidth = 1600;
	private int bottomPanelHeight = 400;
	private int topPanelWidth = 1600;
	private int topPanelHeight = 100;
	private static final float pieDiameter = 300;
	String[] captionNames = new String[5];
	private RGB[] colorsGraph = new RGB[5];
	private RGB defaultColor = new RGB(0, 102, 153);

	int nbPart1 = 0;
	int nbPart2 = 0;
	int nbPart3 = 0;
	int nbPart4 = 0;
	int nbPart5 = 0;

	private int partClicked = -1;
	private boolean setupFinished = false;

	private ArrayList<Float> xCoord = new ArrayList<Float>();
	private ArrayList<Float> yCoord = new ArrayList<Float>();

	private ArcDiagram2 ad;


	private Set<Triplet> associatedGenres = new HashSet<Triplet>();
	private ArrayList<String> genreNames = new ArrayList<String>();

	public void getAssociatedGenres() {
		synchronized (movieList) {

			associatedGenres.clear();

			for (Movie m : movieList) {
				ArrayList<String> genres = m.getGenres();
				for (int i = 0; i < genres.size(); i++) {
					// Get the name of the genre
					String genre1 = genres.get(i);
					if (!genreNames.contains(genre1)) {
						genreNames.add(genre1);
					}
					// For all of the other genres
					for (int j = 0; j < genres.size(); j++) {
						boolean isIN = false;
						if (i != j) {
							String genre2 = genres.get(j);
							// For each item in the set
							for (Triplet t : associatedGenres) {
								// If triplet exists
								if (t.contains(genre1) && t.contains(genre2)) {
									t.inc();
									isIN = true;
								}
							}
							if (!isIN) {
								// ADD new entry
								associatedGenres.add(new Triplet(genre1,
										genre2, 1));
							}
						}
					}// END for j
				}// END for i
			}// END for movieList
		}
	}

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

		for (int i = 0; i < 5; i++) {
			colorsGraph[i] = defaultColor;
		}

		size(1600, 1000);
		pieGraphic = createGraphics(leftPanelWidth, leftPanelHeight);
		graphGraphic = createGraphics(bottomPanelWidth, bottomPanelHeight);
		timeLineGraphic = createGraphics(topPanelWidth, topPanelHeight);
		arcDiagramGraphic = createGraphics(rightPanelWidth, rightPanelHeight);

		background(backgroundColor.getRed(), backgroundColor.getGreen(),
				backgroundColor.getBlue());

		// Set the timeline
		timeLine = new TimeLine(timeLineGraphic, 1990, 2013, 2001,
				new Point2D.Float(30, topPanelHeight / 2), new Point2D.Float(
						topPanelWidth - 30, topPanelHeight / 2),
				new Point2D.Float(topPanelWidth / 2, topPanelHeight / 2), 0, 0);

		movies = loadJSONObject("../imdb_1990/imdb-movies.json");

		movs = movies.getJSONArray("movies");

		pie = new PieChart(pieGraphic, pieDiameter, new float[1],
				pieGraphic.width / 2, pieGraphic.height / 2);

		setPieChartAngles();

		getAssociatedGenres();

		ad = new ArcDiagram2(arcDiagramGraphic, associatedGenres, pieDiameter,
				new Point2D.Float(arcDiagramGraphic.width / 2,
						arcDiagramGraphic.height / 2), genreNames);

		createCaption();

		setupFinished = true;
	}

	public void draw() {
		synchronized (movieList) {

			// DRAW TIMELINE
			timeLineGraphic.beginDraw();
			timeLineGraphic.background(255);
			timeLineGraphic.noFill();
			timeLineGraphic.strokeWeight(5);
			timeLineGraphic.rect(0,0, topPanelWidth,topPanelHeight);
			timeLineGraphic.strokeWeight(2);
			timeLine.drawTimeLine();
			timeLineGraphic.endDraw();
			image(timeLineGraphic, 0, 0);

			// DRAW ARCDIAGRAM
			arcDiagramGraphic.beginDraw();
			arcDiagramGraphic.background(255);
			arcDiagramGraphic.noFill();
			arcDiagramGraphic.strokeWeight(5);
			arcDiagramGraphic.rect(0,0, rightPanelWidth,rightPanelHeight);
			arcDiagramGraphic.strokeWeight(2);
			ad.drawArcDiagram();
			arcDiagramGraphic.endDraw();
			image(arcDiagramGraphic, 800, 100);

			// INITIALISE THE GRAPH PANEL
			graphGraphic.beginDraw();
			graphGraphic.background(255);
			graphGraphic.noFill();
			graphGraphic.strokeWeight(5);
			graphGraphic.rect(0, 0, bottomPanelWidth, bottomPanelHeight);
			graphGraphic.strokeWeight(2);
			graphGraphic.endDraw();
			image(graphGraphic, 0, topPanelHeight + leftPanelHeight);

			// DRAW THE PIE
			pieGraphic.beginDraw();
			pieGraphic.background(backgroundColor.getRed(),
					backgroundColor.getGreen(), backgroundColor.getBlue());
			pieGraphic.noFill();
			pieGraphic.strokeWeight(5);
			pieGraphic.rect(0,0, leftPanelWidth,leftPanelHeight);
			pieGraphic.strokeWeight(2);
			pie.drawPieChart();
			drawCaption();
			pieGraphic.endDraw();
			image(pieGraphic, 0, topPanelHeight);

			graphGraphic.beginDraw();
			graphGraphic.background(255);
			graphGraphic.strokeWeight(5);
			graphGraphic.rect(0, 0, bottomPanelWidth, bottomPanelHeight);
			graphGraphic.strokeWeight(2);
			drawGraph();
			graphGraphic.endDraw();
			image(graphGraphic, 0, topPanelHeight + leftPanelHeight);

		}
	}

	public void createCaption() {
		float part = maxBudget(movieList) / 5;

		captionNames[0] = "0 - " + part;
		captionNames[1] = part + " - " + (2 * part);
		captionNames[2] = (2 * part) + " - " + (3 * part);
		captionNames[3] = (3 * part) + " - " + (4 * part);
		captionNames[4] = (4 * part) + " - " + (5 * part);
	}

	public void drawCaption() {
		float beginX = 15;
		float beginY = 15;
		float currY = 0;
		float edge = 15;
		for (int i = 0 ; i < 5; i++) {
			currY += 2 * beginY;
			RGB rgb =  pie.getColor().get(i);
			pieGraphic.fill(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
			pieGraphic.strokeWeight(0);
			pieGraphic.rect(beginX, currY, edge, edge);
			pieGraphic.strokeWeight(2);
			pieGraphic.textSize(12);
			pieGraphic.textAlign(PApplet.LEFT);
			pieGraphic.text(captionNames[i], beginX + edge + 10, currY + (float)0.75 * edge);
		}
	}

	public void drawGraph() {

		int originX = 30;
		int endX = graphGraphic.width - 30;
		int originY = graphGraphic.height - 30;
		int endY = 30;

		graphGraphic.strokeWeight(2);
		graphGraphic.stroke(0, 0, 0);
		graphGraphic.line(originX, originY, originX, endY);
		graphGraphic.line(originX, originY, endX, originY);
		graphGraphic.textSize(16);
		graphGraphic.fill(0, 102, 153);
		graphGraphic.textAlign(CENTER);
		graphGraphic.text("Budjet in $", endX - 30, originY + 16);
		writeVerticalText(graphGraphic, originX, endY, "Means (0-10)");
		xCoord.clear();
		yCoord.clear();

		// DRAW DATA
		for (int i = 0; i < movieList.size(); i++) {
			float maxBudjet = maxBudget(movieList);
			float minBudjet = minBudget(movieList);
			Movie m = movieList.get(i);
			float x1 = map(m.getBudget(), minBudjet, maxBudjet, originX, endX);
			float y1 = map((float) m.getRating().get("mean"), 0, 10, originY,
					endY);
			// xCoord and yCoord at index i correspond to the movie at index i
			xCoord.add(x1);
			yCoord.add(y1);
			if (clicked) {
				RGB rgb;
				switch (partClicked) {
				case 0:
					rgb = colorsGraph[partClicked];
					if (i < nbPart1) {
						graphGraphic.fill(rgb.getRed(), rgb.getGreen(),
								rgb.getBlue());
					} else {
						graphGraphic
								.fill(defaultColor.getRed(),
										defaultColor.getGreen(),
										defaultColor.getBlue());
					}
					break;
				case 1:
					rgb = colorsGraph[partClicked];
					if (i >= nbPart1 && i < nbPart1 + nbPart2) {
						graphGraphic.fill(rgb.getRed(), rgb.getGreen(),
								rgb.getBlue());
					} else {
						graphGraphic
								.fill(defaultColor.getRed(),
										defaultColor.getGreen(),
										defaultColor.getBlue());
					}
					break;
				case 2:
					rgb = colorsGraph[partClicked];
					if (i >= nbPart1 + nbPart2
							&& i < nbPart1 + nbPart2 + nbPart3) {
						graphGraphic.fill(rgb.getRed(), rgb.getGreen(),
								rgb.getBlue());
						println(i + " inf to nbPart3");
					} else {
						graphGraphic
								.fill(defaultColor.getRed(),
										defaultColor.getGreen(),
										defaultColor.getBlue());
					}
					break;
				case 3:
					rgb = colorsGraph[partClicked];
					if (i >= nbPart1 + nbPart2 + nbPart3
							&& i < nbPart1 + nbPart2 + nbPart3 + nbPart4) {
						graphGraphic.fill(rgb.getRed(), rgb.getGreen(),
								rgb.getBlue());
					} else {
						graphGraphic
								.fill(defaultColor.getRed(),
										defaultColor.getGreen(),
										defaultColor.getBlue());
					}
					break;
				case 4:
					rgb = colorsGraph[partClicked];
					if (i >= nbPart1 + nbPart2 + nbPart3 + nbPart4) {
						graphGraphic.fill(rgb.getRed(), rgb.getGreen(),
								rgb.getBlue());
					} else {
						graphGraphic
								.fill(defaultColor.getRed(),
										defaultColor.getGreen(),
										defaultColor.getBlue());
					}
					break;
				default:

					graphGraphic.fill(defaultColor.getRed(),
							defaultColor.getGreen(), defaultColor.getBlue());
					break;

				}

			} else {
				graphGraphic.fill(defaultColor.getRed(),
						defaultColor.getGreen(), defaultColor.getBlue());
			}
			graphGraphic.ellipse(x1, y1, 10, 10);
		}

		for (int i = 0; i < movieList.size(); i++) {
			Movie m = movieList.get(i);
			if (Math.abs(xCoord.get(i) - mouseX) <= 5
					&& Math.abs(yCoord.get(i)
							- (mouseY - topPanelHeight - leftPanelHeight)) <= 5) {
				graphGraphic.text(m.getTitle() + " : " + m.getBudget() + ", "
						+ m.getRating().get("mean") + "/10", mouseX, (mouseY
						- topPanelHeight - leftPanelHeight - 10));
			}
		}
	}

	public float maxBudget(ArrayList<Movie> ml) {
		float max = 0;
		for (int i = 0; i < ml.size(); i++) {
			Movie m = ml.get(i);
			float budg = m.getBudget();
			if (budg > max) {
				max = budg;
			}
		}
		return max;
	}

	public float minBudget(ArrayList<Movie> ml) {
		float min = Float.POSITIVE_INFINITY;
		for (int i = 0; i < ml.size(); i++) {
			float budg = ml.get(i).getBudget();
			if (budg < min) {
				min = budg;
			}
		}
		return min;
	}

	public void writeVerticalText(PGraphics pg, int x, int y, String text) {
		pushMatrix();
		translate(x, y);
		rotate(-HALF_PI);
		pg.text(text, 0, 0);
		popMatrix();
	}

	public void getMovies() {
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
	}

	public void setPieChartAngles() {
		getMovies();

		int nbMovies = movieList.size();

		// Calculate the max budjet of all the films
		float maxBudjet = maxBudget(movieList);

		nbPart1 = 0;
		nbPart2 = 0;
		nbPart3 = 0;
		nbPart4 = 0;
		nbPart5 = 0;

		// Divide in 5 parts
		double part = maxBudjet / 5;
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
		}

		angle1 = (float) (nbPart1 * 360) / nbMovies;
		angle2 = (float) (nbPart2 * 360) / nbMovies;
		angle3 = (float) (nbPart3 * 360) / nbMovies;
		angle4 = (float) (nbPart4 * 360) / nbMovies;
		angle5 = 360 - angle1 - angle2 - angle3 - angle4;

		angles[0] = angle1;
		angles[1] = angle2;
		angles[2] = angle3;
		angles[3] = angle4;
		angles[4] = angle5;
		pie.setAngles(angles);
	}

	/**
	 * When the mouse is moved and a mouse button is not pressed
	 */
	public void pieInteraction() {
		int nbMovies = movieList.size();
		// Get polar coords
		float[] newCoords = CartesianToPolar(mouseX, mouseY - topPanelHeight);
		// convert theta from radians to degrees
		theta = (float) ((newCoords[1] * 180) / Math.PI);
		ro = newCoords[0];
		if (ro < pie.getDiam() / 2) {
			if (theta > 0 && theta < angle1) {
				pie.setDiams(pieDiameter);
				// If clicked : classical interaction with keep
				if (clicked) {
					if (partClicked != -1) {
						pie.setDiam(pieDiameter + 50, partClicked);
						
					}
				}
				pie.setDiam(pieDiameter + 50, 0);
				pie.setClicked(true);
				pie.setTextToDisplay(((nbPart1*100)/nbMovies)+"%");
				pie.setMouseX(mouseX);
				pie.setMouseY(mouseY - topPanelHeight);
			} else if (theta >= angle1 && theta < angle1 + angle2) {
				pie.setDiams(pieDiameter);
				// If clicked : classical interaction with keep
				if (clicked) {
					if (partClicked != -1) {
						pie.setDiam(pieDiameter + 50, partClicked);
					}
				}
				pie.setDiam(pieDiameter + 50, 1);
				pie.setClicked(true);
				pie.setTextToDisplay(((nbPart2*100)/nbMovies)+"%");
				pie.setMouseX(mouseX);
				pie.setMouseY(mouseY - topPanelHeight);
			} else if (theta >= angle1 + angle2
					&& theta < angle1 + angle2 + angle3) {
				pie.setDiams(pieDiameter);
				// If clicked : classical interaction with keep
				if (clicked) {
					if (partClicked != -1) {
						pie.setDiam(pieDiameter + 50, partClicked);
					}
				}
				pie.setDiam(pieDiameter + 50, 2);
				pie.setClicked(true);
				pie.setTextToDisplay(((nbPart3*100)/nbMovies)+"%");
				pie.setMouseX(mouseX);
				pie.setMouseY(mouseY - topPanelHeight);
			} else if (theta >= angle1 + angle2 + angle3
					&& theta < angle1 + angle2 + angle3 + angle4) {
				pie.setDiams(pieDiameter);
				// If clicked : classical interaction with keep
				if (clicked) {
					if (partClicked != -1) {
						pie.setDiam(pieDiameter + 50, partClicked);
					}
				}
				pie.setDiam(pieDiameter + 50, 3);
				pie.setClicked(true);
				pie.setTextToDisplay(((nbPart4*100)/nbMovies)+"%");
				pie.setMouseX(mouseX);
				pie.setMouseY(mouseY - topPanelHeight);
			} else if (theta >= angle1 + angle2 + angle3 + angle4) {
				pie.setDiams(pieDiameter);
				// If clicked : classical interaction with keep
				if (clicked) {
					if (partClicked != -1) {
						pie.setDiam(pieDiameter + 50, partClicked);
					}
				}
				pie.setDiam(pieDiameter + 50, 4);
				pie.setClicked(true);
				pie.setTextToDisplay(((nbPart5*100)/nbMovies)+"%");
				pie.setMouseX(mouseX);
				pie.setMouseY(mouseY - topPanelHeight);
			}
		} else {
			pie.setDiams(pieDiameter);
			// If clicked : classical interaction with keep
			if (clicked) {
				if (partClicked != -1) {
					pie.setDiam(pieDiameter + 50, partClicked);
				}
			}
			pie.setClicked(false);
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
		// IF click on timeline
		if (Math.abs(mouseX - timeLine.getCurrentPosition().getX()) < 20
				&& Math.abs(mouseY - timeLine.getCurrentPosition().getY()) < 20) {
			pressed = true;
		}

		if (pie != null) {
			// If click in a part of the pie
			if (ro < pie.getDiam() / 2) {
				clicked = true;
				if (theta > 0 && theta < angle1) {
					colorsGraph[0] = pie.getColor().get(0);
					partClicked = 0;
				} else if (theta >= angle1 && theta < angle1 + angle2) {
					colorsGraph[1] = pie.getColor().get(1);
					partClicked = 1;
				} else if (theta >= angle1 + angle2
						&& theta < angle1 + angle2 + angle3) {
					colorsGraph[2] = pie.getColor().get(2);
					partClicked = 2;
				} else if (theta >= angle1 + angle2 + angle3
						&& theta < angle1 + angle2 + angle3 + angle4) {
					colorsGraph[3] = pie.getColor().get(3);
					partClicked = 3;
				} else if (theta >= angle1 + angle2 + angle3 + angle4) {
					colorsGraph[4] = pie.getColor().get(4);
					partClicked = 4;
				}
			} else {
				for (int i = 0; i < colorsGraph.length; i++) {
					colorsGraph[i] = defaultColor;
				}
				clicked = false;
				partClicked = -1;
			}
		}
		redraw();
	}

	public void mouseReleased() {
		pressed = false;
		redraw();
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
				setPieChartAngles();
				getAssociatedGenres();
			}
			redraw();
		}
	}

	public void mouseMoved() {
		if (setupFinished) {
			pieInteraction();
			arcDiagramInteraction();
		}
	}

	public void arcDiagramInteraction() {
		// Get all the points
		HashMap<String, Point2D.Float> genresMap = ad.getGenrePoints();
		for (String s : genresMap.keySet()) {
			Point2D.Float p = genresMap.get(s);
			// IF mouse on the point p

			if (Math.abs(mouseX - (p.getX() + leftPanelWidth)) < 10
					&& Math.abs(mouseY - (p.getY() + topPanelHeight)) < 10) {
				ad.setGenreHL(s);
				ad.setHL(true);
				ad.setHLPoint(p);
				redraw();
				break;
			} else {
				ad.setGenreHL("");
				ad.setHL(false);
				redraw();
			}
		}
	}
}
