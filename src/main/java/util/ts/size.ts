import {Bow, Line, Sketch} from "./Line";

export function getSizeLine(sketch : Sketch, lines : Line[]) : number {
    let standardSquare = getStandardSquare(lines);
    let realSquare =  sketch.getStandardSquare();
    // 计算得分
    return 100 * (realSquare < standardSquare ? realSquare / standardSquare : standardSquare / realSquare)
}

export function getSizeBow(sketch : Sketch, bow : Bow) : number {
    let standardSquare = bow.getStandardSquare();
    let realSquare =  sketch.getStandardSquare();
    // 计算得分
    return 100 * (realSquare < standardSquare ? realSquare / standardSquare : standardSquare / realSquare)
}

// 获取最小矩形
function getStandardSquare(lines : Line[]) : number{
    let left = (lines[0].start).x;
    let right = (lines[0].start).x;
    let up = (lines[0].start).y;
    let down = (lines[0].start).y;
    for (let line in lines) {
        let x = (<Line><unknown>line).start.x;
        let y = (<Line><unknown>line).start.y;
        if (x < left) {
            left = x;
        } else if (x > right) {
            right = x;
        }

        if (y < down) {
            down = y;
        } else if (y > up) {
            up = y;
        }

        x = (<Line><unknown>line).end.x;
        y = (<Line><unknown>line).end.y;
        if (x < left) {
            left = x;
        } else if (x > right) {
            right = x;
        }

        if (y < down) {
            down = y;
        } else if (y > up) {
            up = y;
        }
    }
    return (right - left) * (up - down);
}