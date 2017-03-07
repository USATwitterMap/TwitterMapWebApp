import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class TwitterDataDao {
	
	private SqlSession session;
	public TwitterDataDao() throws IOException {
		session = ConnectionFactory.GetFactory();
	}

	public List<TwitterWordBO> GetOccurancesByTime(TwitterWordBO word) {
		return session.selectList("TwitterWordMapper.GetWordsDuringTimeByState", word);
	}
	
	public TwitterTimeBO GetTimeBetween(Timestamp requestedTime) {
		return session.selectOne("TwitterTimeMapper.GetTimeBetween", requestedTime);
	}
}
