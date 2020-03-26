package targetModel;

import java.lang.annotation.Target;

public class TargetDot {
    public double x;
    public double y;
    public long t;

    public TargetDot() {

    }

    public TargetDot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "TargetDot{" +
                "x=" + x +
                ", y=" + y +
                ", t=" + t +
                '}';
    }

    // 两点间距离
    public static double getDistance(TargetDot dot1, TargetDot dot2) {
        double sum = (dot1.x - dot2.x) * (dot1.x - dot2.x) + (dot1.y - dot2.y) * (dot1.y - dot2.y);
        return Math.sqrt(sum);
    }

    // 三点求夹角
    public static double getAngle(TargetDot centerDot, TargetDot dot1, TargetDot dot2) {
        double acos = ((dot1.x - centerDot.x) * (dot2.x - centerDot.x)
                + (dot1.y - centerDot.y) * (dot2.y - centerDot.y))
                / (getDistance(centerDot, dot1) * getDistance(centerDot, dot2));
        return Math.acos(acos);
    }
}
