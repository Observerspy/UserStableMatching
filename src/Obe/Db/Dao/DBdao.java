package Obe.Db.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Obe.Db.Dto.People;
import Obe.Db.Dto.User;

public class DBdao {

	public void saveUM(SessionFactory sf,Object o){
		 Session session = sf.openSession();
		 session.saveOrUpdate(o);
		 session.beginTransaction();  
	     session.getTransaction().commit(); 
	     session.close();
	}
	
	public List<Integer> getAll(SessionFactory sf,String table){
		 Session session = sf.openSession();
		 Query query=session.getNamedQuery(table);
		 List<Integer> l = query.list();
		 session.beginTransaction();  
	     session.getTransaction().commit(); 
	     session.close();
		 return l;
	}
	
	public List<User> getPart(SessionFactory sf,String table,int num){
		 Session session = sf.openSession();
		 Query query=session.getNamedQuery(table);
		 query.setMaxResults(num);
		 List<User> l = query.list();
		 session.beginTransaction();  
	     session.getTransaction().commit(); 
	     session.close();
		 return l;
	}
	
	public List<People> getPart_p(SessionFactory sf,String table,int num){
		 Session session = sf.openSession();
		 Query query=session.getNamedQuery(table);
		 query.setMaxResults(num);
		 List<People> l = query.list();
		 session.beginTransaction();  
	     session.getTransaction().commit(); 
	     session.close();
		 return l;
	}
	
	public List<Object> getId(SessionFactory sf,String table,int id){
		 Session session = sf.openSession();
		 Query query=session.getNamedQuery(table);
		 query.setString("id",String.valueOf(id));
		 List<Object> l = query.list();
		 session.beginTransaction();  
	     session.getTransaction().commit(); 
	     session.close();
		 return l;
	}
	
	public List<Object> getT(SessionFactory sf,String table,int id,String source){
		 Session session = sf.openSession();
		 Query query=session.getNamedQuery(table);
		 query.setString("id",String.valueOf(id));
		 query.setString("source",source);
		 List<Object> l = query.list();
		 session.beginTransaction();  
	     session.getTransaction().commit(); 
	     session.close();
		 return l;
	}
}
