package service;

import search.TwitterWordSearch;
import view.LineGraphView;
import view.MultiWordView;

public interface PieGraphService {
	public MultiWordView CreatePieGraphView(TwitterWordSearch search, LineGraphView results);
}
