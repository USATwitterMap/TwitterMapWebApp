package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.StatePopulationDao;
import dao.TwitterDataDao;
import dao.queries.TwitterWordTimeQuery;
import dao.results.StatePopulation;
import dao.results.TwitterWordTimeData;
import search.TwitterWordSearch;
import view.LineGraphView;

@Service
public class LineGraphServiceImpl implements LineGraphService {
	
	@Autowired
	private TwitterDataDao wordsDao;
	
	@Autowired
	private StatePopulationDao popDao;
	
	@Override
	public LineGraphView CreateLineGraphView(TwitterWordSearch query) 
	{
		TwitterWordTimeQuery wordQuery = new TwitterWordTimeQuery();
		String[][] search = query.getSearchData();
		wordQuery.setStartDate(search[0][2].substring(0, search[0][2].indexOf(" -")));
		wordQuery.setStopDate(search[0][2].substring(search[0][2].indexOf("- ") + 2));
		wordQuery.setWords(new ArrayList<String>());
		wordQuery.setSegments(50);
		for(int index = 0; index < search.length; index++)
		{
			wordQuery.getWords().add(search[index][0]);
		}
		
		List<StatePopulation> populationData = popDao.GetPopulation();
		List<TwitterWordTimeData> timeResults = wordsDao.GetOccurancesByTime(wordQuery);
		
		LineGraphView view = new LineGraphView();
		view.setLineData(new Object[wordQuery.getSegments() + 1][wordQuery.getWords().size() * (populationData.size() + 1) + 1]);
		view.getLineData()[0][0] = "Time";
		int totalPopulation = 0;
		for(int stateIndex = 0; stateIndex <= populationData.size(); stateIndex ++) 
		{
			for(int wordIndex = 1; wordIndex <= wordQuery.getWords().size(); wordIndex ++) 
			{
				if(stateIndex < populationData.size()) 
				{
					view.getLineData()[0][stateIndex * wordQuery.getWords().size() + wordIndex] = populationData.get(stateIndex).getState() + ": " + wordQuery.getWords().get(wordIndex - 1);
				}
				else {
					//add overall per word per state
					view.getLineData()[0][stateIndex * wordQuery.getWords().size() + wordIndex] = "OVERALL: " + wordQuery.getWords().get(wordIndex - 1);
				}
			}
			if(stateIndex < populationData.size()) 
			{
				totalPopulation += populationData.get(stateIndex).getPopulation();
			}
		}
		for(TwitterWordTimeData result : timeResults) 
		{
			for(int columnIndex = 1; columnIndex < view.getLineData()[0].length - 1; columnIndex++)
			{
				if(view.getLineData()[0][columnIndex].equals(result.getState() + ": " + result.getWord())) 
				{
					view.getLineData()[result.getTimeSegment() + 1][columnIndex] = (double)result.getOccurances();
					view.getLineData()[result.getTimeSegment() + 1][0] = result.getTime().toLocaleString();
					for(int overallIndex = view.getLineData()[0].length - 1; overallIndex >= 0; overallIndex--)
					{
						if(view.getLineData()[0][overallIndex].equals("OVERALL: " + result.getWord())) 
						{
							if(view.getLineData()[result.getTimeSegment() + 1][overallIndex] == null) 
							{
								view.getLineData()[result.getTimeSegment() + 1][overallIndex] = (double)result.getOccurances();
							}
							else 
							{
								view.getLineData()[result.getTimeSegment() + 1][overallIndex] = (double)view.getLineData()[result.getTimeSegment() + 1][overallIndex] + result.getOccurances();
							}
							break;
						}
					}
				}
			}
		}
		
		if(query.isPopulationControl()) 
		{
			for(int index = 1; index < view.getLineData().length; index++) 
			{
				for(int stateIndex = 1; stateIndex < view.getLineData()[index].length; stateIndex ++) 
				{
					double factor = 1.0;
					double occurances = (double)view.getLineData()[index][stateIndex];
					if(!((String)view.getLineData()[0][stateIndex]).startsWith("OVERALL")) 
					{
						for(StatePopulation population : populationData)
						{
							if(((String)view.getLineData()[0][stateIndex]).startsWith(population.getState())) 
							{
								factor = population.getPopulation() / 1000000;
								break;
							}
						}
					}
					else 
					{
						factor = totalPopulation / 1000000;
					}
					if(factor > 0) 
					{						
						occurances = occurances / factor;
					}
					else 
					{
						occurances = 0;
					}
					view.getLineData()[index][stateIndex] = occurances;
				}
			}
		}
		
		return view;
	}
}
