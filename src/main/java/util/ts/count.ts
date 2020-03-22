import {Bow, Dot, Line, Sketch, Stroke} from "./Line";

const distance_tolerance = 5.0;// 5个单位抖动
const angle_tolerance = Math.PI / 180; // 1°
const time_tolerance = 500;// 0.5s

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

function getModifyCountBow(sketch : Sketch, bow : Bow) : number{
    let strokes = sketch.strokes;
    if(strokes != null && strokes.length > 1) {
        for (let i = 0; i + 1 < strokes.length; i++ ) {
            let startStroke = strokes[i];
            let endStroke = strokes[i + 1];
            let endDot1 = startStroke.dots[startStroke.dots.length - 1];
            let startDot2 = endStroke.dots[0];
            let endDot2 = endStroke.dots[endStroke.dots.length - 1];
            let l1d2 = new Dot(endDot1.x, endDot1.y);
            let l2d1 = new Dot(startDot2.x, startDot2.y);
            let l2d2 = new Dot(endDot2.x, endDot2.y);
            if(Math.abs(l1d2.time - l2d1.time) < time_tolerance && bow.canLink(l1d2, l2d1, l2d2, distance_tolerance)) {
                // 将l2放入l1，去掉l2，计数--
                startStroke.dots.concat(endStroke.dots)
                strokes.splice(i + 1, 1);
                i--;
            } else if(Math.abs(l1d2.time - l2d2.time) < time_tolerance && bow.canLink(l1d2, l2d2, l2d1, distance_tolerance)) {
                // 将l2 倒置放入l1， 去掉l2，计数--
                endStroke.dots.reverse();
                startStroke.dots.concat(endStroke.dots)
                strokes.splice(i + 1, 1);
                i--;
            }
        }
    }
    return strokes.length;
}

function getModifyCountLine(sketch : Sketch) : number{
    let strokes = sketch.strokes;
    if(strokes != null && strokes.length > 1) {
        for (let i = 0; i + 1 < strokes.length; i++ ) {
            let startStroke = strokes[i];
            let endStroke = strokes[i + 1];
            let endDot1 = startStroke.dots[startStroke.dots.length - 1];
            let startDot2 = endStroke.dots[0];
            let endDot2 = endStroke.dots[endStroke.dots.length - 1];
            let l1d2 = new Dot(endDot1.x, endDot1.y);
            let l2d1 = new Dot(startDot2.x, startDot2.y);
            let l2d2 = new Dot(endDot2.x, endDot2.y);
            if(Math.abs(l1d2.time - l2d1.time) < time_tolerance && l1d2.getDistance(l2d1) <= distance_tolerance && canLink(startStroke, endStroke)) {
                // 将l2放入l1，去掉l2，计数--
                startStroke.dots.concat(endStroke.dots);
                strokes.splice(i + 1, 1);
                i--;
            } else {
                endStroke.dots.reverse();
                if(Math.abs(l1d2.time - l2d2.time) < time_tolerance && l1d2.getDistance(l2d2) <= distance_tolerance && canLink(startStroke, endStroke)) {
                    // 将l2倒置放入l1， 去掉l2，计数--
                    startStroke.dots.concat(endStroke.dots);
                    strokes.splice(i + 1, 1);
                    i--;
                } else {
                    endStroke.dots.reverse();
                }
            }
        }
    }
    return strokes.length;
}

function canLink(stroke1 : Stroke, stroke2 : Stroke) : boolean{
    let targetLine1 = stroke1.getLine();
    let targetLine2 = stroke2.getLine();
    return Math.abs(targetLine1.angle - targetLine2.angle) <= angle_tolerance && Math.abs(targetLine1.distance - targetLine2.distance) <= distance_tolerance
}
