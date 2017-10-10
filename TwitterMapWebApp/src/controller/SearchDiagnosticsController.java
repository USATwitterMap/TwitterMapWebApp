package controller;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import search.PopularTermsSearch;
import service.PopularTermsService;
import service.SystemHealthService;
import view.PopularTermsView;
import view.SearchScreenView;
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
	
	@RequestMapping(value = "twitterTimeWindow", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView twitterTimeWindow() {
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		try {
			mapper.writeValue(byteArray, getSystemHealth());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonOutput = new String(byteArray.toByteArray());
		return new ModelAndView("twitterWordSearch", "systemHealth", jsonOutput);
   }
}
