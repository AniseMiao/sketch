package dao.impl;

import PO.ScorePO;
import dao.ScoreDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class ScoreDaoImpl implements ScoreDao {
    @Override
    public void addScore(ScorePO score) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(score);
        transaction.commit();
        session.close();
    }

    @Override
    public ScorePO getScore(String pid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ScorePO score = session.get(ScorePO.class, pid);
        transaction.commit();
        session.close();
        return null;
    }
}
