package als.domain.testharness;




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

import als.domain.Dummy;
import als.domain.PersonDetails;
import als.domain.Users;
import handelers.JSonParser;


public class HibernateTestHarness {

	
	private static SessionFactory sessionFactory = null;
	public static void main(String[] args) {
		
//		new JSonParser();
//		JSonParser.jsonToObject("");
		
//		Dummy testStudent = new Dummy("wololo");
		PersonDetails d = new PersonDetails(25221444, "", "", "test@examples.se");
		Users testStudent = new Users(d, "alex", "alex");
		
		SessionFactory sf = buildSessionFactory();
		
		Session session = sf.openSession();
		session.save(testStudent);
		Transaction tx = session.beginTransaction();
//		tx.
		TypedQuery<Users> query = session.createQuery("FROM Users");
		    
		    List<Users> result = query.getResultList();
		    System.out.println(result.size());
		    for (Users dummy : result) {
		    	System.out.println(dummy);
			    	
			}
		    tx.commit();
		    session.close();
		
		sf.close();
		
		
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
