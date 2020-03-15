package dao.impl;

import PO.LinePO;
import dao.LineDao;
import model.Line;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;

public class LineDaoImpl implements LineDao {
    @Override
    public void addLine(LinePO line) {
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
        ArrayList<String> ret = (ArrayList<String>)session.createQuery("select lid from LinePO where pid = ?1 ")
                .setParameter(1, pid).getResultList();
        transaction.commit();
        session.close();
        return ret;
    }
}
