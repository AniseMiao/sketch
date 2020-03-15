package targetModel;

import model.Dot;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.lang.annotation.Target;
import java.util.ArrayList;

public class Sketch {
    public ArrayList<Stroke> strokes;

    public ArrayList<Stroke> getStrokes() {
        return strokes;
    }

    public void setStrokes(ArrayList<Stroke> strokes) {
        this.strokes = strokes;
    }

    public static Sketch getSketch(String pid) {
        Sketch sketch = new Sketch();
        ArrayList<String> lids = getStrokeIds(pid);
        ArrayList<Stroke> strokes = new ArrayList<>();
        for (String lid : lids) {
            ArrayList<Dot> dots = Dot.getDots(lid);
            Stroke stroke = new Stroke();
            ArrayList<TargetDot> targetDots = new ArrayList<>();
            ArrayList<Integer> timeStamps = new ArrayList<>();
            for (Dot dot : dots) {
                TargetDot targetDot = new TargetDot(dot.getX(), dot.getY());
                targetDots.add(targetDot);
                timeStamps.add(dot.getT());
            }
            strokes.add(stroke);
        }
        return sketch;
    }

    static ArrayList<String> getStrokeIds(String pid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<String> ret = (ArrayList<String>)session.createQuery("select lid from Line where pid = ?1 ")
                .setParameter(1, pid).getResultList();
        transaction.commit();
        session.close();
        return ret;
    }

    public double getStandardSquare() {
        TargetDot firstDot = strokes.get(0).dots.get(0);
        double x1 = firstDot.x;
        double x2 = firstDot.x;
        double y1 = firstDot.y;
        double y2 = firstDot.y;
        for(Stroke stroke : strokes) {
            for(TargetDot dot: stroke.dots) {
                if (dot.x < x1) {
                    x1 = dot.x;
                }
                if (dot.x > x2) {
                    x2 = dot.x;
                }
                if (dot.y < y1) {
                    y1 = dot.y;
                }
                if (dot.y > y2) {
                    y2 = dot.y;
                }
            }
        }
        return (x2 - x1) * (y2 - y1);
    }
}
