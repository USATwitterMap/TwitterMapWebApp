package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.PopularTermsDao;
import dao.queries.StartStopDateQuery;
import search.PopularTermsSearch;
import view.PopularTermsView;

@Service
public class PopularTermsServiceImpl implements PopularTermsService {

	@Autowired
	private PopularTermsDao popularTermsDao;
	
	@Override
	public PopularTermsView CreatePopularTermsView(PopularTermsSearch query) {
		//prepare return structure
		StartStopDateQuery search = new StartStopDateQuery();
		search.setStartDate(query.getSearchTime().substring(0, query.getSearchTime().indexOf(" -")));
		search.setStopDate(query.getSearchTime().substring(query.getSearchTime().indexOf("- ") + 2));
			
		List<String> results = popularTermsDao.GetPopularTerms(search);
		PopularTermsView view = new PopularTermsView();
		view.setResults(results);
		return view;
	}

}
