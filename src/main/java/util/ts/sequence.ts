import {Bow, Dot, Line, Sketch, Stroke} from "./Line";

export function getSequenceBow(sketch : Sketch, bow : Bow) {
    return 100 / sketch.strokes.length;
    /*
    if (sketch.strokes.length == 1) {
        return 100.00;
    } else {
        let realAngle2 = 0.0;
        for (let stroke in sketch.strokes) {
            let angle = getSimilarAngle(<Stroke><unknown>stroke, bow.o);
            realAngle2 += (angle * angle);
        }
        return 100.00 * (realAngle2 / (bow.getAngleO() * bow.getAngleO()));
    }
     */

}

function getSimilarAngle(stroke : Stroke, dot : Dot) {
    let start = stroke.dots[0];
    let end = stroke.dots[stroke.dots.length - 1];
    return dot.getAngle(start, end);
}

// 计算直线的顺序分数
export function getSequenceLine(sketch : Sketch, lines : Line[]) : number{
    let sequence = new Number[lines.length];
    let num = new Number[lines.length];
    for (let i = 0; i < num.length; i++) { num[i] = 0; }
    for (let i = 0; i < sketch.strokes.length; i++) {
        let index = getSimilarSequence(sketch.strokes[i], lines);
        num[index]++;
        sequence[index] += Math.abs(i - index);
    }
    let ratio1 = 0;
    let ratio2 = 0;
    for (let i = 0; i < sequence.length; i++){
        if (num[i] != 0) {
            ratio1 += (1 / (1.0 * num[i]));
        }
        ratio2 += sequence[i];
    }
    ratio1 /= lines.length;
    ratio2 /= ((sketch.strokes.length * sketch.strokes.length) / 2 );
    return 100 * ratio1 * ratio2;
}

function getSimilarSequence(stroke : Stroke, lines : Line[]) : number{
    // 求起始点及3个4等分点，计算他们对于某条线的距离之和，最小的那个就是最接近的点。
    let index = 0;
    let min = getFakeDistance(stroke, lines[0]);
    for (let i = 1; i < lines.length; i++) {
        let newDistance = getFakeDistance(stroke, lines[i]);
        if (newDistance < min) {
            min = newDistance;
            index = i;
        }
    }
    return index;
}

function getFakeDistance(stroke : Stroke, line : Line) {
    let sum = 0.0;
    let length = stroke.dots.length;
    let dot1 = stroke.dots[0];
    let dot2 = stroke.dots[(length - 1) / 4];
    let dot3 = stroke.dots[(length - 1) / 2];
    let dot4 = stroke.dots[3 * (length - 1)/ 4];
    let dot5 = stroke.dots[length - 1];
    sum += dot1.getDistanceFromLine(line);
    sum += dot2.getDistanceFromLine(line);
    sum += dot3.getDistanceFromLine(line);
    sum += dot4.getDistanceFromLine(line);
    sum += dot5.getDistanceFromLine(line);
    return sum;
}
