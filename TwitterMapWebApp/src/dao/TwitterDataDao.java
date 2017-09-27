package dao;
import java.sql.Timestamp;
import java.util.List;

import dao.queries.TwitterWordQuery;
import dao.queries.TwitterWordTimeQuery;
import dao.results.SystemDiag;
import dao.results.TwitterWordData;
import dao.results.TwitterWordTimeData;

public interface TwitterDataDao {
	
	public List<TwitterWordData> GetOccurances(TwitterWordQuery word);
	public List<TwitterWordTimeData> GetOccurancesByTime(TwitterWordTimeQuery word);
	public List<SystemDiag> GetSystemHealth();
}
