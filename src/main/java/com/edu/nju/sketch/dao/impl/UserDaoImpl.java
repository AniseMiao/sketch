package com.edu.nju.sketch.dao.impl;

import com.edu.nju.sketch.PO.User;
import com.edu.nju.sketch.dao.UserDao;
import com.edu.nju.sketch.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean addUser(User user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User userPO = (User) session.createQuery("from User where username = ?1 ").setParameter(1, user.getUsername()).uniqueResult();
        if (userPO != null) {
            transaction.commit();
            session.close();
            return false;
        } else {
            session.save(user);
            transaction.commit();
            session.close();
        }
        return true;
    }

    @Override
    public void deleteUser(String aid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from User where aid = ?1 ").setParameter(1, aid).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void updateUser(User user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public User getUser(String username) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.createQuery("from User where username = ?1 ").setParameter(1, username).uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public boolean login(String username, String password) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        int num = session.createQuery("from User where username = ?1 and password = ?2")
                .setParameter(1, username).setParameter(2, password).getResultList().size();
        transaction.commit();
        session.close();
        return num == 1;
    }
}
