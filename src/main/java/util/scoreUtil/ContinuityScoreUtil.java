package util.scoreUtil;

import targetModel.Sketch;
import targetModel.Stroke;
import targetModel.TargetDot;

import java.util.ArrayList;

public class ContinuityScoreUtil {

    public double getContinuityScore(String pid) {
        Sketch sketch = Sketch.getSketch(pid);
        // 相邻两点的距离差与时间差之比为速度，速度的标准差作为分数的基本判断
        double speedSum = 0.0;
        ArrayList<Double> speeds = new ArrayList<>();
        for (Stroke stroke : sketch.strokes) {
            for (int i = 0; i < stroke.dots.size() - 1; i++) {
                double distanceInterval = TargetDot.getDistance(stroke.dots.get(i), stroke.dots.get(i + 1));
                double timeInterval = stroke.timeStamps.get(i + 1) - stroke.timeStamps.get(i);
                double brakingSpeed = distanceInterval / timeInterval;
                speedSum += brakingSpeed;
                speeds.add(speedSum);
            }
        }
        // 求标准差 总体公式
        double average = speedSum / speeds.size();
        double s2 = 0.0;
        for (Double num : speeds) {
            s2 += ((num - average) * (num - average));
        }
        double standard = Math.sqrt(s2 / speeds.size());
        return 100.00 * ( 1 - standard/(standard + average));
    }

    public double getContinuityCircleScore(String pid) {
        return getContinuityScore(pid);
    }

    public double getContinuityLineScore(String pid) {
        return getContinuityScore(pid);
    }
}
