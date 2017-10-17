package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.StatePopulationDao;
import dao.TwitterDataDao;
import dao.queries.TwitterWordQuery;
import dao.results.TwitterWordData;
import search.PopularTermsSearch;
import search.TwitterWordSearch;
import view.LineGraphView;
import view.LocationView;
import view.MultiWordView;

@Service
public class PieGraphServiceImpl implements PieGraphService {

	@Autowired
	private TwitterDataDao wordsDao;
	
	@Autowired
	private StatePopulationDao popDao;
	
	@Override
	public MultiWordView CreatePieGraphView(TwitterWordSearch query) {
		
		//prepare transform and return structure
		//prepare return structure
		TwitterWordQuery wordQuery = new TwitterWordQuery();
		String[][] search = query.getSearchData();
		wordQuery.setStartDate(search[0][2].substring(0, search[0][2].indexOf(" -")));
		wordQuery.setStopDate(search[0][2].substring(search[0][2].indexOf("- ") + 2));
		wordQuery.setWords(new ArrayList<String>());
		for(int index = 0; index < search.length; index++)
		{
			wordQuery.getWords().add(search[index][0]);
		}
		List<TwitterWordData> results = wordsDao.GetOccurances(wordQuery);
		MultiWordView view = new MultiWordView();
		view.setLocations(new LocationView[50]);
		int locationCounter = 0;
		for(StateLocations state : StateLocations.values()) {
			view.getLocations()[locationCounter] = new LocationView();
			view.getLocations()[locationCounter].setStateName("US-" + state.getStateAbbr());
			view.getLocations()[locationCounter].setLatitude(state.getLatitude());
			view.getLocations()[locationCounter].setLongitude(state.getLongitude());
			view.getLocations()[locationCounter].setWordData(new Object[search.length][3]);
			for(int searchIndex = 0; searchIndex < search.length; searchIndex++)
			{
				view.getLocations()[locationCounter].getWordData()[searchIndex][0] = search[searchIndex][0];
				view.getLocations()[locationCounter].getWordData()[searchIndex][1] = search[searchIndex][1];
				for(int resultIndex = 0; resultIndex < results.size(); resultIndex++)
				{
					if(results.get(resultIndex).getWord().equals(search[searchIndex][0]) && results.get(resultIndex).getState().equals(state.getStateAbbr())) 
					{
						view.getLocations()[locationCounter].getWordData()[searchIndex][2] = (double)results.get(resultIndex).getOccurances();
						break;
					}
				}
			}
			locationCounter++;
		}
		return view;
	}

}
