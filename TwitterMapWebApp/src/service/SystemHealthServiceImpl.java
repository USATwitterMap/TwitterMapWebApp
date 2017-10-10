package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.TwitterDataDao;
import dao.results.SystemDiag;
import view.SystemHealthView;

@Service
public class SystemHealthServiceImpl implements SystemHealthService {

	@Autowired
	private TwitterDataDao wordsDao;
	
	@Override
	public SystemHealthView CreateSystemHealthView() {
		SystemHealthView systemHealth =new SystemHealthView();
		List<SystemDiag> systemHealthResults = wordsDao.GetSystemHealth();
		Calendar latestDate  = Calendar.getInstance();
		Calendar currentDate  = Calendar.getInstance();
		
		List<long[]> systemDiagnosticData = new ArrayList<long[]>();
		
		for(int diagnosticIndex = 0; diagnosticIndex < systemHealthResults.size(); diagnosticIndex++)
		{
			if(systemDiagnosticData.size() > 0 ) 
			{
				latestDate.setTime(new Date(systemDiagnosticData.get(systemDiagnosticData.size() - 1)[0]));
				currentDate.setTime(systemHealthResults.get(diagnosticIndex).getForDate());
				while((currentDate.getTimeInMillis() - latestDate.getTimeInMillis()) > (TimeUnit.DAYS.toMillis(1) + 1)) 
				{
					latestDate.add(Calendar.DATE, 1);
					systemDiagnosticData.add(new long[] {latestDate.getTime().getTime(), 23});
				}
			}
			systemDiagnosticData.add(new long[] {systemHealthResults.get(diagnosticIndex).getForDate().getTime(), systemHealthResults.get(diagnosticIndex).getNumPosts()});
		}
		systemHealth.setSystemHealth(new long[systemDiagnosticData.size()][2]);
		for(int diagnosticIndex = 0; diagnosticIndex < systemDiagnosticData.size(); diagnosticIndex++)
		{
			systemHealth.getSystemHealth()[diagnosticIndex] = systemDiagnosticData.get(diagnosticIndex);
		}
		return systemHealth;
	}

}
