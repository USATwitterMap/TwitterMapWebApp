package dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import controller.StateLocations;

public class TwitterDataDaoMock implements TwitterDataDao {

	@Override
	public List<TwitterWordData> GetOccurances(TwitterWordQuery word) {
		List<TwitterWordData> wordResults = new ArrayList<TwitterWordData>();
		int id = 0;

		return wordResults;
	}

	@Override
	public List<SystemDiag> GetSystemHealth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TwitterTime> GetTimeRange() {
		// TODO Auto-generated method stub
		return null;
	}

}
