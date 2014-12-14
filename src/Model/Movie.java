package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import processing.data.JSONArray;
import processing.data.JSONObject;

public class Movie {

	private int bo;
	private String title;
	private ArrayList<String> genres = new ArrayList<String>();
	private int year;
	private int budget;
	private int mid;
	private HashMap<String, JSONArray> peoplesID = new HashMap<String, JSONArray>();
	/*
	 * Contains Costume_Designer Actor Writer Composer Actress Producer Director
	 * Cinematographer
	 */
	private HashMap<String, Object> rating = new HashMap<String, Object>();
	/*
	 * Contains votes int mean float distribution string
	 */
	private String date;

	public Movie(int bo, String title, JSONArray genres, int year, int budget,
			int mid, JSONObject peoplesID, JSONObject rating, String date) {
		super();
		this.bo = bo;
		this.title = title;
		for (int i = 0; i < genres.size(); i++) {
			this.genres.add(genres.getString(i));
		}
		this.year = year;
		this.budget = budget;
		this.mid = mid;

		this.peoplesID.put("Costume_Designer",
				peoplesID.getJSONArray("Costume_Designer"));
		this.peoplesID.put("Actor", peoplesID.getJSONArray("Actor"));
		this.peoplesID.put("Writer", peoplesID.getJSONArray("Writer"));
		this.peoplesID.put("Composer", peoplesID.getJSONArray("Composer"));
		this.peoplesID.put("Actress", peoplesID.getJSONArray("Actress"));
		this.peoplesID.put("Producer", peoplesID.getJSONArray("Producer"));
		this.peoplesID.put("Director", peoplesID.getJSONArray("Director"));
		this.peoplesID.put("Cinematographer",
				peoplesID.getJSONArray("Cinematographer"));

		this.rating.put("votes", rating.getInt("votes"));
		this.rating.put("mean", rating.getFloat("mean"));
		this.rating.put("distribution", rating.getString("distribution"));

		this.date = date;
	}

	public int getBo() {
		return bo;
	}

	public void setBo(int bo) {
		this.bo = bo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(JSONArray genres) {
		for (int i = 0; i < genres.size(); i++) {
			this.genres.add(genres.getString(i));
		}
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public HashMap<String, JSONArray> getPeoplesID() {
		return peoplesID;
	}

	public void setPeoplesID(JSONObject peoplesID) {
		this.peoplesID.put("Costume_Designer",
				peoplesID.getJSONArray("Costume_Desinger"));
		this.peoplesID.put("Actor", peoplesID.getJSONArray("Actor"));
		this.peoplesID.put("Writer", peoplesID.getJSONArray("Writer"));
		this.peoplesID.put("Composer", peoplesID.getJSONArray("Composer"));
		this.peoplesID.put("Actress", peoplesID.getJSONArray("Actress"));
		this.peoplesID.put("Producer", peoplesID.getJSONArray("Producer"));
		this.peoplesID.put("Director", peoplesID.getJSONArray("Director"));
		this.peoplesID.put("Cinematographer",
				peoplesID.getJSONArray("Cinematographer"));
	}

	public HashMap<String, Object> getRating() {
		return rating;
	}

	public void setRating(JSONObject rating) {
		this.rating.put("votes", rating.getInt("votes"));
		this.rating.put("mean", rating.getFloat("mean"));
		this.rating.put("distribution", rating.getString("dstribution"));
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
