package util.scoreUtil;

import targetModel.*;
import util.PictureUtil;

import java.util.ArrayList;
import java.util.Collections;

public class NumScoreUtil {

    private static final double DISTANCE_TOLERANCE = 1.0;
    private static final double ANGLE_TOLERANCE = 1.0;

    // 计算笔数的得分（断断续续的线条如果彼此可以相接，差距极小且斜率大致相当情况下，
    // 会被识别为一条线，但会扣除一定分数，除此之外，如果出现笔数不同，会进行相应程度的扣分
    public double getLineNumScore(String pid, TargetLine[] lines) {
        Sketch sketch = PictureUtil.getSketch(pid);
        int standard = lines.length;
        int real = sketch.getStrokes().size();
        Sketch modifySketch = combineLines(sketch);
        int modify = modifySketch.getStrokes().size();
        if (standard == real && real == modify) {
            return 100.00;
        } else {
            return 100 * (1.0 * modify * standard) / (1.0 * real * real);
        }
    }

    public double getCircleNumScore(String pid, TargetCircle circle) {
        Sketch sketch = PictureUtil.getSketch(pid);
        int standard = 1;
        int real = sketch.getStrokes().size();
        Sketch modifySketch = combineBows(sketch, circle);
        int modify = modifySketch.getStrokes().size();
        if (standard == real && real == modify) {
            return 100.00;
        } else {
            return 100 * (1.0 * modify * standard) / (1.0 * real * real);
        }
    }

    public Sketch combineBows(Sketch sketch, TargetCircle circle) {
        ArrayList<Stroke> strokes = sketch.strokes;
        if(strokes != null && strokes.size() > 1) {
            for (int i = 0; i + 1 < strokes.size(); i++ ) {
                Stroke startStroke = strokes.get(i);
                Stroke endStroke = strokes.get(i + 1);
                TargetDot endDot1 = startStroke.dots.get(startStroke.dots.size() - 1);
                TargetDot startDot2 = endStroke.dots.get(0);
                TargetDot endDot2= endStroke.dots.get(endStroke.dots.size() - 1);
                TargetDot l1d2 = new TargetDot(endDot1.getX(), endDot1.getY());
                TargetDot l2d1 = new TargetDot(startDot2.getX(), startDot2.getY());
                TargetDot l2d2 = new TargetDot(endDot2.getX(), endDot2.getY());
                if(circle.canLink(l1d2, l2d1, l2d2, DISTANCE_TOLERANCE)) {
                    // 将l2放入l1，去掉l2，计数--
                    startStroke.dots.addAll(endStroke.dots);
                    startStroke.timeStamps.addAll(endStroke.timeStamps);
                    strokes.remove(i + 1);
                    i--;
                } else if(circle.canLink(l1d2, l2d2, l2d1, DISTANCE_TOLERANCE)) {
                    // 将l2倒置放入l1， 去掉l2，计数--
                    Collections.reverse(startStroke.dots);
                    Collections.reverse(endStroke.timeStamps);
                    startStroke.dots.addAll(endStroke.dots);
                    startStroke.timeStamps.addAll(endStroke.timeStamps);
                    i--;
                }
            }
            sketch.setStrokes(strokes);
        }
        return sketch;
    }

    public Sketch combineLines(Sketch sketch) {
        ArrayList<Stroke> strokes = sketch.strokes;
        if(strokes != null && strokes.size() > 1) {
            for (int i = 0; i + 1 < strokes.size(); i++ ) {
                Stroke startStroke = strokes.get(i);
                Stroke endStroke = strokes.get(i + 1);
                TargetDot endDot1 = startStroke.dots.get(startStroke.dots.size() - 1);
                TargetDot startDot2 = endStroke.dots.get(0);
                TargetDot endDot2= endStroke.dots.get(endStroke.dots.size() - 1);
                TargetDot l1d2 = new TargetDot(endDot1.getX(), endDot1.getY());
                TargetDot l2d1 = new TargetDot(startDot2.getX(), startDot2.getY());
                TargetDot l2d2 = new TargetDot(endDot2.getX(), endDot2.getY());
                if(TargetDot.getDistance(l1d2, l2d1) <= DISTANCE_TOLERANCE && canLink(startStroke, endStroke)) {
                    // 将l2放入l1，去掉l2，计数--
                    startStroke.dots.addAll(endStroke.dots);
                    startStroke.timeStamps.addAll(endStroke.timeStamps);
                    strokes.remove(i + 1);
                    i--;
                } else {
                    Collections.reverse(endStroke.dots);
                    if(TargetDot.getDistance(l1d2, l2d2) <= DISTANCE_TOLERANCE && canLink(startStroke, endStroke)) {
                        // 将l2倒置放入l1， 去掉l2，计数--
                        Collections.reverse(endStroke.timeStamps);
                        startStroke.dots.addAll(endStroke.dots);
                        startStroke.timeStamps.addAll(endStroke.timeStamps);
                        i--;
                    } else {
                        Collections.reverse(endStroke.dots);
                    }
                }
            }
            sketch.setStrokes(strokes);
        }
        return sketch;
    }

    public boolean canLink(Stroke stroke1, Stroke stroke2) {
        TargetLine targetLine1 = stroke1.toTargetLine();
        TargetLine targetLine2 = stroke1.toTargetLine();
        double angle1, angle2;

        if (targetLine1.isVertical()) {
            angle1 = Math.PI / 2;
        } else {
            angle1 = Math.atan(targetLine1.k);
        }
        if (targetLine2.isVertical()) {
            angle2 = Math.PI / 2;
        } else {
            angle2 = Math.atan(targetLine2.k);
        }

        if(Math.abs(angle1 - angle2) > ANGLE_TOLERANCE) {
            return false;
        }

        if (targetLine1.isVertical()) {
            if (targetLine2.isVertical()) {
                return Math.abs(targetLine1.c - targetLine2.c) <= DISTANCE_TOLERANCE;
            } else {
                double middleY = (targetLine2.start.y + targetLine2.end.y) / 2;
                return Math.abs(targetLine1.c - middleY) <= DISTANCE_TOLERANCE;
            }
        } else {
            if (targetLine2.isVertical()) {
                double middleY = (targetLine1.start.y + targetLine1.end.y) / 2;
                return Math.abs(targetLine2.c - middleY) <= DISTANCE_TOLERANCE;
            } else {
                return Math.abs(targetLine1.b - targetLine2.b) <= DISTANCE_TOLERANCE;
            }
        }
    }

}
