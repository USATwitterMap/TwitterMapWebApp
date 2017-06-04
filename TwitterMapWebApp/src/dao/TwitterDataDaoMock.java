package dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import controller.StateLocations;

public class TwitterDataDaoMock implements TwitterDataDao {

	@Override
	public List<TwitterWord> GetOccurances(TwitterWord word) {
		List<TwitterWord> wordResults = new ArrayList<TwitterWord>();
		int id = 0;
		//cycle through all the states
    	for(StateLocations aState : StateLocations.values()) 
    	{
			wordResults.add(new TwitterWord());
			wordResults.get(wordResults.size() - 1).setId(id++);
			wordResults.get(wordResults.size() - 1).setWord(word.getWord());
			wordResults.get(wordResults.size() - 1).setTime(word.getTime());
			wordResults.get(wordResults.size() - 1).setState(aState.getStateAbbr());
			if(word.getTime() == 1) 
			{
				wordResults.get(wordResults.size() - 1).setOccurances((int)(Math.random() * (double)30));
			}
			else if(word.getTime() == 2) 
			{
				wordResults.get(wordResults.size() - 1).setOccurances((int)(Math.random() * (double)30));
			}
			else if(word.getTime() == 3) 
			{
				wordResults.get(wordResults.size() - 1).setOccurances((int)(Math.random() * (double)30));
			}
		}
		
		return wordResults;
	}

	@Override
	public List<TwitterTime> GetTimeRange() {
		List<TwitterTime> timeRange = new ArrayList<TwitterTime>();
		
		TwitterTime firstTime = new TwitterTime();
		firstTime.setStartTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.1")); 
		firstTime.setEndTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:30.1")); 
		firstTime.setId(1);
		TwitterTime thirdTime = new TwitterTime();
		thirdTime.setStartTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:50.1")); 
		thirdTime.setEndTime(java.sql.Timestamp.valueOf("2017-03-4 19:10:10.1")); 
		thirdTime.setId(2);
		
		timeRange.add(firstTime);
		timeRange.add(thirdTime);
		return timeRange;
	}

	@Override
	public TwitterTime GetTimeBetween(Timestamp requestedTime) {
		
		TwitterTime time = new TwitterTime();
		if(requestedTime.after(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.0")) && requestedTime.before(java.sql.Timestamp.valueOf("2017-03-4 19:09:30.1")))
		{
			time.setId(1);
			time.setStartTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.0"));
			time.setEndTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:30.1"));
		}
		else if(requestedTime.after(java.sql.Timestamp.valueOf("2017-03-4 19:09:30.0")) && requestedTime.before(java.sql.Timestamp.valueOf("2017-03-4 19:09:50.1")))
		{
			time.setId(2);
			time.setStartTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:30.0"));
			time.setEndTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:50.1"));
		}
		else if(requestedTime.after(java.sql.Timestamp.valueOf("2017-03-4 19:09:50.0")) && requestedTime.before(java.sql.Timestamp.valueOf("2017-03-4 19:10:10.1")))
		{
			time.setId(3);
			time.setStartTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:50.0"));
			time.setEndTime(java.sql.Timestamp.valueOf("2017-03-4 19:10:10.1"));
		}
		return time;
	}
	
	 private List<String> GetStates()
	 {
		 List<String> states = new ArrayList<String>();
		 
		states.add("AL");
		states.add("AK");
		states.add("AZ");
		states.add("AR");
		states.add("CA");
		states.add("CO");
		states.add("CT");
		states.add("DE");
		states.add("DC");
		states.add("FM");
		states.add("FL");
		states.add("GA");
		states.add("GU");
		states.add("HI");
		states.add("ID");
		states.add("IL");
		states.add("IN");
		states.add("IA");
		states.add("KS");
		states.add("KY");
		states.add("LA");
		states.add("ME");
		states.add("MH");
		states.add("MD");
		states.add("MA");
		states.add("MI");
		states.add("MN");
		states.add("MS");
		states.add("MO");
		states.add("MT");
		states.add("NE");
		states.add("NV");
		states.add("NH");
		states.add("NJ");
		states.add("NM");
		states.add("NY");
		states.add("NC");
		states.add("ND");
		states.add("OH");
		states.add("OK");
		states.add("OR");
		states.add("PW");
		states.add("PA");
		states.add("PR");
		states.add("RI");
		states.add("SC");
		states.add("SD");
		states.add("TN");
		states.add("TX");
		states.add("UT");
		states.add("VT");
		states.add("VI");
		states.add("VA");
		states.add("WA");
		states.add("WV");
		states.add("WI");
		states.add("WY");
		return states;
	 }

}
