package util.scoreUtil;

import targetModel.Sketch;
import targetModel.TargetCircle;
import targetModel.TargetLine;

public class SizeScoreUtil {

    public double getLineSizeScore(String pid, TargetLine[] lines) {
        // 计算标准面积
        double standSquare = getStandSquare(lines);
        // 计算实际面积
        double realSquare =  Sketch.getSketch(pid).getStandardSquare();
        // 计算得分
        if( realSquare < standSquare) {
            return 100 * (realSquare / standSquare);
        } else {
            return 100 * (standSquare/ realSquare);
        }
    }

    public double getCircleSizeScore(String pid, TargetCircle circle) {
        // 计算标准面积
        double standSquare = circle.getStandardSquare();
        // 计算实际面积
        double realSquare =  Sketch.getSketch(pid).getStandardSquare();
        // 计算得分
        if( realSquare < standSquare) {
            return 100 * (realSquare / standSquare);
        } else {
            return 100 * (standSquare/ realSquare);
        }
    }

    public double getStandSquare(TargetLine[] lines) {
        if(lines == null || lines.length == 0)
            return 0;
        TargetLine line = lines[0];
        double maxX = line.start.x;
        double maxY = line.start.y;
        double minX = line.start.x;
        double minY = line.start.y;
        for(int i = 0; i < lines.length; i++) {
            if (lines[i].start.x > maxX) maxX = lines[i].start.x;
            if (lines[i].start.x < minX) minX = lines[i].start.x;
            if (lines[i].end.x > maxX) maxX = lines[i].end.x;
            if (lines[i].end.x < minX) minX = lines[i].end.x;
            if (lines[i].start.y > maxY) maxY = lines[i].start.y;
            if (lines[i].start.y < minY) minY = lines[i].start.y;
            if (lines[i].end.y > maxY) maxY = lines[i].end.y;
            if (lines[i].end.y < minY) minY = lines[i].end.y;
        }
        return (maxX - minX) * (maxY - minY);
    }
}
