package targetModel;

import static targetModel.TargetDot.getDistance;

public class TargetCircle {
    TargetDot o;
    TargetDot start;
    TargetDot end;
    boolean moreThanHalf;
    double r;

    public TargetDot getO() {
        return o;
    }

    public void setO(TargetDot o) {
        this.o = o;
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

    public boolean isMoreThanHalf() {
        return moreThanHalf;
    }

    public void setMoreThanHalf(boolean moreThanHalf) {
        this.moreThanHalf = moreThanHalf;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "TargetCircle{" +
                "o=" + o +
                ", start=" + start +
                ", end=" + end +
                ", moreThanHalf=" + moreThanHalf +
                ", r=" + r +
                '}';
    }

    public double getAngle () {
        double acos = ((start.x - o.x) * (end.x - o.x) + (start.y - o.y) * (end.y - o.y)) / (r * r);
        return Math.acos(acos);
    }

    public double getAngle (TargetDot dot) {
        double acos = (start.x - o.x)  / r;
        return dot.y > o.y ? Math.acos(acos) : 2 * Math.PI - Math.acos(acos);
    }

    public double getLength() {
        if (isMoreThanHalf()) {
            return (2 - getAngle()) * r ;
        } else {
            return getAngle() * r;
        }
    }

    public double getBias(TargetDot dot, double h) {
        double y = Math.abs(getDistance(o, dot) - r);
        return y > h ? (y - h) : 0;
    }

    // 获得点偏离弧线的距离（横坐标） 百分比
    public double getBiasPercent(TargetDot dot, double h) {
        double y = getDistance(o, dot);
        if (y > r) {
            return 1 - r/y;
        }
        return 1 - y/r;
    }

    // 判断点是否在圆上
    public boolean onCircle(TargetDot dot) {
         return Math.abs(getDistance(dot, o) - r) < 0.00001;
    }

    // 判断点是否在弧上(角度是否在范围内
    public boolean onBow(TargetDot dot) {
        if (!onCircle(dot)) {
            return false;
        }
        double a1 = getAngle(start);
        double a2 = getAngle(end);
        double a0 = getAngle(dot);
        if (!moreThanHalf) {
            // 交界处
            if(Math.abs(a2 - a1) > Math.PI) {
                return (0 <= a0 && a0 <= a1) || (a2 <= a0 && a0 <= 2 * Math.PI);
            } else {
                return (a1 <= a0 && a0 <= a2);
            }
        } else {
            // 与上面相反即可
            if(Math.abs(a2 - a1) > Math.PI) {
                return !(0 <= a0 && a0 <= a1) && !(a2 <= a0 && a0 <= 2 * Math.PI);
            } else {
                return !(a1 <= a0 && a0 <= a2);
            }
        }
    }

    // 计算实际占地
    public double getStandardSquare() {
        TargetDot up = new TargetDot(o.x, o.y + r);
        TargetDot down = new TargetDot(o.x, o.y - r);
        TargetDot left = new TargetDot(o.x - r, o.y);
        TargetDot right = new TargetDot(o.x + r, o.y);
        double x1, x2, y1, y2;
        if (onBow(up)) {
            y1 = o.y + r;
        } else {
            y1 = Math.max(start.y, end.y);
        }
        if (onBow(down)) {
            y2 = o.y - r;
        } else {
            y2 = Math.min(start.y, end.y);
        }
        if (onBow(left)) {
            x1 = o.x - r;
        } else {
            x1 = Math.min(start.x, end.x);
        }
        if (onBow(right)) {
            x2 = o.x + r;
        } else {
            x2 = Math.max(start.x, end.x);
        }
        return (x2 - x1) * (y2 - y1);
    }

    // 如果第二条弧的起始及结束点均在圆的容错带内，且第二条弧的起始点与第一条弧的结束点相接，
    // 则可以认为两者可以连接为一条弧
    public boolean canLink(TargetDot l1d2, TargetDot l2d1, TargetDot l2d2, double h) {
        return getBias(l2d1, h) == 0 && getBias(l2d2, h) == 0 && getDistance(l1d2, l2d1) <= h;
    }
}
