package service;

import view.LineGraphView;
import view.LocationView;
import view.MultiWordView;
import view.SingleWordView;

import org.springframework.stereotype.Service;

import search.TwitterWordSearch;

@Service
public class GeoChartServiceImpl implements GeoChartService {
	
	public SingleWordView[] CreateGeoChartView(MultiWordView results) {
		
		SingleWordView[] views = new SingleWordView[results.getLocations()[0].getWordData().length];
		
		for(int wordIndex = 0; wordIndex < results.getLocations()[0].getWordData().length; wordIndex++) 
		{
			views[wordIndex] = new SingleWordView();
			
			views[wordIndex].setAreaChart(new Object[results.getLocations().length + 1][2]);
			views[wordIndex].getAreaChart()[0][0] = "State";
			views[wordIndex].getAreaChart()[0][1] = results.getLocations()[0].getWordData()[wordIndex][0];
			views[wordIndex].setColor((String)results.getLocations()[0].getWordData()[wordIndex][1]);
			for(int locationIndex = 0; locationIndex < results.getLocations().length; locationIndex++) 
			{
				views[wordIndex].getAreaChart()[locationIndex + 1][0] = results.getLocations()[locationIndex].getStateName();
				views[wordIndex].getAreaChart()[locationIndex + 1][1] = results.getLocations()[locationIndex].getWordData()[wordIndex][2];
			}
		}
		return views;
	}
}
