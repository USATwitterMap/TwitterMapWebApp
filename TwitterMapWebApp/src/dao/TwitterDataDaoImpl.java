package dao;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class TwitterDataDaoImpl implements TwitterDataDao {
	
	public List<TwitterWord> GetOccurancesByTime(TwitterWord word) {
		SqlSession session = null;
		List<TwitterWord> results = null;
		try {
			session = ConnectionFactory.GetFactory().openSession();
			results = session.selectList("TwitterWordMapper.GetWordsDuringTimeByState", word);
		} catch (IOException e) {
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
	
	public TwitterTime GetTimeBetween(Timestamp requestedTime) {
		SqlSession session = null;
		TwitterTime results = null;
		try {
			session = ConnectionFactory.GetFactory().openSession();
			results = session.selectOne("TwitterTimeMapper.GetTimeBetween", requestedTime);
		} catch (IOException e) {
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
