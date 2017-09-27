package dao.results;

import java.sql.Timestamp;

public class TwitterWordTimeData extends TwitterWordData
{
	private int timeSegment;
	private Timestamp time;

	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getTimeSegment() {
		return timeSegment;
	}
	public void setTimeSegment(int timeSegment) {
		this.timeSegment = timeSegment;
	}
}
