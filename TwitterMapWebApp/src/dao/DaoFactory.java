package dao;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoFactory {
	
	
	private AbstractApplicationContext context = null;
	
	public TwitterDataDao getTwitterDataDao() {
		
		if(context == null) {
			context = new ClassPathXmlApplicationContext("resources/spring-configuration.xml");
		}
		
		return (TwitterDataDao)context.getBean("twitterDataDao");
	}
}
