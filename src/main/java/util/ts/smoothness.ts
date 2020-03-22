import {Bow, Dot, Line, Sketch, Stroke} from "./Line";
import {equals} from "./common";

export function getSmoothnessLine(sketch : Sketch, lines : Line[]) {
    let angleBias = 0;
    let lengthSum = 0;
    for(let stroke in sketch.strokes) {
        let line = (<Stroke><unknown>stroke).getLine();
        for (let i = 0; i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let centerDot = (<Stroke><unknown>stroke).dots[i];
            let dot1 = (<Stroke><unknown>stroke).dots[i + 1];
            let dot2 = line.getDot(dot1.x);
            let angle =  Math.abs(centerDot.getAngle(dot1, dot2));
            lengthSum += line.getLength();
            angleBias += angle * lengthSum;
        }
    }
    let average = angleBias / lengthSum;
    return 100 * (1 - average / Math.PI);
}

export function getSmoothnessBow(sketch : Sketch, bow : Bow) {
    let angleBias = 0;
    let angleSum = 0;
    let o = bow.o;
    for(let stroke in sketch.strokes) {
        for (let i = 0; i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let centerDot = (<Stroke><unknown>stroke).dots[i];
            let dot1 = (<Stroke><unknown>stroke).dots[i + 1];
            let angle = bow.getAngleX(dot1);
            let x = o.x + bow.r * Math.cos(angle);
            let y = o.y + bow.r * Math.sin(angle);
            let dot2 = new Dot(x,y);// o - dot1线上的标准点
            angleBias += o.getAngle(dot1, dot2);
            angleSum += o.getAngle(centerDot, dot1);
            angleBias += angle * angleSum;
        }
    }
    let average = angleBias / angleSum;
    return 100 * (1 - average / Math.PI);
}