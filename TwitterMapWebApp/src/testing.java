import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testing {
	
	@Test
	public void test () throws IOException {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"resources\\spring-context.xml");
		//TwitterDataController hw = (TwitterDataController) appContext.getBean("connector");
		
		//hw.WordsConnectAttempt();
	}
}
