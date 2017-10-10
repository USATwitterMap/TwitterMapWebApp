package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import search.PopularTermsSearch;
import service.PopularTermsService;
import service.SystemHealthService;
import view.PopularTermsView;
import view.SystemHealthView;

@Controller
public class SearchDiagnosticsController {
	
	@Autowired
	private SystemHealthService systemHealthService;
	
	@Autowired
	private PopularTermsService popularTermsService;
	
	@RequestMapping(value = "getSystemHealth", method = RequestMethod.POST)
	@ResponseBody
	public SystemHealthView getSystemHealth()
	{
		SystemHealthView systemHealth = systemHealthService.CreateSystemHealthView();
		return systemHealth;
	}
	
	@RequestMapping(value = "searchPopularTerms", method = RequestMethod.POST)
	@ResponseBody
	public PopularTermsView searchPopularTerms(@RequestBody PopularTermsSearch query)
	{	
		PopularTermsView view = popularTermsService.CreatePopularTermsView(query);
		return view;
	}
}
