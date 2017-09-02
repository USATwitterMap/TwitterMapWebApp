package view;

public class SingleWordView {
	private Object[][] areaChart;
	private String color;
	private Object[][] systemHealth;

	public Object[][] getAreaChart() {
		return areaChart;
	}

	public void setAreaChart(Object[][] areaChart) {
		this.areaChart = areaChart;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Object[][] getSystemHealth() {
		return systemHealth;
	}

	public void setSystemHealth(Object[][] systemHealth) {
		this.systemHealth = systemHealth;
	}
}
