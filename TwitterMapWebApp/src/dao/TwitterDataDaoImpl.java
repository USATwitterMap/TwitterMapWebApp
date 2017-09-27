package dao;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import dao.queries.TwitterWordQuery;
import dao.queries.TwitterWordTimeQuery;
import dao.results.StatePopulation;
import dao.results.SystemDiag;
import dao.results.TimeSegment;
import dao.results.TwitterWordData;
import dao.results.TwitterWordTimeData;

public class TwitterDataDaoImpl implements TwitterDataDao {
	
	public List<TwitterWordData> GetOccurances(TwitterWordQuery word) {
		SqlSession session = null;
		List results = null;
		try {
			session = ConnectionFactory.GetFactory().openSession();
			results = session.selectList("TwitterWordMapper.GetWordsDuringTimeByState", word);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session != null) {
				session.close();
			}
		}
		return results;
	}
	
	public List<TwitterWordTimeData> GetOccurancesByTime(TwitterWordTimeQuery word) {
		SqlSession session = null;
		List<TwitterWordTimeData> results = null;
		List<StatePopulation> stateResults = null;
		List<TimeSegment> timeSegments = null;
		try {
			session = ConnectionFactory.GetFactory().openSession();
			timeSegments = session.selectList("TwitterTimeMapper.GetTimeSegments", word);
			stateResults = session.selectList("StatePopulationMapper.GetPopulation");
			results = session.selectList("TwitterWordMapper.GetWordsByTimeByState", word);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session != null) {
				session.close();
			}
		}
		if(results != null && timeSegments != null) 
		{
			Map<String, Integer> wordMap = new HashMap<String, Integer>();
			for(TwitterWordTimeData wordData : results) 
			{
				wordMap.put(wordData.getWord() + wordData.getState() + wordData.getTimeSegment(), 1);
			}
			
			//find all missing timesegments
			List<TwitterWordTimeData> missingSegments = new ArrayList<TwitterWordTimeData>();
			for(String wordExpected : word.getWords()) 
			{
				for(StatePopulation stateExpected : stateResults) 
				{
					for(TimeSegment timeSegment : timeSegments) 
					{
						if(!wordMap.containsKey(wordExpected + stateExpected.getState() + timeSegment.getTimeSegment())) {
							TwitterWordTimeData missingSegment = new TwitterWordTimeData();
							missingSegment.setWord(wordExpected);
							missingSegment.setOccurances(0);
							missingSegment.setState(stateExpected.getState());
							missingSegment.setTimeSegment(timeSegment.getTimeSegment());
							missingSegments.add(missingSegment);
						}
					}
				}
			}
			
			//add missing segments to result
			for(TwitterWordTimeData missingWordData : missingSegments) 
			{
				results.add(missingWordData);
			}
			
			//set timestamps for each word
			for(TwitterWordTimeData result : results) 
			{
				for(TimeSegment timeSegment : timeSegments) 
				{
					if(result.getTimeSegment() == timeSegment.getTimeSegment()) {
						result.setTime(timeSegment.getTime());
						break;
					}
				}
			}
		}
		return results;
	}
	
	public List<SystemDiag> GetSystemHealth() 
	{
		SqlSession session = null;
		List results = null;
		try {
			session = ConnectionFactory.GetFactory().openSession();
			results = session.selectList("TwitterTimeMapper.GetSystemDiagnostics");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session != null) {
				session.close();
			}
		}
		return results;
	}
}
