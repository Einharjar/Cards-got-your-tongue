package handelers;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import als.domain.PersonDetails;
import als.domain.Transactions;
import als.domain.Users;

public class DBwriter {

	static SessionFactory sf = buildSessionFactory();
	
	
	

	public static void removeBananas(String username, int b) {
		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		TypedQuery<Users> query = session.createQuery("FROM Users");
	    List<Users> result = query.getResultList();
	    

	    for(Users u : result) {
	    	if(username.equals(u.getUsername())) {
	    		Transactions t = new Transactions(username, b, "");
	    		u.removeBananas(b);
	    		session.save(u);
	    		session.save(t);
	    		Transaction tx = session.beginTransaction();
	    	    tx.commit();
	    	    break;
	    	}
	    	}
	    

	    session.close();
	}
	

	public static void addBananas(String username, int b) {
		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		TypedQuery<Users> query = session.createQuery("FROM Users");
	    List<Users> result = query.getResultList();
	    

	    for(Users u : result) {
	    	if(username.equals(u.getUsername())) {
//	    		return;
	    		Transactions t = new Transactions(username, b, "");
	    		u.addBananas(b);
	    		session.save(u);
	    		session.save(t);
	    		Transaction tx = session.beginTransaction();
	    	    tx.commit();
	    	    break;
	    	}
	    	}
	    

	    session.close();
	}
	
	
	public static Users getUserByID(int id) {
		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		TypedQuery<Users> query = session.createQuery("FROM Users");
	    List<Users> result = query.getResultList();
	    

	    for(Users u : result) {
	    	if(id == (u.getUserId())) {
	    	    session.close();
	    		return u;
	    		
	    	}
	    	}
	    

	    session.close();
		return null;
	}

	public static List<Users> getAllUsers() {

		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		TypedQuery<Users> query = session.createQuery("FROM Users");
	    List<Users> result = query.getResultList();
	    session.close();
		return result;
	}
	
	
	public static Users getUser(String username) {
		
		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		TypedQuery<Users> query = session.createQuery("FROM Users");
	    List<Users> result = query.getResultList();
	    

	    for(Users u : result) {
	    	if(username.equals(u.getUserName())) {
	    	    session.close();
	    		return u;
	    	}
	    	}
	    
	    session.close();
		
		return null;
	}
	
	
	public static boolean autenticateUser(String username, String password) {
		
		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		TypedQuery<Users> query = session.createQuery("FROM Users");
	    List<Users> result = query.getResultList();
	    

	    for(Users u : result) {
	    	if(username.equals(u.getUserName()) && password.equals(u.getPassword())) {
	    	    session.close();
	    		return true;
	    	}
	    	}
	    
	    session.close();
		
		return false;
	}
	public static void writeNewPerson(PersonDetails p) {

		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		TypedQuery<PersonDetails> query = session.createQuery("FROM PersonDetails");
	    List<PersonDetails> result = query.getResultList();
	    

	    for(PersonDetails pd : result) {
	    	if(p.getPerson() == pd.getPerson()) {
	    		return;
	    	}
	    	}
	    
		session.save(p);
		Transaction tx = session.beginTransaction();
	    tx.commit();
	    session.close();
	}
	
	public static boolean writeNewUser(String username, String password, int personid, String fnamn, String enamn) {

		Session session = sf.openSession();
		
		PersonDetails pd = new PersonDetails(personid, fnamn, enamn, "");
		Users thisUser = new Users(personid, username, password);
		thisUser.setDetails(pd);

		session.save(pd);
		session.save(thisUser);
		Transaction tx = session.beginTransaction();
	    tx.commit();
	    session.close();
	    sf.close();
		return false;
	}
	public static void main(String[] args) {
		
		PersonDetails ud = new PersonDetails(420, "john", "larsson", "");
//		writeNewPerson(ud);
		Users u = new Users(0, "john", "qwerty");
		u.setDetails(ud);
		
		System.out.println(JSonParser.toJson(u));
//		System.out.println(writeNewUser(u, 420));
//	    sf.close();
	}
	public static boolean writeNewUser(Users u, int pd) {

		Session session = sf.openSession();
		
		@SuppressWarnings("unchecked")
		TypedQuery<PersonDetails> query = session.createQuery("FROM PersonDetails");
	    List<PersonDetails> result = query.getResultList();
	    
	    for(PersonDetails p : result) {
	    	if(p.getPerson() == pd) {

	    		session.save(u);
	    		Transaction tx = session.beginTransaction();
	    	    tx.commit();
	    	    session.close();
//	    	    sf.close();
	    		return true;
	    	}
	    }
	    
		
		return false;
	}
	
	  public static SessionFactory buildSessionFactory() {
	        try {
	            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
	                    .configure("hibernate.cfg.xml").build();
	            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
	            return metadata.getSessionFactoryBuilder().build();

	        } catch (HibernateException he) {
	            System.out.println("Session Factory creation failure");
	            throw he;
	        }
	    }
}
