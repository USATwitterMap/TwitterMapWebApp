package dao;
import java.sql.Timestamp;
import java.util.List;

import dao.queries.TwitterWordQuery;
import dao.results.SystemDiag;
import dao.results.TwitterWordData;

public interface TwitterDataDao {
	
	public List<TwitterWordData> GetOccurances(TwitterWordQuery word);
	
	public List<SystemDiag> GetSystemHealth();
}
