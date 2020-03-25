// 主要根据超出绘制的部分在总绘制部分的占比
import {Bow, Dot, Line, Sketch, Stroke} from "./Line";
import {getDistanceSum} from "./lack"
import {isBowMarked, isLineMarked, markBow, markLine, setupBowMark, setupLineMark} from "./mark";

export function getExcessiveLine(sketch : Sketch, lines : Line[]) : number {
    setupLineMark(lines.length);
    let standardDistance = getDistanceSum(lines);
    let excessiveDistance = 0.0;
    // 计算超出部分的长度
    for (let stroke in sketch.strokes) {
        for (let i = 0;i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let dot1 = (<Stroke><unknown>stroke).dots[i];
            let dot2 = (<Stroke><unknown>stroke).dots[i + 1];
            excessiveDistance += getExcessiveDistanceLine(dot1, dot2, lines);
        }
    }
    return 100 * (1 - excessiveDistance / excessiveDistance + standardDistance);
}

function getExcessiveDistanceLine(dot1 : Dot, dot2 : Dot, lines : Line[]) : number{
    let distance1 = dot1.getDistanceFromLine(lines[0]);
    let target = lines[0];
    let index = 0;
    for (let i = 0; i < lines.length; i++) {
        let distance = dot1.getDistanceFromLine(<Line><unknown>lines[i]);
        if (distance < distance1) {
            distance1 = distance;
            target = <Line><unknown>lines[i];
            index = i;
        }
    }
    let distance2 = dot2.getDistanceFromLine(target);
    let tolerance = target.getToleranceZoneWidth();
    let ratio = 0.5;
    if (distance1 <= tolerance && distance2 <=  tolerance) {
        ratio = 0;
    } else if (distance1 > tolerance && distance2 >  tolerance) {
        ratio = 1;
    }
    if (isLineMarked(index, dot1, dot2)) {
        ratio = 1;
    } else {
        markLine(index, dot1, dot2);
    }
    if (ratio == 0) {
        return 0;
    } else {
        let distance = dot1.getDistance(dot2);
        let cosTheta = Math.cos(target.angle);
        let sinTheta = Math.sin(target.angle);
        let delta1 = dot1.x * cosTheta + dot1.y * sinTheta - target.distance;
        let delta2 = dot2.x * cosTheta + dot2.y * sinTheta - target.distance;
        // 如果异侧，构造镜像点
        if (Math.abs(delta1) + Math.abs(delta2) > Math.abs(delta1 + delta2)) {
            // 获取镜像点
            let mirrorDot = target.getMirrorDot(dot2)
            distance = dot1.getDistance(mirrorDot);
        }
        return ratio * distance;
    }
}

export function getExcessiveBow(sketch : Sketch, bow : Bow) : number{
    setupBowMark();
    let standardDistance = bow.getLength();
    let excessiveDistance = 0.0;
    // 计算超出部分的长度
    for (let stroke in sketch.strokes) {
        for (let i = 0;i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let dot1 = (<Stroke><unknown>stroke).dots[i];
            let dot2 = (<Stroke><unknown>stroke).dots[i + 1];
            excessiveDistance += getExcessiveDistanceBow(dot1, dot2, bow)
        }
    }
    return 100 * (1 - excessiveDistance / excessiveDistance + standardDistance);
}

function getExcessiveDistanceBow(dot1 : Dot, dot2 : Dot, bow : Bow) : number{
    let distance1 = Math.abs(dot1.getDistance(bow.o) - bow.r);
    let distance2 = Math.abs(dot2.getDistance(bow.o) - bow.r);
    let tolerance = bow.getToleranceZoneWidth();
    let ratio = 0.5;
    if (distance1 <= tolerance && distance2 <=  tolerance) {
        ratio = 0;
    } else if (distance1 > tolerance && distance2 >  tolerance) {
        ratio = 1;
    }

    if (isBowMarked(bow.getAngleX(dot1), bow.getAngleX(dot2))) {
        ratio = 1;
    } else {
        markBow(bow.getAngleX(dot1), bow.getAngleX(dot2));
    }
    if (ratio == 0) {
        return 0;
    } else {
        let distance = dot1.getDistance(dot2);
        let delta1 = dot1.getDistance(bow.o) - bow.r;
        let delta2 = dot2.getDistance(bow.o) - bow.r;
        // 如果异侧，构造镜像点
        if (Math.abs(delta1) + Math.abs(delta2) > Math.abs(delta1 + delta2)) {
            // 获取镜像点
            let mirrorDot = bow.getMirrorDot(dot2)
            distance = dot1.getDistance(mirrorDot);
        }
        return ratio * distance;
    }
}