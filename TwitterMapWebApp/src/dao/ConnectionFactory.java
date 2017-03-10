package dao;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ConnectionFactory 
{
	private static SqlSessionFactory sessionFactory = null;
	public static SqlSessionFactory GetFactory() throws IOException 
	{
		if(sessionFactory == null) {
			String resource = "resources/mybatis-configuration.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}
		return sessionFactory;
	}
}
