package com.edu.nju.sketch.util.scoreUtil;

import com.edu.nju.sketch.targetModel.*;

public class SequenceScoreUtil {

    public double getSequenceCircleScore(String pid, TargetCircle circle) {
        Sketch sketch = Sketch.getSketch(pid);
        return 100 / sketch.strokes.size();
        /*
        if (sketch.strokes.size() == 1) {
            return 100.00;
        } else {
            double realAngle2 = 0.0;
            for (Stroke stroke : sketch.strokes) {
                double angle = getSimilarAngle(stroke, circle.getO());
                realAngle2 += (angle * angle);
            }
            return 100.00 * (realAngle2 / (circle.getAngle() * circle.getAngle()));
        }
        */
    }

    public double getSimilarAngle(Stroke stroke, TargetDot dot) {
        TargetDot start = stroke.dots.get(0);
        TargetDot end = stroke.dots.get(stroke.dots.size() - 1);
        return TargetDot.getAngle(dot, start, end);
    }

    // 计算直线的顺序分数
    public double getSequenceLineScore(String pid, TargetLine[] lines) {
        Sketch sketch = Sketch.getSketch(pid);
        double[] sequence = new double[lines.length];
        int[] num = new int[lines.length];
        for (int i = 0; i < num.length; i++) { num[i] = 0; }
        for (int i = 0; i < sketch.strokes.size(); i++) {
            int index = getSimilarSequence(sketch.strokes.get(i), lines);
            num[index]++;
            sequence[index] += Math.abs(i - index);
        }
        double ratio1 = 0;
        double ratio2 = 0;
        for (int i = 0; i < sequence.length; i++){
            if (num[i] != 0) {
                ratio1 += (1 / (1.0 * num[i]));
            }
            ratio2 += sequence[i];
        }
        ratio1 /= lines.length;
        ratio2 /= ((sketch.strokes.size() * sketch.strokes.size()) / 2 );
        return 100 * ratio1 * ratio2;
    }

    public int getSimilarSequence(Stroke stroke, TargetLine[] lines) {
        // 求起始点及3个4等分点，计算他们对于某条线的距离之和，最小的那个就是最接近的点。
        int index = 0;
        double min = getFakeDistance(stroke, lines[0]);
        for (int i = 1; i < lines.length; i++) {
            double newDistance = getFakeDistance(stroke, lines[i]);
            if (newDistance < min) {
                min = newDistance;
                index = i;
            }
        }
        return index;
    }

    public double getFakeDistance(Stroke stroke, TargetLine targetLine) {
        double sum = 0.0;
        int length = stroke.dots.size();
        TargetDot dot1 = stroke.dots.get(0);
        TargetDot dot2 = stroke.dots.get((length - 1) / 4);
        TargetDot dot3 = stroke.dots.get((length - 1) / 2);
        TargetDot dot4 = stroke.dots.get( 3 * (length - 1)/ 4);
        TargetDot dot5 = stroke.dots.get(length - 1);
        sum += TargetLine.getDistanceFromLine(dot1, targetLine);
        sum += TargetLine.getDistanceFromLine(dot2, targetLine);
        sum += TargetLine.getDistanceFromLine(dot3, targetLine);
        sum += TargetLine.getDistanceFromLine(dot4, targetLine);
        sum += TargetLine.getDistanceFromLine(dot5, targetLine);
        return sum;
    }
}
