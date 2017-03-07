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
		SqlSession session = ConnectionFactory.GetFactory();
		List<TwitterWordBO> results = null;
		try {
			results = session.selectList("TwitterWordMapper.GetWordsDuringTimeByState", word);
		}
		finally {
			session.close();
		}
			
		return results;
	}
	
	public TwitterTimeBO GetTimeBetween(Timestamp requestedTime) {
		SqlSession session = ConnectionFactory.GetFactory();
		TwitterTimeBO results = null;
		try {
			results = session.selectOne("TwitterTimeMapper.GetTimeBetween", requestedTime);
		}
		finally {
			session.close();
		}
			
		return results;
	}
}
