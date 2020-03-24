import {Bow, Dot, Line, Sketch, Stroke} from "./Line";
import {equals} from "./common";

// 倾斜角度的加权平均值作为平滑度依据，越接近pi，说明抖动越大
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

// 倾斜角度的加权平均值作为平滑度依据，越接近pi，说明抖动越大
// 补充说明，这里的夹角是以点A，点B到圆心O的连线上某点，点B三点中以点A为中心的夹角，
// 如果足够圆滑，夹角应为0
export function getSmoothnessBow(sketch : Sketch, bow : Bow) {
    let angleBias = 0;
    let angleSum = 0;
    let o = bow.o;
    for(let stroke in sketch.strokes) {
        for (let i = 0; i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let centerDot = (<Stroke><unknown>stroke).dots[i];
            let dot1 = (<Stroke><unknown>stroke).dots[i + 1];
            let angle = bow.getAngleX(dot1);
            let x = o.x + bow.o.getDistance(centerDot) * Math.cos(angle);
            let y = o.y + bow.o.getDistance(centerDot) * Math.sin(angle);
            let dot2 = new Dot(x,y);// o - dot1线上的标准点
            angleBias += o.getAngle(dot1, dot2);
            angleSum += o.getAngle(centerDot, dot1);
            angleBias += angle * angleSum;
        }
    }
    let average = angleBias / angleSum;
    return 100 * (1 - average / Math.PI);
}