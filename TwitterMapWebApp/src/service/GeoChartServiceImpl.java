package service;

import view.LineGraphView;
import view.LocationView;
import view.MultiWordView;
import view.SingleWordView;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import dao.StatePopulationDao;
import dao.results.StatePopulation;

@Service
public class GeoChartServiceImpl implements GeoChartService {
	
	@Autowired
	private StatePopulationDao popDao;
	
	public SingleWordView[] CreateGeoChartView(MultiWordView results, boolean popControl) {
		
		SingleWordView[] views = new SingleWordView[results.getLocations()[0].getWordData().length];
		
		List<StatePopulation> populationData = null;
		if(popControl) 
		{
			populationData = popDao.GetPopulation();
		}
		
		for(int wordIndex = 0; wordIndex < results.getLocations()[0].getWordData().length; wordIndex++) 
		{
			views[wordIndex] = new SingleWordView();
			
			views[wordIndex].setAreaChart(new Object[results.getLocations().length + 1][2]);
			views[wordIndex].getAreaChart()[0][0] = "State";
			views[wordIndex].getAreaChart()[0][1] = results.getLocations()[0].getWordData()[wordIndex][0];
			views[wordIndex].setColor((String)results.getLocations()[0].getWordData()[wordIndex][1]);
			for(int locationIndex = 0; locationIndex < results.getLocations().length; locationIndex++) 
			{
				String state = results.getLocations()[locationIndex].getStateName();
				state = state.substring(state.length() - 2);
				double occurances = 0;
				if(results.getLocations()[locationIndex].getWordData()[wordIndex][2] != null) {
					occurances = (double)results.getLocations()[locationIndex].getWordData()[wordIndex][2];
				}
				views[wordIndex].getAreaChart()[locationIndex + 1][0] = state;
				if(populationData != null) 
				{
					for(StatePopulation population : populationData) 
					{
						if(population.getState().equals(state)) 
						{
							double factor = population.getPopulation() / 1000000;
							if(factor > 0) 
							{						
								occurances = occurances / factor;
							}
							else 
							{
								occurances = 0;
							}
							break;
						}
					}
				}
				views[wordIndex].getAreaChart()[locationIndex + 1][1] = occurances;
			}
		}
		return views;
	}
}
