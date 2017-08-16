package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.*;
import view.ChartView;
import view.TwitterTimeWindow;

@Controller
public class SearchWordsController {

	@Autowired
	private TwitterDataDao wordsDao;
	
	@RequestMapping(value = "twitterAjax", method = RequestMethod.POST)
	@ResponseBody
	public ChartView twitterAjax(@RequestBody String[][] search )
	{
		//prepare return structure
		TwitterWordQuery wordQuery = new TwitterWordQuery();
		wordQuery.setStartDate(search[0][2].substring(0, search[0][2].indexOf(" -")));
		wordQuery.setStopDate(search[0][2].substring(search[0][2].indexOf("- ") + 2));
		wordQuery.setWords(new ArrayList<String>());
		for(int index = 0; index < search.length; index++)
		{
			wordQuery.getWords().add(search[index][0]);
		}
		List<TwitterWordData> results = wordsDao.GetOccurances(wordQuery);
		ChartView view = new ChartView();
		Object[][] areaMapData = new Object[results.size()+1][2];
		areaMapData[0][0] = "State";
		areaMapData[0][1] = search[0][0];
		view.setColor(search[0][1]);
		for(int index = 0; index < results.size(); index++) 
		{
			areaMapData[index + 1][0] = "US-" + results.get(index).getState();
			areaMapData[index + 1][1] = results.get(index).getOccurances();
		}
		
		view.setAreaChart(areaMapData);
		return view;
	}
	
	@RequestMapping(value = "twitterTimeWindow", method = RequestMethod.GET)
	   public ModelAndView twitterTimeWindow() {
			TwitterTimeWindow vo =new TwitterTimeWindow();
			return new ModelAndView("twitterWordSearch", "command", vo);
	   }
}
