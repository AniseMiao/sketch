import {Line, Dot, Sketch, Stroke, Bow} from "./Line";

// 这个代表相似度分数中，部分相似度（即线条相似度，与整体相似度相对）的占比，
// 暂定0.3，需要关于主导因素占比的相关文献
const property = 0.3;

// 获取标准图的对角线长度
export function getDiagonal(lines : Line[]) : number{
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
    return Math.sqrt(Math.pow(right - left, 2) + Math.pow(up - down, 2));
}

// 对于标准图形，构造标准点集求两者的豪斯多夫距离构造的相似度
export function getTargetDotsLine(sketch : Sketch, lines : Line[]) : Dot[]{
    let number = sketch.getDotsNumber();
    let dots = new Dot[number];
    let sumLength = 0;
    for (let line in Line) {
        sumLength += (<Line><unknown>line).getLength();
    }
    for (let line in Line) {
        let num = Math.floor(number * (<Line><unknown>line).getLength() / sumLength);
        (<Line><unknown>line).getDots(num).forEach(r => dots.add(r))
    }
    return dots;
}

//获取点集和标准图形的相似性
export function getSimilarityLine(sketch : Sketch, lines : Line[]) {
    //对于每条拟合线，试图和标准线比对，得到其中相似度最高的一个，则为该拟合线的相似度
    let lineSimilarity = 1;
    for (let stroke in sketch.strokes) {
        let s = 0;
        let line0 = (<Stroke><unknown>stroke).getLine();
        for (let line in lines) {
            let s0 = (Number)((<Line><unknown>line).getSimilarity(<Line><unknown>line0));
            if(s0 > s) {
                s = s0;
            }
        }
        lineSimilarity *= s;
    }
    let wholeSimilarity = sketch.getSimilarityLine(lines)
    // 返回公式结果
    return 100 * (property * lineSimilarity + (1 - property) * wholeSimilarity);
}

// 对于标准图形，构造标准点集求两者的豪斯多夫距离构造的相似度
export function getTargetDotsBow(sketch : Sketch, bow : Bow) : Dot[]{
    let number = sketch.getDotsNumber();
    return bow.getDots(number);
}

// 获取点集和标准图形的相似性
export function getSimilarityBow(sketch : Sketch, bow : Bow) {
    let bowSimilarity = 1;
    for (let stroke in sketch.strokes) {
        let bow0 = (<Stroke><unknown>stroke).getBow();
        bowSimilarity += bow0.getSimilarity(bow);
    }
    let wholeSimilarity = sketch.getSimilarityBow(bow)
    // 返回公式结果
    return 100 * (property * bowSimilarity + (1 - property) * wholeSimilarity);
}