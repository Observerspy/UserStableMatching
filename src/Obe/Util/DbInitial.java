package Obe.Util;
/**
* 功能：Hibernate数据库单例初始化
*/
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbInitial {

	private static  SessionFactory sessionFactory;
	private static DbInitial db = null;

	
	public DbInitial(){
	    sessionFactory = new Configuration().configure().buildSessionFactory();  
	}
	
	public static DbInitial getNewDb(){
		if(db==null)
			db = new DbInitial();
		return db;

	}
	
	public SessionFactory getFactory(){
		return sessionFactory;
	}
}
