package view;

public class MultiWordView {
	private LocationView[] locations;
	private Object[][] systemHealth;

	public LocationView[] getLocations() {
		return locations;
	}

	public void setLocations(LocationView[] locations) {
		this.locations = locations;
	}

	public Object[][] getSystemHealth() {
		return systemHealth;
	}

	public void setSystemHealth(Object[][] systemHealth) {
		this.systemHealth = systemHealth;
	}
}
