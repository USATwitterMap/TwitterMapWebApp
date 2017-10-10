package service;

import search.PopularTermsSearch;
import view.PopularTermsView;

public interface PopularTermsService {
	public PopularTermsView CreatePopularTermsView(PopularTermsSearch query);
}
