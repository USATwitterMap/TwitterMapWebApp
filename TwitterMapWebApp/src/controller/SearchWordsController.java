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
import view.TwitterWordData;
import view.TwitterWordQuery;

@Controller
public class SearchWordsController {

	@Autowired
	private TwitterDataDao wordsDao;
	
	@RequestMapping(value = "twitterAjax", method = RequestMethod.POST)
	@ResponseBody
	public TwitterWordData twitterAjax(@RequestBody TwitterWordQuery search )
	{
		//prepare return structure
		TwitterWordData wordQueryData = new TwitterWordData();
		wordQueryData.setMarkers(new ArrayList<MapMarker>());
		wordQueryData.setNextTime(1000);
		
		//get word results from database
		TwitterTime time = wordsDao.GetTimeBetween(new Timestamp(search.getDate()));
		if(time.getEndTime() != null) {
			TwitterWord wordQuery = new TwitterWord();
			wordQuery.setTime(time.getId());
			wordQuery.setWord(search.getKeyword());
			List<TwitterWord> wordQueryResults = wordsDao.GetOccurances(wordQuery);
			
			//add the ending time for this slice of data to result
			wordQueryData.setNextTime(time.getEndTime().getTime());
	
			//cycle and add to result
			for(TwitterWord result : wordQueryResults) {
				double timeLeft = time.getEndTime().getTime() - search.getDate();
				StateLocations stateLoc = StateLocations.FindLatLong(result.getState());
				MapMarker marker = new MapMarker();
				marker.setCounter(result.getOccurances());
				marker.setDelay((int)(timeLeft / (result.getOccurances() + 1)));
				marker.setLatitude((stateLoc.getLatitudeMax() + stateLoc.getLatitudeMin()) / 2);
				marker.setLongitude((stateLoc.getLongitudeMax() + stateLoc.getLongitudeMin()) / 2);
				wordQueryData.getMarkers().add(marker);
			}
		}
		return wordQueryData;
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
