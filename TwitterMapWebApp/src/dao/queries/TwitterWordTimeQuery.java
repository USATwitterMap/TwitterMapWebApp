package dao.queries;

import java.util.List;

public class TwitterWordTimeQuery extends TwitterWordQuery 
{
	private int segments;

	public int getSegments() {
		return segments;
	}

	public void setSegments(int segments) {
		this.segments = segments;
	}
}
