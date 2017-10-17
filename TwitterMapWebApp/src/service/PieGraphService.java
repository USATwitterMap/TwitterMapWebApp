package service;

import search.PopularTermsSearch;
import search.TwitterWordSearch;
import view.LineGraphView;
import view.MultiWordView;

public interface PieGraphService {
	public MultiWordView CreatePieGraphView(TwitterWordSearch query);
}
