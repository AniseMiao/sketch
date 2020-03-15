package targetModel;

import util.CommomUtil;

public class TargetLine {
    public boolean vertical;
    public double k;
    public double b;
    public double c;
    public TargetDot start;
    public TargetDot end;

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public TargetDot getStart() {
        return start;
    }

    public void setStart(TargetDot start) {
        this.start = start;
    }

    public TargetDot getEnd() {
        return end;
    }

    public void setEnd(TargetDot end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "TargetLine{" +
                "vertical=" + vertical +
                ", k=" + k +
                ", b=" + b +
                ", c=" + c +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public double getLength() {
        return TargetDot.getDistance(start, end);
    }

    // 两点得线
    public TargetLine getLine(TargetDot dot1, TargetDot dot2) {
        TargetLine targetLine = new TargetLine();
        if(Math.abs(dot1.x - dot2.x) < 0.00001) {
            targetLine.setVertical(true);
            targetLine.setC((dot1.x + dot2.x) / 2);
        } else {
            double k = (dot1.y - dot2.y)/(dot1.x - dot2.x);
            targetLine.setK(k);
            double b = dot1.y - k * dot1.x;
            targetLine.setB(b);
        }
        targetLine.setStart(dot1);
        targetLine.setEnd(dot2);
        return targetLine;
    }

    // k 点 得线
    public static TargetLine getLine(double k, TargetDot dot) {
        TargetLine targetLine = new TargetLine();
        targetLine.setK(k);
        double b = dot.y - k * dot.x;
        targetLine.setB(b);
        targetLine.setStart(dot);
        return targetLine;
    }

    // 获取两线交点
    public static TargetDot getDotFromLines(TargetLine line1, TargetLine line2) {
        TargetDot dot = new TargetDot();
        if (CommomUtil.equals(line1.k, line2.k)) {
            return null;
        } else {
            double x = (line2.b - line1.b) / (line1.k - line2.k);
            double y = line1.k * x + line1.b;
            dot.setX(x);
            dot.setY(y);
            return dot;
        }
    }

    // 获取点到线距离
    public static double getDistanceFromLine(TargetDot dot, TargetLine line) {
        if (line.isVertical()) {
            return dot.x - line.getC();
        } else {
            double k = - 1 / line.k;
            TargetLine newLine = getLine(k, dot);
            TargetDot newDot = getDotFromLines(line, newLine);
            return TargetDot.getDistance(dot, newDot);
        }
    }
}
