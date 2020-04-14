package com.edu.nju.sketch.util.scoreUtil;


import com.edu.nju.sketch.targetModel.*;
import com.edu.nju.sketch.util.CommomUtil;
import com.edu.nju.sketch.util.PictureUtil;

public class SmoothnessScoreUtil {

    public double getLineSmoothnessScore(String pid, TargetLine[] lines) {
        Sketch sketch = PictureUtil.getSketch(pid);
        double angleBias = 0;
        double lengthSum = 0;
        for(Stroke stroke : sketch.strokes) {
            TargetLine line = stroke.toTargetLine();
            for (int i = 0; i < stroke.dots.size() - 1; i++) {
                TargetDot centerDot = stroke.dots.get(i);
                TargetDot dot1 = stroke.dots.get(i + 1);
                TargetDot dot2 = new TargetDot();
                if (line.isVertical()) {
                    dot2.setX(line.c);
                    dot2.setY(dot1.y);
                } else {
                    dot2.setX(dot1.x);
                    dot2.setY(dot1.x * line.k + line.b);
                }
                double angle =  Math.abs(TargetDot.getAngle(centerDot, dot1, dot2));
                lengthSum += line.getLength();
                angleBias += angle * lengthSum;
            }
        }
        // average的倒数乘以pi的值在1到+oo之间，将其等比分配给0-100分
        double average = angleBias / lengthSum;
        if (CommomUtil.equals(average, 0)) {
            return 100.00;
        } else {
            return 100.00 * (Math.PI/average - 1)/(Integer.MAX_VALUE - 1);
        }
    }

    public double getCircleSmoothnessScore(String pid, TargetCircle circle) {
        Sketch sketch = PictureUtil.getSketch(pid);
        double angleBias = 0;
        double angleSum = 0;
        TargetDot o = circle.getO();
        TargetDot horizontal = new TargetDot(o.x + circle.getR(), o.y);
        for(Stroke stroke : sketch.strokes) {
            for (int i = 0; i < stroke.dots.size() - 1; i++) {
                TargetDot centerDot = stroke.dots.get(i);
                TargetDot dot1 = stroke.dots.get(i + 1);
                double angle = TargetDot.getAngle(o, horizontal, dot1);
                double x = o.x + circle.getR() * Math.cos(angle);
                double y = o.y + circle.getR() * Math.sin(angle);
                TargetDot dot2 = new TargetDot(x,y);// o - dot1线上的标准点
                angleBias += TargetDot.getAngle(o, dot1, dot2);
                angleSum += TargetDot.getAngle(o, centerDot, dot1);
                angleBias += angle * angleSum;
            }
        }
        // average的倒数乘以pi的值在1到+oo之间，将其等比分配给0-100分
        double average = angleBias / angleSum;
        if (CommomUtil.equals(average, 0)) {
            return 100.00;
        } else {
            return 100.00 * (Math.PI/average - 1)/(Integer.MAX_VALUE - 1);
        }
    }

}
