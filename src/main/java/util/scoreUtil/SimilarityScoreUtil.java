package util.scoreUtil;

import targetModel.*;
import util.CommomUtil;

import java.util.ArrayList;
import java.util.Comparator;

public class SimilarityScoreUtil {
    public double getSimilarityScore(ArrayList<TargetDot> realDots, ArrayList<TargetDot> standardDots) {
        // 求出集合A中的点与集合B中的点的最小值中的最大值
        ArrayList<Double> minDistances = new ArrayList<>();
        for (TargetDot dot : realDots) {
            double min = getMinDistance(dot, standardDots);
            minDistances.add(min);
        }
        minDistances.sort(new Comparator<Double>() {
            @Override
            public int compare(Double a, Double b) {
                if (CommomUtil.equals(a, b)) {
                    return 0;
                } else if (a > b) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        double maxmin = minDistances.get(minDistances.size() - 1);
        return 100.00;
    }

    public double getMinDistance(TargetDot dot, ArrayList<TargetDot> standardDots) {
        double min = TargetDot.getDistance(dot, standardDots.get(0));
        for (int i = 1; i < standardDots.size(); i++) {
            double newDistance = TargetDot.getDistance(dot, standardDots.get(0));
            if (newDistance < min) {
                min = newDistance;
            }
        }
        return min;
    }

    public double getSimilarityCircleScore(String pid, TargetCircle circle) {
        ArrayList<TargetDot> realDots = getDots(pid);
        ArrayList<TargetDot> standardDots = new ArrayList<>();
        int num = realDots.size();
        double averageAngle = circle.getAngle() / (num - 1);
        for (int i = 0; i < num - 1; i++) {
            TargetDot newDot = getNewDot(circle, averageAngle * (i + 1));
            standardDots.add(newDot);
        }
        // 构造圆弧/圆上的等分点
        return getSimilarityScore(realDots, standardDots);
    }

    public TargetDot getNewDot(TargetCircle circle, double biaAngle) {
        double startAngle = circle.getAngle(circle.getStart());
        double realAngle = startAngle - biaAngle;
        double x = circle.getO().x + Math.cos(realAngle) * circle.getR();
        double y = circle.getO().y + Math.sin(realAngle) * circle.getR();
        return new TargetDot(x, y);
    }

    public double getSimilarityLineScore(String pid, TargetLine[] lines) {
        ArrayList<TargetDot> realDots = getDots(pid);
        ArrayList<TargetDot> standardDots = new ArrayList<>();
        double lengthSum = 0.0;
        for (TargetLine line : lines) {
            lengthSum += line.getLength();
        }
        for (TargetLine line : lines) {
            ArrayList<TargetDot> tempDots = getLineStandardDots(line, (int)(realDots.size() * line.getLength() / lengthSum));
            standardDots.addAll(tempDots);
        }
        // 构造直线上的等分点
        return getSimilarityScore(realDots, standardDots);
    }

    public ArrayList<TargetDot> getLineStandardDots(TargetLine line, int num) {
        ArrayList<TargetDot> ret = new ArrayList<>();
        double X = line.getEnd().getX() - line.getStart().getX();
        double Y = line.getEnd().getY() - line.getStart().getY();
        for (int i = 0; i < num - 1; i ++) {
            double x = line.getStart().getX() + ((X * i) / (num -1));
            double y = line.getStart().getY() + ((Y * i) / (num -1));
            ret.add(new TargetDot(x, y));
        }
        return ret;
    }

    public ArrayList<TargetDot> getDots(String pid) {
        Sketch sketch = Sketch.getSketch(pid);
        ArrayList<TargetDot> ret = new ArrayList<>();
        for (Stroke stroke : sketch.strokes) {
            for(TargetDot targetDot : stroke.dots) {
                ret.add(targetDot);
            }
        }
        return ret;
    }
}
