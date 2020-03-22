import {Sketch, Stroke} from "./Line";

export function getContinuity(sketch : Sketch) : number {
    // 相邻两点的距离差与时间差之比为速度，速度的标准差作为分数的基本判断
    let speedSum = 0.0;
    let speeds = new Number[sketch.getDotsNumber() - sketch.strokes.length];
    for (let stroke in sketch.strokes) {
        for (let i = 0; i < (<Stroke><unknown>stroke).dots.length - 1; i++) {
            let distanceInterval = (<Stroke><unknown>stroke).dots[i].getDistance((<Stroke><unknown>stroke).dots[i + 1]);
            let timeInterval = (<Stroke><unknown>stroke).dots[i + 1].time - (<Stroke><unknown>stroke).dots[i].time;
            let brakingSpeed = distanceInterval / timeInterval;
            speedSum += brakingSpeed;
            speeds.add(speedSum);
        }
    }
    // 求标准差 总体公式
    let average = speedSum / speeds.size();
    let s2 = 0.0;
    for (let num in speeds) {
        s2 += ((<number><unknown>num - average) * (<number><unknown>num - average));
    }
    let standard = Math.sqrt(s2 / speeds.size());
    return 100.00 * ( 1 - standard/(standard + average));
}
