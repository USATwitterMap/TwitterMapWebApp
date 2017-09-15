package dao.results;

import java.util.List;

public class TwitterWordData {
	private String state;
	private String word;
	private int occurances;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getOccurances() {
		return occurances;
	}
	public void setOccurances(int occurances) {
		this.occurances = occurances;
	}
	
}
