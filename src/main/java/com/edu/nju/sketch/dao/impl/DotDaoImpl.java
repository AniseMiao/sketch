package com.edu.nju.sketch.dao.impl;

import com.edu.nju.sketch.PO.Dot;
import com.edu.nju.sketch.dao.DotDao;
import com.edu.nju.sketch.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class DotDaoImpl implements DotDao {
    @Override
    public void addDots(String lid, ArrayList<Dot> dots) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        for(Dot dot : dots) {
            session.save(dot);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public ArrayList<Dot> getDots(String lid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Dot> ret = (ArrayList<Dot>)session.createQuery("from Dot where lid = ?1 ")
                .setParameter(1, lid).getResultList();
        transaction.commit();
        session.close();
        return ret;
    }
}
