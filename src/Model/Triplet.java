package Model;

import java.util.ArrayList;

public class Triplet {

	private String genre1;
	private String genre2;
	private int number;
	public Triplet(String genre1, String genre2, int number) {
		super();
		this.genre1 = genre1;
		this.genre2 = genre2;
		this.number = number;
	}
	public String getGenre1() {
		return genre1;
	}
	public void setGenre1(String genre1) {
		this.genre1 = genre1;
	}
	public String getGenre2() {
		return genre2;
	}
	public void setGenre2(String genre2) {
		this.genre2 = genre2;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void inc() {
		number++;
	}
	
	public boolean contains(String name) {
		return genre1.equals(name) || genre2.equals(name);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(genre1+"/"+genre2+"/"+number);
		return new String(sb);
	}
	
	
	
	

}
