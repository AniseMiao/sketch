package util;

import PO.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(DotPO.class);
        config.addAnnotatedClass(LinePO.class);
        config.addAnnotatedClass(PicturePO.class);
        config.addAnnotatedClass(UserPO.class);
        config.addAnnotatedClass(IdPO.class);
        config.addAnnotatedClass(ScorePO.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
}
