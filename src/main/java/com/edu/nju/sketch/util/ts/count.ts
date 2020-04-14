import {Bow, Dot, Line, Sketch, Stroke} from "./Line";

const angle_tolerance = Math.PI / 180; // 角度修正，一般取1度
const shake_tolerance = 5;// 抖动修正，一般取4-6，此处取5

// 用到实际所需笔数，修正笔数，标准笔数。当三者相等时，分数最高（即无需修正，就使用了与标准画法相同的笔数）
export function getCountLine(sketch : Sketch, lines : Line[]) {
    let targetCount = lines.length;
    let realCount = sketch.strokes.length;
    let modifyCount = getModifyCountLine(sketch);
    let ratio1 = targetCount > modifyCount ? modifyCount / targetCount : targetCount / modifyCount;
    let ratio2 = modifyCount / realCount;
    return 100 * ratio1 * ratio2;
}

export function getCountBow(sketch : Sketch, bow : Bow) {
    let targetCount = 1;
    let realCount = sketch.strokes.length;
    let modifyCount = getModifyCountBow(sketch, bow);
    let ratio1 = targetCount > modifyCount ? modifyCount / targetCount : targetCount / modifyCount;
    let ratio2 = modifyCount / realCount;
    return 100 * ratio1 * ratio2;
}

// 对手绘笔划进行修正（两笔间的抖动值在范围内时可以认为应当是同一笔）
function getModifyCountBow(sketch : Sketch, bow : Bow) : number{
    let strokes = sketch.strokes;
    for (let i = 0; i + 1 < strokes.length; i++ ) {
        // 笔序上连续，才认为实际可能连续。因此取连续两条线试图修正
        let startStroke = strokes[i];
        let endStroke = strokes[i + 1];
        // 后一条线的起始点或结束点与前一条线的结束点相接，认为更可能是可以修正的线条
        let dot1 = startStroke.dots[startStroke.dots.length - 1];
        let dot2 = endStroke.dots[0];
        let dot3 = endStroke.dots[endStroke.dots.length - 1];
        // 如果两弧在抖动范围内，可以相接
        if(bow.canLink(dot1, dot2, dot3, shake_tolerance)) {
            // 合并两笔
            startStroke.dots.concat(endStroke.dots)
            strokes.splice(i + 1, 1);
            i--;
        } else if(bow.canLink(dot1, dot3, dot2, shake_tolerance)) {
            endStroke.dots.reverse();
            startStroke.dots.concat(endStroke.dots)
            strokes.splice(i + 1, 1);
            i--;
        }
    }
    // 只需要最后的连接长度，所以存在优化空间（如只需将连接点加入前一笔）
    return strokes.length;
}

function getModifyCountLine(sketch : Sketch) : number{
    let strokes = sketch.strokes;
    for (let i = 0; i + 1 < strokes.length; i++ ) {
        // 笔序上连续，才认为实际可能连续。因此取连续两条线试图修正
        let startStroke = strokes[i];
        let endStroke = strokes[i + 1];
        // 后一条线的起始点或结束点与前一条线的结束点相接，认为更可能是可以修正的线条
        let dot1 = startStroke.dots[startStroke.dots.length - 1];
        let dot2 = endStroke.dots[0];
        let dot3 = endStroke.dots[endStroke.dots.length - 1];
        if(dot1.getDistance(dot2) <= shake_tolerance && canLink(startStroke, endStroke)) {
            // 将l2放入l1，去掉l2，计数--
            startStroke.dots.concat(endStroke.dots);
            strokes.splice(i + 1, 1);
            i--;
        } else {
            endStroke.dots.reverse();
            if(dot1.getDistance(dot3) <= shake_tolerance && canLink(startStroke, endStroke)) {
                // 将l2倒置放入l1， 去掉l2，计数--
                startStroke.dots.concat(endStroke.dots);
                strokes.splice(i + 1, 1);
                i--;
            } else {
                endStroke.dots.reverse();
            }
        }
    }
    return strokes.length;
}

// 两条线的相似度较高才可以认为可以连接（取决于夹角和距离）
function canLink(stroke1 : Stroke, stroke2 : Stroke) : boolean{
    let targetLine1 = stroke1.getLine();
    let targetLine2 = stroke2.getLine();
    // 容差带宽度的最小值作为距离差的容忍度（即两线都互在对方的容差带内）
    let distance_tolerance = Math.min(targetLine1.getToleranceZoneWidth(), targetLine2.getToleranceZoneWidth());
    return Math.abs(targetLine1.angle - targetLine2.angle) <= angle_tolerance && Math.abs(targetLine1.distance - targetLine2.distance) <= distance_tolerance
}
