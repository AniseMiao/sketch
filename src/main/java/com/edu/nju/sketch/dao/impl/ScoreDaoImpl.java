package com.edu.nju.sketch.dao.impl;

import com.edu.nju.sketch.PO.Score;
import com.edu.nju.sketch.dao.ScoreDao;
import com.edu.nju.sketch.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ScoreDaoImpl implements ScoreDao {
    @Override
    public void addScore(Score score) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(score);
        transaction.commit();
        session.close();
    }

    @Override
    public Score getScore(String pid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Score score = session.get(Score.class, pid);
        transaction.commit();
        session.close();
        return null;
    }
}
