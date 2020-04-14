package com.edu.nju.sketch.util;

import com.edu.nju.sketch.PO.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Dot.class);
        config.addAnnotatedClass(Line.class);
        config.addAnnotatedClass(Picture.class);
        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(Constructor.class);
        config.addAnnotatedClass(Score.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
}
