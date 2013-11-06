package ch.itraum.jpa.runtime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import ch.itraum.jpa.model.Author;
import ch.itraum.jpa.model.Book;

public class HibernateExample {

	public void run() {
		Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost/jpa-example");
		configuration.setProperty("hibernate.connection.username", "postgres");
		configuration.setProperty("hibernate.connection.password", "");

		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.format_sql", "true");
		configuration.setProperty("hibernate.use_sql_comments", "true");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");

		configuration.addAnnotatedClass(Author.class);
		configuration.addAnnotatedClass(Book.class);

		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		// Save
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Author author = new Author();
		author.setName("Vader Abraham");

		session.save(author);

		Book book = new Book();
		book.setTitle("Meine Schlümpfe und ich");
		book.setAuthor(author);

		session.save(book);

		session.getTransaction().commit();
		session.close();

		// Read
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		Author abraham = (Author) session2.get(Author.class, 1);
		System.out.println("--------- " + abraham.getName());

		session2.getTransaction().commit();
		session2.close();

	}

}
