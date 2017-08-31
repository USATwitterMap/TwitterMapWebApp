package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import resources.Constants;

public class StatePopulationDaoImpl implements StatePopulationDao{

	@Override
	public int GetPopulation(int stateId) {
		int responseCode = 0;
		try 
		{
			ObjectMapper mapper = new ObjectMapper();
			String url = "http://api.census.gov/data/2010/sf1?key=" + Constants.CENSUS_API_KEY + "&get=P0010001&for=state:" + stateId;
	
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			// optional default is GET
			con.setRequestMethod("GET");
			String data = getStringFromInputStream(con.getInputStream());
	
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
	
	// convert InputStream to String
		private static String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return sb.toString();

		}

}
