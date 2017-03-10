package controller;
import java.io.IOException;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.*;
import view.TwitterMapVO;

@Controller
public class SearchWordsController {

	@Autowired
	private TwitterDataDao wordsDao;
		/*	
	@RequestMapping(value="welcome.do", method = RequestMethod.GET)
	public ModelAndView WordsConnectAttempt(TwitterMapVO twitterMapVO)
	{
		System.out.println("hello");
		TwitterWord word = new TwitterWord();
		word.setTime(260);
		word.setWord("you");
		TwitterTime time = null;
		List<TwitterWord> words = null;
		try {
		time = wordsDao.GetTimeBetween(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.0"));
		words = wordsDao.GetOccurancesByTime(word);
		}
		catch (Exception e) {
			
		}
		String debug = "";
		return new ModelAndView("welcome", "message", "Brett");
	}
	*/
	@RequestMapping(value = "twitterWordSearch", method = RequestMethod.GET)
	   public ModelAndView twitterWordSearch() {
	      return new ModelAndView("twitterWordSearch", "command", new TwitterMapVO());
	   }
	
	   @RequestMapping(value = "searchWords", method = RequestMethod.POST)
      public String searchWords(@ModelAttribute("SpringWeb")TwitterMapVO twitteMapVO, ModelMap model) 
	   {
		  //convert to BO, search using DAO, convert to VO and display
		   
		  
	      model.addAttribute("keyword", twitteMapVO.getKeyword());
	      model.addAttribute("date", twitteMapVO.getDate());      
	      return "twitterWordResult";
	   }
	
}
