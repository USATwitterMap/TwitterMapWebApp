package service;

import java.util.ArrayList;
import java.util.List;

import dao.results.StatePopulation;
import search.TwitterWordSearch;
import view.LineGraphView;
import view.MultiWordView;
import view.SingleWordView;

public interface GeoChartService {
	
	public SingleWordView[] CreateGeoChartView(MultiWordView results, boolean popControl);
}
