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
import view.TwitterTimeWindow;

@Controller
public class SearchWordsController {

	@Autowired
	private TwitterDataDao wordsDao;
	
	@RequestMapping(value = "twitterAjax", method = RequestMethod.POST)
	@ResponseBody
	public TwitterWordData twitterAjax(@RequestBody String[][] search )
	{
		//prepare return structure
		TwitterWordData wordQueryData = new TwitterWordData();

		TwitterWordQuery wordQuery = new TwitterWordQuery();
		wordQuery.setStartDate(search[0][2].substring(0, search[0][2].indexOf(" -")));
		wordQuery.setStopDate(search[0][2].substring(search[0][2].indexOf("- ") + 2));		
		String[] words = new String[search.length];
		for(int index = 0; index < search.length; index++)
		{
			words[index] = (search[index][0]);
		}
		wordQuery.setWords(words);
		wordsDao.GetOccurances(wordQuery);
		
		/*
		//get word results from database
		System.out.print("\nTime searched: " + new Timestamp(search.getDate()));
		TwitterTime time = wordsDao.GetTimeBetween(new Timestamp(search.getDate()));
		System.out.print("\nTime retrieved start: " + time.getStartTime());
		System.out.print("\nTime retrieved end: " + time.getEndTime());
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
				System.out.print("\nDelay(s): " + (double)marker.getDelay() / (double)1000);
			}
		}
		*/
		return wordQueryData;
	}
	
	@RequestMapping(value = "twitterTimeWindow", method = RequestMethod.GET)
	   public ModelAndView twitterTimeWindow() {
			TwitterTimeWindow vo =new TwitterTimeWindow();
			return new ModelAndView("twitterWordSearch", "command", vo);
	   }
}
