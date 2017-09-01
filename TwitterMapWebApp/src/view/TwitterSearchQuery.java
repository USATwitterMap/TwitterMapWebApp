package view;

public class TwitterSearchQuery 
{
	private String[][] searchData;
	private boolean populationControl;
	
	public String[][] getSearchData() {
		return searchData;
	}
	public void setSearchData(String[][] searchData) {
		this.searchData = searchData;
	}
	public boolean isPopulationControl() {
		return populationControl;
	}
	public void setPopulationControl(boolean populationControl) {
		this.populationControl = populationControl;
	}

}
