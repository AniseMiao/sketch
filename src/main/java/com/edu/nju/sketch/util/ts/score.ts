import {Bow, Line, Sketch} from "./Line";
import {getContinuity} from "./continuity";
import {getCountBow, getCountLine} from "./count";
import {getExcessiveBow, getExcessiveLine} from "./excessive";
import {getLackBow, getLackLine} from "./lack";
import {getSequenceBow, getSequenceLine} from "./sequence";
import {getSimilarityBow, getSimilarityLine} from "./similarity";
import {getSizeBow, getSizeLine} from "./size";
import {getSmoothnessBow, getSmoothnessLine} from "./smoothness";
import {getTimeBow, getTimeLine} from "./time";

export function getScore(sketch : Sketch, copyId : number, isLine : boolean) : number[]{
    if (isLine) {
        return getScoreLine(sketch, copyId);
    } else {
        return getScoreBow(sketch, copyId);
    }
}

function getScoreLine(sketch : Sketch, copyId : number) : number[] {
    let score = new Number[10];
    let lines = getLines(copyId);
    // 连续性
    let continuity = getContinuity(sketch);
    score.add(continuity);
    // 笔画数
    let count = getCountLine(sketch, lines);
    score.add(count);
    // 过度绘制
    let excessive = getExcessiveLine(sketch, lines);
    score.add(excessive);
    // 绘制缺失
    let lack = getLackLine(sketch, lines);
    score.add(lack);

    /* 顺序
    let sequence = getSequenceLine(sketch, lines);
    score.add(sequence)
     */

    // 相似性
    let similarity = getSimilarityLine(sketch, lines);
    score.add(similarity)
    // 大小
    let size = getSizeLine(sketch, lines);
    score.add(size)
    // 平滑度
    let smoothness = getSmoothnessLine(sketch, lines);
    score.add(smoothness)
    // 时间
    let time = getTimeLine(sketch, lines);
    score.add(time)
    // TODO 总分
    let sum = 100;
    score.add(sum);
    return score
}

function getScoreBow(sketch : Sketch, copyId : number) : number[] {
    let score = new Number[10];
    let bow = getBow(copyId);
    // 连续性
    let continuity = getContinuity(sketch);
    score.add(continuity);
    // 笔画数
    let count = getCountBow(sketch, bow);
    score.add(count);
    // 过度绘制
    let excessive = getExcessiveBow(sketch, bow);
    score.add(excessive);
    // 绘制缺失
    let lack = getLackBow(sketch, bow);
    score.add(lack);

    // 相似性
    let similarity = getSimilarityBow(sketch, bow);
    score.add(similarity)
    // 大小
    let size = getSizeBow(sketch, bow);
    score.add(size)
    // 平滑度
    let smoothness = getSmoothnessBow(sketch, bow);
    score.add(smoothness)
    // 时间
    let time = getTimeBow(sketch, bow);
    score.add(time);
    //总分 S = 0.143*St + 0.145*Sn + 0.108*Ssim + 0.208*Sc + 0.158*Se + 0.239*Sl
    let sum = 0.143 * time + 0.145 * count + 0.108 * similarity + 0.208 * continuity + 0.158 * excessive + 0.239 * lack;
    score.add(sum);
    return score
}

function getLines(copyId : number) : Line[]{
    return
}

function getBow(copyId : number) : Bow{
    return
}