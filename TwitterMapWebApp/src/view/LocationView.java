package view;

public class LocationView {
	private Object[][] wordData;
	private double latitude;
	private double longitude;
	private String stateName;
	
	public Object[][] getWordData() {
		return wordData;
	}
	public void setWordData(Object[][] wordData) {
		this.wordData = wordData;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
