import {Bow, Line, Sketch} from "./Line";

const gap_time = 500;// 0.5s的间隔
const stroke_time = 100;// 一笔至少要若干ms
const length_ratio = 1;// 长度系数
const circle_time = 500;// 一个圆至少要若干ms
const circle_ratio = 1.5;// 长度系数

export function getTimeLine(sketch : Sketch, lines : Line[]) {
    let targetTime = getTargetTimeLine(lines);
    let realTime = getRealTime(sketch);
    if (realTime <= targetTime) {
        return 100;
    } else {
        return 100 * (targetTime / realTime);
    }
}

export function getTimeBow(sketch : Sketch, bow : Bow) {
    let targetTime = getTargetTimeCircle(bow);
    let realTime = getRealTime(sketch);
    if (realTime <= targetTime) {
        return 100;
    } else {
        return 100 * (targetTime / realTime);
    }
}

function getTargetTimeLine(lines : Line[]) : number {
    let cost = 0;
    cost += (lines.length - 1) * gap_time;
    cost += lines.length * stroke_time;
    lines.forEach(r => cost += r.getLength() * length_ratio);
    return cost;
}

function getTargetTimeCircle(bow : Bow) {
    return circle_time + bow.getLength() * circle_ratio;
}

function getRealTime(sketch : Sketch) : number {
    let index1 = sketch.strokes.length - 1;
    let index2 = sketch.strokes[index1].dots.length - 1;
    return  sketch.strokes[index1].dots[index2].time - sketch.strokes[0].dots[0].time;
}