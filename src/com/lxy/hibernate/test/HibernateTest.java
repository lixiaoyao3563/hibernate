package com.lxy.hibernate.test;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import com.lxy.hibernate.entities.News;

public class HibernateTest {

	@Test
	public void test() {
		
		SessionFactory sessionFactory =null;
		
		Configuration configuration = new Configuration().configure();
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		Session session = sessionFactory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		
		News news = new News("Java", "Tom", new Date(new java.util.Date().getTime()));
		session.save(news);
		
		transaction.commit();
		session.close();
		sessionFactory.close();
		
		
	}

}
