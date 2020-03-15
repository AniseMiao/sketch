package model;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.ArrayList;

public class Line {

  private ArrayList<Dot> dots;

  public ArrayList<Dot> getDots() {
    return dots;
  }

  public void setDots(ArrayList<Dot> dots) {
    this.dots = dots;
  }

  public Dot getStartDot(String lid) {
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    Dot ret = (Dot)session.createQuery("from Dot where lid = ?1 and t in (select min(t) from Dot where lid = ?1)")
            .setParameter(1, lid).uniqueResult();
    transaction.commit();
    session.close();
    return ret;
  }

  public Dot getEndDot(String lid) {
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();
    Dot ret = (Dot)session.createQuery("from Dot where lid = ?1 and t in (select max(t) from Dot where lid = ?1)")
            .setParameter(1, lid).uniqueResult();
    transaction.commit();
    session.close();
    return ret;
  }
}
