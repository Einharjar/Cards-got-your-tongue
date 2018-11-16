import java.io.IOException;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import virtualpairprogrammers.domain.Dummy;
import virtualpairprogrammers.testharness.HibernateTestHarness;

@WebServlet("/login")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	

		SessionFactory sf = HibernateTestHarness.buildSessionFactory();
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
    	
        response.setContentType("text/plain");
        response.getWriter().println(result);
    }
}
