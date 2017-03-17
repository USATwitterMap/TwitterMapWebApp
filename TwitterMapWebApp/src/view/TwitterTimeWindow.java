package view;

import java.util.Date;

public class TwitterTimeWindow {
	private Date earliestDate;
	private Date latestDate;
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
