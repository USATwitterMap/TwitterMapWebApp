package service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import search.TwitterWordSearch;
import view.LineGraphView;
import view.LocationView;
import view.MultiWordView;

@Service
public class PieGraphServiceImpl implements PieGraphService {

	@Override
	public MultiWordView CreatePieGraphView(TwitterWordSearch search, LineGraphView results) {
		
		//prepare transform and return structure
		MultiWordView view = new MultiWordView();
		view.setLocations(new LocationView[50]);
		
		Map<String, Map<String, Double>> storedByState = new HashMap<String, Map<String, Double>>();
		for(StateLocations location : StateLocations.values()) 
		{
			storedByState.put(location.getStateAbbr(), new HashMap<String, Double>());
		}
		
		//combine occurances across all timestamps
		for(int timeSegIndex = 1; timeSegIndex < results.getLineData().length; timeSegIndex++)
		{
			for(int wordIndex = 1; wordIndex < results.getLineData()[timeSegIndex].length; wordIndex++)
			{
				String thisState = ((String)results.getLineData()[0][wordIndex]).substring(0,  ((String)results.getLineData()[0][wordIndex]).indexOf(":"));
				if(!thisState.contains("OVERALL")) 
				{
					Double thisOccurances = ((Double)results.getLineData()[timeSegIndex][wordIndex]);
					String thisWord = ((String)results.getLineData()[0][wordIndex]).substring(4);
					Double occurancesSummed = storedByState.get(thisState).get(thisWord);
					if(occurancesSummed == null) {
							storedByState.get(thisState).put(thisWord, thisOccurances);
					}
					else {
						storedByState.get(thisState).put(thisWord, occurancesSummed + thisOccurances);
					}
				}
			}
		}
		
		//create location objects and format data for pie chart consumption
		StateLocations[] states = StateLocations.values();
		for(int stateIndex = 0; stateIndex < states.length; stateIndex++) 
		{
			String thisAbbr = states[stateIndex].getStateAbbr();
			Map<String, Double> stateData = storedByState.get(thisAbbr);
			Double[] wordData = new Double[search.getSearchData().length];
			stateData.values().toArray(wordData);
			
			view.getLocations()[stateIndex] = new LocationView();
			view.getLocations()[stateIndex].setStateName("US-" + thisAbbr);
			view.getLocations()[stateIndex].setLatitude(states[stateIndex].getLatitude());
			view.getLocations()[stateIndex].setLongitude(states[stateIndex].getLongitude());
			view.getLocations()[stateIndex].setWordData(new Object[wordData.length][3]);
			
			//this assumes they are all ordered in the same order
			for(int searchIndex = 0; searchIndex < search.getSearchData().length; searchIndex++)
			{
				view.getLocations()[stateIndex].getWordData()[searchIndex][0] = search.getSearchData()[searchIndex][0];
				view.getLocations()[stateIndex].getWordData()[searchIndex][1] = search.getSearchData()[searchIndex][1];
				view.getLocations()[stateIndex].getWordData()[searchIndex][2] = stateData.get(search.getSearchData()[searchIndex][0]);
			}
		}
		return view;
	}

}
