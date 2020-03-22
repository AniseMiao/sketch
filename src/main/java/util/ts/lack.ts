// 主要根据占有的角度计算结果，找出每段在范围内的占有角
import {Bow, Dot, Line, Sketch, Stroke} from "./Line";

const distance_tolerance = 5; //

export function getLackBow(sketch : Sketch, bow : Bow) : number {
    let standardAngle = bow.getAngleO();
    let realAngle = 0;
    // 对于每条线，找出所有不在范围内的角度值
    let dots1 = new Dot[sketch.strokes.length];
    let dots2 = new Dot[sketch.strokes.length];
    for (let stroke in sketch.strokes) {
        dots1.add((<Stroke><unknown>stroke).dots[0]);
        dots2.add((<Stroke><unknown>stroke)[(<Stroke><unknown>stroke).dots.length - 1]);
        for (let i = 0;i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let dot1 = (<Stroke><unknown>stroke).dots[i];
            let dot2 = (<Stroke><unknown>stroke).dots[i + 1];
            let distance1 = Math.abs(dot1.getDistance(bow.o) - bow.r);
            let distance2 = Math.abs(dot2.getDistance(bow.o) - bow.r);
            if (distance1 > distance_tolerance && distance2 > distance_tolerance) {
                realAngle += bow.o.getAngle(dot1, dot2);
            } else if(distance1 <= distance_tolerance || distance2 <= distance_tolerance) {
                realAngle += 0.5 * bow.o.getAngle(dot1, dot2);
            }
        }
    }

    let simulateAngles = new Number[(dots1.length - 1) * dots1.length / 2];
    for (let i = 0; i < dots1.length; i++) {
        for (let j = 0; j < i; j++) {
            if (i != j) {
                simulateAngles.add(bow.o.getAngle(dots1[i], dots2[j]));
            }
        }
    }
    simulateAngles.sort();
    for (let i = 0; i < dots1.size - 1; i++) {
        realAngle += simulateAngles.get(i);
    }
    return 100 * (1 - realAngle / standardAngle);
}


export function getLackLine(sketch : Sketch, lines : Line[]) : number{
    let standardDistance = getDistanceSum(lines);
    let realDistance = 0.0;
    // 对于每条线，找出所有不在范围内的角度值
    for (let stroke in sketch.strokes) {
        for (let i = 0;i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let dot1 = (<Stroke><unknown>stroke).dots[i];
            let dot2 = (<Stroke><unknown>stroke).dots[i + 1];
            let line = getSimilarLine(dot1, lines);
            let distance1 = dot1.getDistanceFromLine(line);
            let distance2 = dot2.getDistanceFromLine(line);
            if (distance1 > distance_tolerance && distance2 > distance_tolerance) {
                realDistance += Math.abs(line.getLength() * (dot1.x - dot2.x)/(line.start.x, line.end.x));
            } else if(distance1 <= distance_tolerance || distance2 <= distance_tolerance) {
                realDistance += 0.5 * Math.abs(line.getLength() * (dot1.x - dot2.x)/(line.start.x, line.end.x));
            }
        }
    }
    return 100 * (1 - realDistance / standardDistance);
}

export function getDistanceSum(lines : Line[]) : number{
    let sum = 0;
    for (let line in lines) {
        sum += (<Line><unknown>line).getLength();
    }
    return sum;
}

function getSimilarLine(dot : Dot, lines : Line[]) : Line{
    let ret = lines[0];
    let min = dot.getDistanceFromLine(ret);
    for (let line in lines) {
        let distance = dot.getDistanceFromLine(<Line><unknown>line);
        if (distance < min) {
            min = distance;
            ret = <Line><unknown>line;
        }
    }
    return ret;
}

