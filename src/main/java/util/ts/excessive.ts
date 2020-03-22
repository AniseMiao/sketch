// 主要根据占有的角度计算结果，找出每段在范围内的占有角
import {Bow, Dot, Line, Sketch, Stroke} from "./Line";
import {getDistanceSum} from "./lack"
const distance_tolerance = 5;//
export function getExcessiveBow(sketch : Sketch, bow : Bow) : number{
    let standardDistance = bow.getLength();
    let realDistance = 0.0;
    // 对于每条线，找出所有不在范围内的角度值
    for (let stroke in sketch.strokes) {
        for (let i = 0;i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let dot1 = (<Stroke><unknown>stroke).dots[i];
            let dot2 = (<Stroke><unknown>stroke).dots[i + 1];
            let distance1 = Math.abs(dot1.getDistance(bow.o) - bow.r);
            let distance2 = Math.abs(dot2.getDistance(bow.o) - bow.r);
            if (distance1 > distance_tolerance && distance2 > distance_tolerance) {
                realDistance += dot1.getDistance(dot2);
            } else if(distance1 <= distance_tolerance || distance2 <= distance_tolerance) {
                realDistance += 0.5 * dot1.getDistance(dot2);
            }
        }
    }
    return 100 * (realDistance / (realDistance + standardDistance));
}



function getMinDistance(dot : Dot, lines : Line[]) {
    let min = dot.getDistanceFromLine(lines[0]);
    for (let line in lines) {
        let distance = dot.getDistanceFromLine(<Line><unknown>line);
        if (distance < min) {
            min = distance;
        }
    }
    return min;
}

export function getExcessiveLine(sketch : Sketch, lines : Line[]) : number {
    let standardDistance = getDistanceSum(lines);
    let excessiveDistance = 0.0;
    // 对于每条线，找出所有不在范围内的角度值
    for (let stroke in sketch.strokes) {
        for (let i = 0;i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let dot1 = (<Stroke><unknown>stroke).dots[i];
            let dot2 = (<Stroke><unknown>stroke).dots[i + 1];
            let distance1 = Math.abs(getMinDistance(dot1, lines));
            let distance2 = Math.abs(getMinDistance(dot2, lines));
            if (distance1 > distance_tolerance && distance2 > distance_tolerance) {
                excessiveDistance += dot1.getDistance(dot2);
            } else if(distance1 <= distance_tolerance || distance2 <= distance_tolerance) {
                excessiveDistance += 0.5 * dot1.getDistance(dot2);
            }
        }
    }
    return 100 * (1 - excessiveDistance / excessiveDistance + standardDistance);
}