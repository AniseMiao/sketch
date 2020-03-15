package dao.impl;

import PO.PicturePO;
import dao.PictureDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;

public class PictureDaoImpl implements PictureDao {
    @Override
    public void addPicture(PicturePO picture) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(picture);
        transaction.commit();
        session.close();
    }

    @Override
    public void deletePicture(String pid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from PicturePO where pid = ?1 ").setParameter(1, pid).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public PicturePO getPicture(String pid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        PicturePO picture = session.get(PicturePO.class, pid);
        transaction.commit();
        session.close();
        return picture;
    }

    @Override
    public ArrayList<PicturePO> getPictures(String aid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<PicturePO> ret = (ArrayList<PicturePO>)session.createQuery("from PicturePO where aid = ?1 ")
                .setParameter(1, aid).getResultList();
        transaction.commit();
        session.close();
        return ret;
    }
}
