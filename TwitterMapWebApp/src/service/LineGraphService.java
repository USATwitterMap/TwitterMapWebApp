package service;

import search.TwitterWordSearch;
import view.LineGraphView;

public interface LineGraphService{

	public LineGraphView CreateLineGraphView(TwitterWordSearch query);

}
