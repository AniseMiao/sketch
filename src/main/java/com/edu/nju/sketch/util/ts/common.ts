// 获取一列数据中的众数及其出现次数，与霍夫空间相关
import { Mode } from './Line'
export function getMode(list : number[]) : Mode{
    list.sort();
    let mode = list[0];
    let num = 1;
    let maxMode = list[0];
    let maxNum = 1;
    for(let i = 1; i < list.length; i++) {
        if (list[i] == mode) {// 数字与上一个相同，当前计数++
            num++;
        } else {// 不同，则前一个数字已计算完毕
            if (num > maxNum) { // 前一个数字计数大于目前最大计数，更新
                maxNum = num;
                maxMode = mode;
            }
            // 对当前清0
            mode = list[i];
            num = 1;
        }
    }
    return new Mode(maxMode, maxNum)
}

export function equals(a : number, b: number) : boolean{
    return Math.abs(a - b) < 0.0001;
}