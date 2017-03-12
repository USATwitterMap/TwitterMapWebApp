package dao;
import java.sql.Timestamp;
import java.util.List;

public interface TwitterDataDao {
	
	public List<TwitterWord> GetOccurances(TwitterWord word);
	public List<TwitterTime> GetTimeRange();
	public TwitterTime GetTimeBetween(Timestamp requestedTime);
}
