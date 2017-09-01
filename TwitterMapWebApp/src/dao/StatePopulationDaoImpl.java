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

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import resources.Constants;

public class StatePopulationDaoImpl implements StatePopulationDao{

	@Override
	public List<StatePopulation> GetPopulation() {
		SqlSession session = null;
		List results = null;
		try {
			session = ConnectionFactory.GetFactory().openSession();
			results = session.selectList("StatePopulationMapper.GetPopulation");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session != null) {
				session.close();
			}
		}
		return results;
	}

}
