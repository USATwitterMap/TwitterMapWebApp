package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import search.TwitterWordSearch;
import service.GeoChartService;
import service.LineGraphService;
import service.PieGraphService;
import view.TwitterSearchView;
import view.SearchScreenView;

@Controller
public class SearchWordsController {

	@Autowired
	private GeoChartService geoChartService;
	
	@Autowired
	private LineGraphService lineGraphService;
	
	@Autowired
	private PieGraphService pieGraphService;
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public TwitterSearchView search(@RequestBody TwitterWordSearch query)
	{	
		TwitterSearchView view = new TwitterSearchView();
		view.setLineGraphView(lineGraphService.CreateLineGraphView(query));
		view.setMultiWordView(pieGraphService.CreatePieGraphView(query, view.getLineGraphView()));
		view.setSingleWordView(geoChartService.CreateGeoChartView(view.getMultiWordView()));
		return view;
		
	}
	
	@RequestMapping(value = "twitterTimeWindow", method = RequestMethod.GET)
	public ModelAndView twitterTimeWindow() {
		SearchScreenView vo =new SearchScreenView();
		return new ModelAndView("twitterWordSearch", "searchInitData", vo);
   }
}
