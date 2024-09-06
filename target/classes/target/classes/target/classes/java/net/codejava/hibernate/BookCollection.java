package net.codejava.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BookCollection {
	
	protected SessionFactory sessionFactory;
	
	public BookCollection()
	{
		setup();
	}
	
    protected void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
    	        .configure() // configures settings from hibernate.cfg.xml
    	        .build();
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	    StandardServiceRegistryBuilder.destroy(registry);
    	}
    }
    
    protected void close() {
    	sessionFactory.close();
    }


	public void getAlldata(){       
	    
	    try{
	    	Session session = sessionFactory.openSession();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // Create CriteriaQuery
            CriteriaQuery<Book> criteria = builder.createQuery(Book.class);

            // Specify criteria root
            criteria.from(Book.class);

            // Execute query
            List<Book> entityList = session.createQuery(criteria).getResultList();

            for (Book e : entityList) {
            	System.out.println(e.getTitle());
            }
            
            session.close();
        }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void getByQuery(){       
	    
	    try{
	    	Session session = sessionFactory.openSession();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // Create CriteriaQuery
            CriteriaQuery<Book> criteria = builder.createQuery(Book.class);

            // Specify criteria root
            Root<Book> root = criteria.from(Book.class);
            criteria.select(root).where(builder.like(root.get("title"), "%4%"));

            // Execute query
            List<Book> entityList = session.createQuery(criteria).getResultList();

            for (Book e : entityList) {
            	System.out.println(e.getTitle());
            }
            
            session.close();
        }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	}

}
