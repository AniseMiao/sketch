package com.edu.nju.sketch.dao.impl;

import com.edu.nju.sketch.PO.Picture;
import com.edu.nju.sketch.dao.PictureDao;
import com.edu.nju.sketch.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class PictureDaoImpl implements PictureDao {
    @Override
    public void addPicture(Picture picture) {
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
        session.createQuery("delete from Picture where pid = ?1 ").setParameter(1, pid).executeUpdate();
        session.createQuery("delete from Dot  where lid in (select lid from Line where pid = ?1)").setParameter(1, pid).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public Picture getPicture(String pid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Picture picture = session.get(Picture.class, pid);
        transaction.commit();
        session.close();
        return picture;
    }

    @Override
    public ArrayList<Picture> getPictures(String aid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Picture> ret = (ArrayList<Picture>)session.createQuery("from Picture where aid = ?1 ")
                .setParameter(1, aid).getResultList();
        transaction.commit();
        session.close();
        return ret;
    }
}
