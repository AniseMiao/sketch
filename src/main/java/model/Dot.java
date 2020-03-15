package model;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;

public class Dot {

  private double X;
  private double Y;
  private long T;

  public double getX() {
    return X;
  }

  public void setX(double x) {
    X = x;
  }

  public double getY() {
    return Y;
  }

  public void setY(double y) {
    Y = y;
  }

  public long getT() {
    return T;
  }

  public void setT(long t) {
    T = t;
  }

  public static ArrayList<Dot> getDots(String lid) {
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    ArrayList<Dot> ret = (ArrayList<Dot>)session.createQuery("from Dot where lid = ?1 ")
            .setParameter(1, lid).getResultList();
    transaction.commit();
    session.close();
    return ret;
  }

}
