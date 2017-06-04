package view;

import java.util.List;

public class TwitterWordData {
	private long nextTime;
	private List<MapMarker> markers;

	public long getNextTime() {
		return nextTime;
	}

	public void setNextTime(long nextTime) {
		this.nextTime = nextTime;
	}

	public List<MapMarker> getMarkers() {
		return markers;
	}

	public void setMarkers(List<MapMarker> markers) {
		this.markers = markers;
	}
}
