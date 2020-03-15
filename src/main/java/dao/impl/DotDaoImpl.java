package dao.impl;

import PO.DotPO;
import dao.DotDao;
import model.Dot;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;

public class DotDaoImpl implements DotDao {
    @Override
    public void addDots(String lid, ArrayList<DotPO> dots) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        for(DotPO dot : dots) {
            session.save(dot);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public ArrayList<DotPO> getDots(String lid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<DotPO> ret = (ArrayList<DotPO>)session.createQuery("from DotPO where lid = ?1 ")
                .setParameter(1, lid).getResultList();
        transaction.commit();
        session.close();
        return ret;
    }
}
