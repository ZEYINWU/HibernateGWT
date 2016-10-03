package zeyin.cis.cis550.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import com.google.gwt.dev.util.collect.HashSet;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import zeyin.cis.cis550.client.MyService;
import zeyin.cis.cis550.server.dto.Attrib;
import zeyin.cis.cis550.server.dto.Content;
import zeyin.cis.cis550.server.dto.Extraction;
import zeyin.cis.cis550.server.dto.User;
import zeyin.cis.cis550.shared.UserResult;
import zeyin.cis.cis550.shared.exceptions.DatabaseException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MyServiceImpl extends RemoteServiceServlet implements
		MyService {
	
	private final SessionFactory sessionFactory;
	
	public MyServiceImpl() throws ClassNotFoundException, SQLException {
		Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration
                        .getProperties());
        sessionFactory = configuration
                        .buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        
        populateDatabase();
	}
	
	/**
	 * Populate a few actors and movies in the database
	 */
	public void populateDatabase() {
        User kevinBacon = new User("Kevin Bacon");
        User sandraBullock = new User("Sandra Bullock");
        User georgeClooney = new User("George Clooney");
        User alfonscoCuron = new User("Alfonso Curon");
        
        User davidMatuszek = new User("David Matuszek");
        User chrisMurphy = new User("Chris Murphy");
        User swapneelSheth = new User("Swapneel Sheth");
        User mikeFelker = new User("Mike Felker");
        User zacharyIves = new User("Zachary Ives");
        
        List<Attrib> attrs = new ArrayList<Attrib>();
        attrs.add(new Attrib("a", "b"));
        attrs.get(0).getExtractions().add(new Extraction(0, 100));
        attrs.add(new Attrib("c", "d"));
        attrs.get(0).getExtractions().add(new Extraction(101, 155));
        Content sample = new Content(new byte[256], attrs);
        
        kevinBacon.getContent().add(sample);
        
        
        kevinBacon = addUser(kevinBacon);
        sandraBullock = addUser(sandraBullock);       
        georgeClooney = addUser(georgeClooney);       
        alfonscoCuron = addUser(alfonscoCuron);
        davidMatuszek = addUser(davidMatuszek);
        chrisMurphy = addUser(chrisMurphy);
        swapneelSheth = addUser(swapneelSheth);
        mikeFelker = addUser(mikeFelker);
        zacharyIves = addUser(zacharyIves);
        addFollow(georgeClooney, kevinBacon);
        addFollow(zacharyIves, mikeFelker);
        addFollow(alfonscoCuron, georgeClooney);
        addFollow(zacharyIves, swapneelSheth);
        addFollow(zacharyIves, chrisMurphy);
        addFollow(zacharyIves, georgeClooney);
        
	}

	
	public User addUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		User ret = null;
		try{
			tx = session.beginTransaction();
			
			// Does the user already have an entry?
	        List<User> users = session.createQuery("FROM User WHERE name = '" + user.getName() + "'").list();
	         
	        if (users.isEmpty()) {
	        	session.saveOrUpdate(user);
	        	ret = user;
	        } else {
	        	// Existing user ID
	        	for (User match: users) {
	        		ret = match;
	        		session.saveOrUpdate(match);
	        		
	        	}
	        }
			tx.commit();
		}catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return ret;
	}
	
	public void addFollow(User user1, User user2){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();			
			// Does the user already have an entry?
	        List<User> users1 = session.createQuery("FROM User WHERE name = '" + user1.getName() + "'").list();
	        List<User> users2 = session.createQuery("FROM User WHERE name = '" + user2.getName() + "'").list();	         
	        if ((!users1.isEmpty())&&(!users2.isEmpty())) {
	        	for (User match: users1) {
	        		match.getFollows().add(user2);
	        		session.merge(match);
	        	}
	        }
			tx.commit();
		}catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
	
	@Override
	public List<UserResult> getUsersWithName(String name)
			throws IllegalArgumentException, DatabaseException {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         List<User> users = session.createQuery("FROM User WHERE name = '" + name + "'").list();
	         
	         tx.commit();
	         List<UserResult> results = new ArrayList<UserResult>();
	         
	         for (User u : users) {
	        	 
	        	 Set<UserResult> urFollowSet = new HashSet<UserResult>();
	        	 
	        	 for(User u1: u.getFollows()) {
	        		 urFollowSet.add(new UserResult(u1.getId(),u1.getName()));
	        	 }
	        	 UserResult ur = new UserResult(u.getId(), u.getName());
	        	 ur.setFollows(urFollowSet);
	        	 // TODO: populate UserResult with the follows set
	        	 results.add(ur);
	         }
	         return results;
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return null;
	}
}
