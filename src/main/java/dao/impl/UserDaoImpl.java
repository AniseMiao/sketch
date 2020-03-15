package dao.impl;

import PO.UserPO;
import dao.UserDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class UserDaoImpl implements UserDao {
    @Override
    public void addUser(UserPO user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteUser(String aid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from UserPO where aid = ?1 ").setParameter(1, aid).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void updateUser(UserPO user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public UserPO getUser(String aid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        UserPO user = session.get(UserPO.class, aid);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public boolean login(String username, String password) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        int num = session.createQuery("from UserPO where username = ?1 and password = ?2")
                .setParameter(1, username).setParameter(2, password).getResultList().size();
        transaction.commit();
        session.close();
        return num == 1;
    }
}
