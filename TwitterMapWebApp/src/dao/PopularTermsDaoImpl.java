package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dao.queries.StartStopDateQuery;

public class PopularTermsDaoImpl implements PopularTermsDao {

	@Override
	public List<String> GetPopularTerms(StartStopDateQuery search) {
		SqlSession session = null;
		List results = null;
		try {
			session = ConnectionFactory.GetFactory().openSession();
			results = session.selectList("TwitterPopularTermsMapper.GetPopularTerms", search);
		} catch (Exception e) {
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
