package view;

import java.util.Date;

public class SearchScreenView {
	private Date earliestDate;
	private Date latestDate;
	private double[][] locations;
	
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
	public double[][] getLocations() {
		return locations;
	}
	public void setLocations(double[][] locations) {
		this.locations = locations;
	}
}
