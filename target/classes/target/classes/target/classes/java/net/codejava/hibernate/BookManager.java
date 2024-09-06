package net.codejava.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BookManager {
    protected SessionFactory sessionFactory;
 
    protected void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
    	        .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
    	        .build();
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	    StandardServiceRegistryBuilder.destroy(registry);
    	}
    }
 
    protected void exit() {
    	sessionFactory.close();
    }
 
    protected long create() {
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setPrice(32.59f);
     
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.save(book);
        
        long savedId = book.getId();
        System.out.println(String.format("New book id: %d", savedId));
     
        session.getTransaction().commit();
        session.close();
        
        return savedId;
    }
    
    protected void read(long id) {
    	
        Session session = sessionFactory.openSession();
        
        long bookId = id;
        Book book = session.get(Book.class, bookId);
     
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: " + book.getPrice());
     
        session.close();
    }
 
    protected void update(long id) {
	    Book book = new Book();
	    book.setId(id);
	    book.setTitle(String.format("Ultimate Java Programming %d", id));
	    book.setAuthor("Nam Ha Minh");
	    book.setPrice(19.99f);
	 
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.update(book);
	 
	    session.getTransaction().commit();
	    session.close();
    }
 
    protected void delete(long id) {
        Book book = new Book();
        book.setId(id);
     
        Session session = sessionFactory.openSession();
        session.beginTransaction();
     
        session.delete(book);
     
        session.getTransaction().commit();
        session.close();
    }
 
    public static void main(String[] args) {
        BookManager manager = new BookManager();
        manager.setup();
        
        long newId = manager.create();
        manager.read(newId);
        manager.update(newId);
        manager.read(newId);
//        manager.delete(newId);
        
        manager.sessionFactory.close();
        
        BookCollection bookCollection = new BookCollection();
        bookCollection.getAlldata();
        bookCollection.getByQuery();
        bookCollection.close();
        
        manager.exit();
    }
}