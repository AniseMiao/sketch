// 主要根据占有的角度计算结果，找出每段在范围内的占有角
import {Bow, Dot, Line, Sketch, Stroke} from "./Line";
import {isBowMarked, isLineMarked, markBow, markLine, setupBowMark, setupLineMark} from "./mark";

export function getLackBow(sketch : Sketch, bow : Bow) : number {
    setupBowMark();
    let standardAngle = bow.getAngleO();
    let realAngle = 0;
    // 对于每条线，找出所有实际包括的角度值
    for (let stroke in sketch.strokes) {
        for (let i = 0;i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let dot1 = (<Stroke><unknown>stroke).dots[i];
            let dot2 = (<Stroke><unknown>stroke).dots[i + 1];
            realAngle += getRealAngleBow(dot1, dot2, bow);
        }
    }
    return 100 * (1 - realAngle / standardAngle);
}

function getRealAngleBow(dot1 : Dot, dot2 : Dot, bow : Bow) : number{
    let distance1 = Math.abs(dot1.getDistance(bow.o) - bow.r);
    let distance2 = Math.abs(dot2.getDistance(bow.o) - bow.r);
    let tolerance = bow.getToleranceZoneWidth();
    let ratio = 0.5;
    if (distance1 <= tolerance && distance2 <=  tolerance) {
        ratio = 0;
    } else if (distance1 > tolerance && distance2 >  tolerance) {
        ratio = 1;
    }
    if (ratio == 0) {
        return 0;
    } else {// 计算两点间的夹角
        if (ratio == 1) {// 如果实际绘制部分已被标记过，那么不再次计算
            if (isBowMarked(bow.getAngleX(dot1), bow.getAngleX(dot2))) {
                return 0;
            } else {
                markBow(bow.getAngleX(dot1), bow.getAngleX(dot2));
            }
        }
        let angle = bow.o.getAngle(dot1, dot2);
        return ratio * angle;
    }
}

export function getLackLine(sketch : Sketch, lines : Line[]) : number{
    setupLineMark(lines.length);
    let standardDistance = getDistanceSum(lines);
    let realDistance = 0.0;
    // 对于每条线，找出实际绘制的部分
    for (let stroke in sketch.strokes) {
        for (let i = 0;i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let dot1 = (<Stroke><unknown>stroke).dots[i];
            let dot2 = (<Stroke><unknown>stroke).dots[i + 1];
            realDistance += getRealDistanceLine(dot1, dot2, lines);
        }
    }
    return 100 * (1 - realDistance / standardDistance);
}

function getRealDistanceLine(dot1 : Dot, dot2 : Dot, lines : Line[]) : number{
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
        ratio = 1;
    } else if (distance1 > tolerance && distance2 >  tolerance) {
        ratio = 0;
    }
    if (ratio == 0) {
        return 0;
    } else {// 计算两点间的距离（映射到同侧）
        if (ratio == 1) {// 如果实际绘制部分已被标记过，那么不再次计算
            if (isLineMarked(index, dot1, dot2)) {
                return 0;
            } else {
                markLine(index, dot1, dot2);
            }
        }
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

export function getDistanceSum(lines : Line[]) : number{
    let sum = 0;
    for (let line in lines) {
        sum += (<Line><unknown>line).getLength();
    }
    return sum;
}



