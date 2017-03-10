package dao;
import java.sql.Timestamp;
import java.util.List;

public interface TwitterDataDao {
	
	public List<TwitterWord> GetOccurancesByTime(TwitterWord word);
	public TwitterTime GetTimeBetween(Timestamp requestedTime);
}