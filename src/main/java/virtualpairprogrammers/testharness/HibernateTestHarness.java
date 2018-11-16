package virtualpairprogrammers.testharness;




import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import virtualpairprogrammers.domain.Dummy;

public class HibernateTestHarness {

	
	private static SessionFactory sessionFactory = null;
	public static void main(String[] args) {
//		Dummy testStudent = new Dummy("wololo");
		
		SessionFactory sf = buildSessionFactory();
		
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();TypedQuery<Dummy> query = session.createQuery("FROM Dummy");
		    
		    List<Dummy> result = query.getResultList();
		    System.out.println(result.size());
		    for (Dummy dummy : result) {
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
