package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.*;
import view.TestObj;
import view.TwitterMapVO;

@Controller
public class SearchWordsController {

	@Autowired
	private TwitterDataDao wordsDao;
	
	@RequestMapping(value = "twitterAjax", method = RequestMethod.POST)
	@ResponseBody
	public TestObj twitterAjax(@RequestBody TestObj search )
	{
		System.out.println("debug");
		return search;
	}
	
	@RequestMapping(value = "twitterWordSearch", method = RequestMethod.GET)
	   public ModelAndView twitterWordSearch(@ModelAttribute("SpringWeb")TwitterMapVO twitterMapVO) {
			TwitterMapVO vo = new TwitterMapVO();
			List<TwitterTime> times = wordsDao.GetTimeRange();
			vo.setEarliestDate(times.get(0).getStartTime());
			vo.setLatestDate(times.get(1).getEndTime());
			return new ModelAndView("twitterWordSearch", "command", vo);
	   }
	
	   @RequestMapping(value = "searchWords", method = RequestMethod.POST)
      public String searchWords(@ModelAttribute("SpringWeb")TwitterMapVO twitteMapVO, ModelMap model) 
	   {
		  //convert to BO, search using DAO, convert to VO and display

		  TwitterWord twitterMapBo = TwitterWordBoVOConverter.getBo(twitteMapVO);
		  List<TwitterWord> twitterMapResultsBo = null;
		  try 
		  {
			TwitterTime time = wordsDao.GetTimeBetween(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.0"));
			twitterMapBo.setTime(time.getId());
			twitterMapResultsBo = wordsDao.GetOccurances(twitterMapBo);
		  }
		  catch (Exception e) 
		  {
				
		  }
		  TwitterMapVO results = TwitterWordBoVOConverter.getVo(twitterMapResultsBo.get(0));
		  
	      model.addAttribute("results", results);
	      return "twitterWordResult";
	   }
	
}
