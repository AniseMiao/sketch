package com.edu.nju.sketch.dao.impl;

import com.edu.nju.sketch.PO.Line;
import com.edu.nju.sketch.dao.LineDao;
import com.edu.nju.sketch.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class LineDaoImpl implements LineDao {
    @Override
    public void addLine(Line line) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(line);
        transaction.commit();
        session.close();
    }

    @Override
    public ArrayList<String> getLids(String pid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<String> ret = (ArrayList<String>)session.createQuery("select lid from Line where pid = ?1 ")
                .setParameter(1, pid).getResultList();
        transaction.commit();
        session.close();
        return ret;
    }
}
