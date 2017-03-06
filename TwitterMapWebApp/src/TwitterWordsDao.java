import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class TwitterWordsDao {
	
	private SqlSession session;
	public TwitterWordsDao() throws IOException {
		session = ConnectionFactory.GetFactory();
	}

	public List<TwitterWordBO> GetWordsDuringTime(int timeId) {
		return session.selectList("TwitterWordMapper.GetWordsDuringTime", timeId);
	}
}
