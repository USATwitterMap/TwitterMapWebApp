package dao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import resources.Constants;

public class StatePopulationDaoImpl implements StatePopulationDao{

	@Override
	public int GetPopulation(int stateId) {
		int responseCode = 0;
		try 
		{
			String url = "http://api.census.gov/data/2010/sf1?key=" + Constants.CENSUS_API_KEY + "&get=P0010001&for=state:" + stateId;
	
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			// optional default is GET
			con.setRequestMethod("GET");
			
			String data = con.getResponseMessage();
	
			responseCode = con.getResponseCode();
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseCode;
	}

}
