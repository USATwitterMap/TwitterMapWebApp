package dao.results;

import java.sql.Timestamp;

public class SystemDiag {
	private Timestamp forDate;
	private int numPosts;
	public Timestamp getForDate() {
		return forDate;
	}
	public void setForDate(Timestamp forDate) {
		this.forDate = forDate;
	}
	public int getNumPosts() {
		return numPosts;
	}
	public void setNumPosts(int numPosts) {
		this.numPosts = numPosts;
	}
}
