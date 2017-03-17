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
import view.MapMarker;
import view.TwitterTimeWindow;
import view.TwitterWordQuery;

@Controller
public class SearchWordsController {

	@Autowired
	private TwitterDataDao wordsDao;
	
	@RequestMapping(value = "twitterAjax", method = RequestMethod.POST)
	@ResponseBody
	public List<MapMarker> twitterAjax(@RequestBody TwitterWordQuery search )
	{
		TwitterTime time = wordsDao.GetTimeBetween(new Timestamp(search.getDate()));
		TwitterWord wordQuery = new TwitterWord();
		wordQuery.setTime(time.getId());
		wordQuery.setWord(search.getKeyword());
		List<TwitterWord> wordQueryResults = wordsDao.GetOccurances(wordQuery);
		List<MapMarker> markers = new ArrayList<MapMarker>();
		for(TwitterWord result : wordQueryResults) {
			double timeLeft = time.getEndTime().getTime() - search.getDate();
			double windowSize = time.getEndTime().getTime()  - time.getStartTime().getTime();
			double occurancesAdjusted = (((double)1 - (timeLeft/ windowSize)) * (double)result.getOccurances()) + 1;
			StateLocations stateLoc = StateLocations.FindLatLong(result.getState());
			MapMarker marker = new MapMarker();
			marker.setDelay((int)((timeLeft / occurancesAdjusted) - ((timeLeft / occurancesAdjusted) / (double)2)));
			marker.setLatitude(stateLoc.getLatitudeMin());
			marker.setLongitude(stateLoc.getLongitudeMin());
			markers.add(marker);
		}
		return markers;
	}
	
	@RequestMapping(value = "twitterTimeWindow", method = RequestMethod.GET)
	   public ModelAndView twitterTimeWindow() {
			TwitterTimeWindow vo =new TwitterTimeWindow();
			List<TwitterTime> times = wordsDao.GetTimeRange();
			vo.setEarliestDate(times.get(0).getStartTime());
			vo.setLatestDate(times.get(1).getEndTime());
			return new ModelAndView("twitterWordSearch", "command", vo);
	   }
}
