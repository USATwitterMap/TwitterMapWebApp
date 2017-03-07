import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class TwitterDataConnector {

	private TwitterDataDao wordsDao;
			
	@Test
	public void WordsConnectAttempt() throws IOException 
	{
		wordsDao = new TwitterDataDao();
		TwitterWordBO word = new TwitterWordBO();
		word.setTime(260);
		word.setWord("you");
		TwitterTimeBO time = wordsDao.GetTimeBetween(java.sql.Timestamp.valueOf("2017-03-4 19:09:10.0"));
		List<TwitterWordBO> words = wordsDao.GetOccurancesByTime(word);
		String debug = "";
	}
		
}
