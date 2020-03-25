// 用于标记
import {Dot, Line} from "./Line";

// 用于标记已计算的线段
class DotPair{
    start : Dot;
    end : Dot;

    contains (dot : Dot) {
        let x = (dot.x <= this.start.x && dot.x >= this.end.x) ||
            (dot.x >= this.start.x && dot.x <= this.end.x);
        let y = (dot.y <= this.start.y && dot.y >= this.end.y) ||
            (dot.y >= this.start.y && dot.y <= this.end.y);
        return x && y;
    }

    constructor(start : Dot, end : Dot) {
        this.start = start;
        this.end = end;
    }
}

// 每条线对应一个标记数组
class LineMark{
    pairs : DotPair[];
}

let lineMarks = [];
// 初始化
export function setupLineMark(num : number) {
    lineMarks = new LineMark[num];
    for (let i = 0; i < num; i++) {
        (<LineMark><unknown>lineMarks[i]).pairs = new DotPair[0];
    }
}

//某线的某部分是否已被标记
export function isLineMarked(index : number, dot1 : Dot, dot2 : Dot) {
    for (let dotPair in (<LineMark><unknown>lineMarks[index]).pairs) {
        if ((<DotPair><unknown>dotPair).contains(dot1) || (<DotPair><unknown>dotPair).contains(dot2)) {
            return true;
        }
    }
    return false
}

//标记某线的某部分
export function markLine (index : number, dot1 : Dot, dot2 : Dot) {
    (<LineMark><unknown>lineMarks[index]).pairs.concat(new DotPair(dot1, dot2));
}

// 用于标记已计算的角度
class AngleMark {
    start : number;
    end : number;

    // 计算夹角
    contains (num : number) {
        return (this.start <= num && num <= this.end) || (this.end <= num && num <= this.start) ;
    }

    constructor(start : number, end : number) {
        this.start = start;
        this.end = end;
    }
}

let bowMark = [];
// 初始化
export function setupBowMark() {
    bowMark = new AngleMark[0];
}

// 某段夹角范围是否已被标记
export function isBowMarked(num1 : number, num2 : number) {
    for (let item in bowMark) {
        if ((<AngleMark><unknown>item).contains(num1) || (<AngleMark><unknown>item).contains(num2)) {
            return true;
        }
    }
    return false
}


// 标记某段夹角范围
export function markBow (num1 : number, num2 : number) {
    bowMark.concat(new AngleMark(num1, num2));
}
