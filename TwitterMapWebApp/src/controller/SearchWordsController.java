package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.*;
import dao.queries.StartStopDateQuery;
import dao.queries.TwitterWordQuery;
import dao.queries.TwitterWordTimeQuery;
import dao.results.StatePopulation;
import dao.results.SystemDiag;
import dao.results.TwitterWordData;
import dao.results.TwitterWordTimeData;
import view.SingleWordView;
import view.SystemHealthView;
import view.TwitterSearchQuery;
import view.TwitterSearchView;
import view.LineGraphView;
import view.LocationView;
import view.MultiWordView;
import view.PopularTermsSearch;
import view.PopularTermsView;
import view.SearchScreenView;

@Controller
public class SearchWordsController {

	@Autowired
	private TwitterDataDao wordsDao;
	
	@Autowired
	private StatePopulationDao popDao;
	
	@Autowired
	private PopularTermsDao popularTermsDao;
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public TwitterSearchView search(@RequestBody TwitterSearchQuery query)
	{	
		//prepare return structure
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
		List<TwitterWordData> results = wordsDao.GetOccurances(wordQuery);
		List<TwitterWordTimeData> timeResults = wordsDao.GetOccurancesByTime(wordQuery);
		
		TwitterSearchView view = new TwitterSearchView();
		view.setSingleWordView(new SingleWordView[wordQuery.getWords().size()]);
		for(int word = 0; word < wordQuery.getWords().size(); word++) 
		{
			view.getSingleWordView()[word] = searchSingle(results, populationData, query, wordQuery.getWords().get(word));
		}
		view.setMultiWordView(searchMultiple(results, search));
		view.setLineGraphView(searchWordByTime(timeResults, populationData, wordQuery, query.isPopulationControl()));
		return view;
	}
	
	private SingleWordView searchSingle(List<TwitterWordData> results, List<StatePopulation> populationData, TwitterSearchQuery query, String targetWord)
	{	
		//prepare return structure
		String[][] search = query.getSearchData();
		SingleWordView view = new SingleWordView();
		List<Object[]> areaMapData = new ArrayList<Object[]>();
		areaMapData.add(new Object[2]);
		areaMapData.get(0)[0] = "State";
		for(int index = 0; index < search.length; index++)
		{
			if(search[index][0].equals(targetWord)) 
			{
				areaMapData.get(0)[1] = search[index][0];
				view.setColor(search[index][1]);
				break;
			}
		}
		for(int index = 0; index < results.size(); index++) 
		{
			if(results.get(index).getWord().equals(targetWord))
			{
				areaMapData.add(new Object[2]);
				areaMapData.get(areaMapData.size() - 1)[0] = "US-" + results.get(index).getState();
				double occurances = results.get(index).getOccurances();
				if(query.isPopulationControl()) 
				{
					for(StatePopulation population : populationData) 
					{
						if(population.getState().equals(results.get(index).getState())) 
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
				//round to two decimal places and store
				areaMapData.get(areaMapData.size() - 1)[1] = Math.round(occurances * 100.0) / 100.0;
			}
		}
		Object[][] areaChartArray = new Object[areaMapData.size()][2];
		for(int index = 0; index < areaMapData.size(); index++) 
		{
			areaChartArray[index] = areaMapData.get(index);
		}
		view.setAreaChart(areaChartArray);
		return view;
	}

	private MultiWordView searchMultiple(List<TwitterWordData> results, String[][] search)
	{
		//prepare return structure
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
						view.getLocations()[locationCounter].getWordData()[searchIndex][2] = results.get(resultIndex).getOccurances();
						break;
					}
				}
			}
			locationCounter++;
		}
		return view;
	}
	
	
	@RequestMapping(value = "getSystemHealth", method = RequestMethod.POST)
	@ResponseBody
	public SystemHealthView getSystemHealth()
	{
		SystemHealthView systemHealth =new SystemHealthView();
		List<SystemDiag> systemHealthResults = wordsDao.GetSystemHealth();
		Calendar latestDate  = Calendar.getInstance();
		Calendar currentDate  = Calendar.getInstance();
		
		List<long[]> systemDiagnosticData = new ArrayList<long[]>();
		
		for(int diagnosticIndex = 0; diagnosticIndex < systemHealthResults.size(); diagnosticIndex++)
		{
			if(systemDiagnosticData.size() > 0 ) 
			{
				latestDate.setTime(new Date(systemDiagnosticData.get(systemDiagnosticData.size() - 1)[0]));
				currentDate.setTime(systemHealthResults.get(diagnosticIndex).getForDate());
				while((currentDate.getTimeInMillis() - latestDate.getTimeInMillis()) > (TimeUnit.DAYS.toMillis(1) + 1)) 
				{
					latestDate.add(Calendar.DATE, 1);
					systemDiagnosticData.add(new long[] {latestDate.getTime().getTime(), 23});
				}
			}
			systemDiagnosticData.add(new long[] {systemHealthResults.get(diagnosticIndex).getForDate().getTime(), systemHealthResults.get(diagnosticIndex).getNumPosts()});
		}
		systemHealth.setSystemHealth(new long[systemDiagnosticData.size()][2]);
		for(int diagnosticIndex = 0; diagnosticIndex < systemDiagnosticData.size(); diagnosticIndex++)
		{
			systemHealth.getSystemHealth()[diagnosticIndex] = systemDiagnosticData.get(diagnosticIndex);
		}
		return systemHealth;
	}

	private LineGraphView searchWordByTime(List<TwitterWordTimeData> results, List<StatePopulation> populationData, TwitterWordTimeQuery wordQuery, boolean popControl)
	{
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
		for(TwitterWordTimeData result : results) 
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
		
		if(popControl) 
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
	
	@RequestMapping(value = "searchPopularTerms", method = RequestMethod.POST)
	@ResponseBody
	public PopularTermsView searchPopularTerms(@RequestBody PopularTermsSearch query)
	{	
		//prepare return structure
		StartStopDateQuery search = new StartStopDateQuery();
		search.setStartDate(query.getSearchTime().substring(0, query.getSearchTime().indexOf(" -")));
		search.setStopDate(query.getSearchTime().substring(query.getSearchTime().indexOf("- ") + 2));
		
		List<String> results = popularTermsDao.GetPopularTerms(search);
		PopularTermsView view = new PopularTermsView();
		view.setResults(results);
		return view;
	}
	
	@RequestMapping(value = "twitterTimeWindow", method = RequestMethod.GET)
	   public ModelAndView twitterTimeWindow() {
			SearchScreenView vo =new SearchScreenView();
			return new ModelAndView("twitterWordSearch", "searchInitData", vo);
	   }
}
