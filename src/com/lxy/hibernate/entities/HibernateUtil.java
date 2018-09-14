package com.lxy.hibernate.entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateUtil {
	
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init() {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void destroy() {
		transaction.commit();
		session.close();
		sessionFactory.close();
		
	}

	@Test
	public void testSave() {
		News news = new News("Java", "Tom", new Date(new java.util.Date().getTime()));
		session.save(news);
	}
	
	
	@Test
	public void testGet() {
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
	}
	
	@Test
	public void testDoWork() {
		session.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println(connection);
			}
		});
	}
	
	@Test
	public void testHQL() {
		String hql = " FROM News n WHERE n.title = ? ";
		Query query = session.createQuery(hql);
		query.setString(0, "java");
		List<News> list = query.list();
		System.out.println(list);
	}
	
	@Test
	public void testPerson() {
		Person person = new Person();
		Address address = new Address();
		
		address.setProvince("guangdong");
		address.setCity("dongguan");
		
		person.setName("zhangsan");
		person.setAddress(address);
		
		session.save(person);
	}

}
