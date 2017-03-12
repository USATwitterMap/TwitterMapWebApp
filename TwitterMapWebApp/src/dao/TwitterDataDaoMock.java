package dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TwitterDataDaoMock implements TwitterDataDao {

	@Override
	public List<TwitterWord> GetOccurances(TwitterWord word) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TwitterTime> GetTimeRange() {
		List<TwitterTime> timeRange = new ArrayList<TwitterTime>();
		
		TwitterTime firstTime = new TwitterTime();
		firstTime.setStartTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.0")); 
		firstTime.setEndTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.1")); 
		TwitterTime secondTime = new TwitterTime();
		secondTime.setStartTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:30.0")); 
		secondTime.setEndTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:30.0")); 
		
		timeRange.add(firstTime);
		timeRange.add(secondTime);
		
		return timeRange;
	}

	@Override
	public TwitterTime GetTimeBetween(Timestamp requestedTime) {
		TwitterTime firstTime = new TwitterTime();
		firstTime.setId(5);
		firstTime.setStartTime(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.0")); 
		firstTime.setEndTime(java.sql.Timestamp.valueOf("2017-03-5 19:09:10.0")); 
		return firstTime;
	}

}
