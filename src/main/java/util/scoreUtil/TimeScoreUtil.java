package util.scoreUtil;

import targetModel.Sketch;
import targetModel.TargetCircle;
import targetModel.TargetLine;
import util.CommomUtil;

public class TimeScoreUtil {
    private static final double STROKE_COST = 1.0;// 笔划系数
    private static final double LINE_COST = 1.0;// 直线系数（横/竖）
    private static final double SLASH_COST = 1.0;// 斜线系数
    private static final double SMALL_BOW_COST = 1.0;// 劣弧系数
    private static final double BIG_BOW_COST = 1.0;// 优弧及圆系数

    public double getLineCost(TargetLine line) {
        if (line.isVertical() || CommomUtil.equals(line.k, 0)) {
            return STROKE_COST * 1 + line.getLength() * LINE_COST;
        } else {
            return STROKE_COST * 1 + line.getLength() * SLASH_COST;
        }
    }

    public double getCircleCost(TargetCircle circle) {
        if (circle.isMoreThanHalf()) {
            return STROKE_COST * 1 + circle.getR() * BIG_BOW_COST;
        } else {
            return STROKE_COST * 1 + circle.getR() * SMALL_BOW_COST;
        }
    }

    public double getRealCost(String pid) {
        Sketch sketch = Sketch.getSketch(pid);
        int lastStrokeIndex = sketch.strokes.size() - 1;
        int lastDotIndex = sketch.strokes.get(lastStrokeIndex).dots.size() - 1;
        return sketch.strokes.get(lastStrokeIndex).dots.get(lastDotIndex).getT();
    }

    public double getLineTimeScore(String pid, TargetLine[] lines) {
        double standardTime = 0.0;
        for(TargetLine line : lines) {
            standardTime += getLineCost(line);
        }
        double realTime = getRealCost(pid);
        return getTimeScore(standardTime, realTime);
    }

    public double getCircleTimeScore(String pid, TargetCircle circle) {
        double standardTime = getCircleCost(circle);
        double realTime = getRealCost(pid);
        return getTimeScore(standardTime, realTime);
    }

    public double getTimeScore(double standardTime, double realTime) {
        return standardTime > realTime ? 100.00 : 100.00 * (realTime/standardTime);
    }

}
