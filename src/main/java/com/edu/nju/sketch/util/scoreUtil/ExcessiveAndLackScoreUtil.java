package com.edu.nju.sketch.util.scoreUtil;


import com.edu.nju.sketch.targetModel.*;
import com.edu.nju.sketch.util.CommomUtil;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;



public class ExcessiveAndLackScoreUtil {

    private static final double TOLERANCE = 1.0;

    // 主要根据占有的角度计算结果，找出每段在范围内的占有角
    public double getCircleLossScore(String pid, TargetCircle circle) {
        double standardAngle = circle.getAngle();
        double realAngle = 0.0;
        Sketch sketch = Sketch.getSketch(pid);
        ArrayList<TargetDot> dots = new ArrayList<>();
        // 对于每条线，找出所有不在范围内的角度值
        for (Stroke stroke : sketch.strokes) {
            dots.add(stroke.dots.get(0));
            dots.add(stroke.dots.get(stroke.dots.size() - 1));
            for (int i = 0;i < stroke.dots.size() - 1; i++) {
                TargetDot dot1 = stroke.dots.get(i);
                TargetDot dot2 = stroke.dots.get(i + 1);
                double distance1 = Math.abs(TargetDot.getDistance(dot1, circle.getO()) - circle.getR());
                double distance2 = Math.abs(TargetDot.getDistance(dot2, circle.getO()) - circle.getR());
                if (distance1 > TOLERANCE && distance2 > TOLERANCE) {
                    realAngle += TargetDot.getAngle(circle.getO(), dot1, dot2);
                } else if(distance1 <= TOLERANCE || distance2 <= TOLERANCE) {
                    realAngle += 0.5 *TargetDot.getAngle(circle.getO(), dot1, dot2);
                }
            }
        }
        ArrayList<Double>  simulateAngles = new ArrayList<>();
        for (int i = 0; i < dots.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (j % 2 == 0 && j == i - 1) {
                    simulateAngles.add(TargetDot.getAngle(circle.getO(), dots.get(i), dots.get(j)));
                }
            }
        }
        simulateAngles.sort(new Comparator<Double>() {
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
        for (int i = 0; i < dots.size() / 2 - 1; i++) {
            realAngle += simulateAngles.get(i);
        }
        return 100 * (1 - realAngle / standardAngle);
    }

    // 主要根据占有的角度计算结果，找出每段在范围内的占有角
    public double getCircleExcessiveScore(String pid, TargetCircle circle) {
        double standardDistance = circle.getLength();
        double realDistance = 0.0;
        Sketch sketch = Sketch.getSketch(pid);
        // 对于每条线，找出所有不在范围内的角度值
        for (Stroke stroke : sketch.strokes) {
            for (int i = 0;i < stroke.dots.size() - 1; i++) {
                TargetDot dot1 = stroke.dots.get(i);
                TargetDot dot2 = stroke.dots.get(i + 1);
                double distance1 = Math.abs(TargetDot.getDistance(dot1, circle.getO()) - circle.getR());
                double distance2 = Math.abs(TargetDot.getDistance(dot2, circle.getO()) - circle.getR());
                if (distance1 > TOLERANCE && distance2 > TOLERANCE) {
                    realDistance += TargetDot.getDistance(dot1, dot2);
                } else if(distance1 <= TOLERANCE || distance2 <= TOLERANCE) {
                    realDistance += 0.5 * TargetDot.getDistance(dot1, dot2);
                }
            }
        }
        return 100 * (realDistance / (realDistance + standardDistance));
    }

    public double getLineLossScore(String pid, TargetLine[] lines) {
        double standardDistance = getDistanceSum(lines);
        double realDistance = 0.0;
        Sketch sketch = Sketch.getSketch(pid);
        // 对于每条线，找出所有不在范围内的角度值
        for (Stroke stroke : sketch.strokes) {
            for (int i = 0;i < stroke.dots.size() - 1; i++) {
                TargetDot dot1 = stroke.dots.get(i);
                TargetDot dot2 = stroke.dots.get(i + 1);
                TargetLine line = getSimilarLine(dot1, lines);
                double distance1 = Math.abs(TargetLine.getDistanceFromLine(dot1, line) - TOLERANCE);
                double distance2 = Math.abs(TargetLine.getDistanceFromLine(dot2, line) - TOLERANCE);
                if (distance1 <= TOLERANCE && distance2 <= TOLERANCE) {
                    if (line.isVertical()) {
                        realDistance += (Math.abs(dot1.y - dot2.y));
                    } else {
                        realDistance += (Math.abs(line.getK() * (dot1.x - dot2.x)));
                    }
                } else if(distance1 <= TOLERANCE || distance2 <= TOLERANCE) {
                    if (line.isVertical()) {
                        realDistance += 0.5 * (Math.abs(dot1.y - dot2.y));
                    } else {
                        realDistance += 0.5 * (Math.abs(line.getK() * (dot1.x - dot2.x)));
                    }
                }
            }
        }
        return 100 * (1 - realDistance / standardDistance);
    }

    public double getMinDistance(TargetDot dot, TargetLine[] lines) {
        double min = TargetLine.getDistanceFromLine(dot, lines[0]);
        for (TargetLine line : lines) {
            double distance = TargetLine.getDistanceFromLine(dot, line);
            if (distance < min) {
                min = distance;
            }
        }
        return min;
    }

    public TargetLine getSimilarLine(TargetDot dot, TargetLine[] lines) {
        TargetLine ret = lines[0];
        double min = TargetLine.getDistanceFromLine(dot, lines[0]);
        for (TargetLine line : lines) {
            double distance = TargetLine.getDistanceFromLine(dot, line);
            if (distance < min) {
                min = distance;
                ret = line;
            }
        }
        return ret;
    }

    public double getLineExcessiveScore(String pid, TargetLine[] lines) {
        double standardDistance = getDistanceSum(lines);
        double excessiveDistance = 0.0;
        Sketch sketch = Sketch.getSketch(pid);
        // 对于每条线，找出所有不在范围内的角度值
        for (Stroke stroke : sketch.strokes) {
            for (int i = 0;i < stroke.dots.size() - 1; i++) {
                TargetDot dot1 = stroke.dots.get(i);
                TargetDot dot2 = stroke.dots.get(i + 1);
                double distance1 = Math.abs(getMinDistance(dot1, lines) - TOLERANCE);
                double distance2 = Math.abs(getMinDistance(dot2, lines) - TOLERANCE);
                if (distance1 > TOLERANCE && distance2 > TOLERANCE) {
                    excessiveDistance += TargetDot.getDistance(dot1, dot2);
                } else if(distance1 <= TOLERANCE || distance2 <= TOLERANCE) {
                    excessiveDistance += 0.5 * TargetDot.getDistance(dot1, dot2);
                }
            }
        }
        return 100 * (1 - excessiveDistance / excessiveDistance + standardDistance);
    }

    public double getDistanceSum(TargetLine[] lines) {
        double sum = 0;
        for (TargetLine line : lines) {
            sum += line.getLength();
        }
        return sum;
    }
}
