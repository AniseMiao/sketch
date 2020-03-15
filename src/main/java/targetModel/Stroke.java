package targetModel;

import util.CommomUtil;
import java.util.ArrayList;

public class Stroke {
    public ArrayList<TargetDot> dots;
    public ArrayList<Integer> timeStamps;

    public ArrayList<TargetDot> getDots() {
        return dots;
    }

    public void setDots(ArrayList<TargetDot> dots) {
        this.dots = dots;
    }

    public ArrayList<Integer> getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(ArrayList<Integer> timeStamps) {
        this.timeStamps = timeStamps;
    }

    public TargetLine toTargetLine() {
        // 其他点想读计算平均倾角
        TargetLine targetLine = new TargetLine();
        TargetDot standardDot = new TargetDot(dots.get(0).x + 1, dots.get(0).y);
        double angleSum = 0.0;
        for (int i = 1; i < dots.size(); i++) {
            angleSum += TargetDot.getAngle(dots.get(0), dots.get(i), standardDot);
        }
        int size = dots.size();
        double averageAngle = angleSum / ( size - 1);
        if (CommomUtil.equals(averageAngle, Math.PI/2)) {// 如果近似垂直
            targetLine.setVertical(true);
            targetLine.setStart(dots.get(0));
            targetLine.setC(dots.get(0).x);
            targetLine.setEnd(new TargetDot(targetLine.getC(), dots.get(size - 1).y));
        } else {
            double k = Math.tan(averageAngle);
            targetLine = TargetLine.getLine(k,dots.get(0));
            TargetDot endDot = dots.get(size - 1);
            TargetDot modifyDot = new TargetDot(endDot.x, endDot.x * targetLine.k + targetLine.b);
            targetLine.setEnd(modifyDot);
        }
        return targetLine;
    }
}
