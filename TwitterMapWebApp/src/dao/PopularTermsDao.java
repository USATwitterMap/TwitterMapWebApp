package dao;

import java.util.ArrayList;
import java.util.List;

import dao.queries.StartStopDateQuery;

public interface PopularTermsDao {
	public List<String> GetPopularTerms(StartStopDateQuery query);
}
