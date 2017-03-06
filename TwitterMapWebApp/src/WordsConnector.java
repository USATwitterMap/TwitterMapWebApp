import java.io.IOException;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;

public class WordsConnector {

	private TwitterWordsDao wordsDao;
			
	public void WordsConnectAttempt() throws IOException 
	{
		wordsDao = new TwitterWordsDao();
		wordsDao.GetWordsDuringTime(1);
	}
		
}
