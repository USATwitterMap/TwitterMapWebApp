package view;

import java.util.Date;

public class TwitterMapVO {
	
	private String keyword;
	
	private String date;
	
	private int occurances;
	
	private String state;
	
	private Date earliestDate;
	
	private Date latestDate;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getOccurances() {
		return occurances;
	}
	public void setOccurances(int occurances) {
		this.occurances = occurances;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getEarliestDate() {
		return earliestDate;
	}
	public void setEarliestDate(Date earliestDate) {
		this.earliestDate = earliestDate;
	}
	public Date getLatestDate() {
		return latestDate;
	}
	public void setLatestDate(Date latestDate) {
		this.latestDate = latestDate;
	}
	
	
}
