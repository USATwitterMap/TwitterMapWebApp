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
	
	@RequestMapping(value = "searchSingle", method = RequestMethod.POST)
	@ResponseBody
	public SingleWordView searchSingle(@RequestBody TwitterSearchQuery query)
	{	
		//prepare return structure
		TwitterWordQuery wordQuery = new TwitterWordQuery();
		String[][] search = query.getSearchData();
		
		List<StatePopulation> populationData = null;
		if(query.isPopulationControl()) 
		{
			populationData = popDao.GetPopulation();
		}
		
		wordQuery.setStartDate(search[0][2].substring(0, search[0][2].indexOf(" -")));
		wordQuery.setStopDate(search[0][2].substring(search[0][2].indexOf("- ") + 2));
		wordQuery.setWords(new ArrayList<String>());
		for(int index = 0; index < search.length; index++)
		{
			wordQuery.getWords().add(search[index][0]);
		}
		List<TwitterWordData> results = wordsDao.GetOccurances(wordQuery);
		SingleWordView view = new SingleWordView();
		Object[][] areaMapData = new Object[results.size()+1][2];
		areaMapData[0][0] = "State";
		areaMapData[0][1] = search[0][0];
		view.setColor(search[0][1]);
		for(int index = 0; index < results.size(); index++) 
		{
			areaMapData[index + 1][0] = "US-" + results.get(index).getState();
			double occurances = results.get(index).getOccurances();
			if(populationData != null) 
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
			areaMapData[index + 1][1] = Math.round(occurances * 100.0) / 100.0;
		}
		
		view.setAreaChart(areaMapData);
		return view;
	}
	
	@RequestMapping(value = "searchMultiple", method = RequestMethod.POST)
	@ResponseBody
	public MultiWordView searchMultiple(@RequestBody TwitterSearchQuery query)
	{
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
	
	@RequestMapping(value = "searchWordByTime", method = RequestMethod.POST)
	@ResponseBody
	public LineGraphView searchWordByTime(@RequestBody TwitterSearchQuery query)
	{
		int segments = 50;
		List<StatePopulation> populationData = popDao.GetPopulation();
		
		//prepare return structure
		TwitterWordTimeQuery wordQuery = new TwitterWordTimeQuery();
		String[][] search = query.getSearchData();
		wordQuery.setSegments(segments);
		wordQuery.setStartDate(search[0][2].substring(0, search[0][2].indexOf(" -")));
		wordQuery.setStopDate(search[0][2].substring(search[0][2].indexOf("- ") + 2));
		wordQuery.setWords(new ArrayList<String>());
		for(int index = 0; index < search.length; index++)
		{
			wordQuery.getWords().add(search[index][0]);
		}
		List<TwitterWordTimeData> results = wordsDao.GetOccurancesByTime(wordQuery);
		
		LineGraphView view = new LineGraphView();
		
		view.setLineData(new String[segments + 1][search.length * populationData.size() + 1]);
		view.getLineData()[0][0] = "Time";
		for(int stateIndex = 0; stateIndex < populationData.size(); stateIndex ++) 
		{
			for(int wordIndex = 1; wordIndex <= wordQuery.getWords().size(); wordIndex ++) 
			{
				view.getLineData()[0][stateIndex * wordQuery.getWords().size() + wordIndex] = populationData.get(stateIndex).getState() + ": " + wordQuery.getWords().get(wordIndex - 1);
			}
		}
		for(TwitterWordTimeData result : results) 
		{
			for(int columnIndex = 1; columnIndex < view.getLineData()[0].length; columnIndex++)
			{
				if(view.getLineData()[0][columnIndex].equals(result.getState() + ": " + result.getWord())) 
				{
					view.getLineData()[result.getTimeSegment() + 1][columnIndex] = Integer.toString(result.getOccurances());
					view.getLineData()[result.getTimeSegment() + 1][0] = result.getTime().toLocaleString();
					break;
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
