import {getDiagonal, getTargetDotsBow, getTargetDotsLine} from "./similarity";
import {equals, getMode} from "./common";

// 使用 ρ = x cos θ + y sin θ 的公式
export class Line{
    distance: number;
    angle: number;
    start: Dot;
    end: Dot;

    // 构造方法
    constructor(distance : number, angle : number) {
        this.distance = distance;
        this.angle = angle;
    }

    setStart(start : Dot) {
        this.start = start
    }

    setEnd(end : Dot) {
        this.end = end
    }

    // 两条线求相似度，使用xx公式
    getSimilarity (line : Line) : Number{
        let ratio1 = Math.abs(line.angle - this.angle) / Math.PI;
        let ratio2 = this.distance > line.distance ? line.distance / this.distance : this.distance/ line.distance;
        /* 长度是否要作为线条相似度的一部分，抑或交给整体相似度或过度绘制等计分
        let length1 = line.start.getDistance(line.end)
        let length2 = this.start.getDistance(this.end)
        let ratio3 = length1 > length2 ? length2/ length1 : length1 / length2;
        */
        return ratio1 * ratio2
    }

    // 给出横坐标，求在该线上的点
    getDot(x : number) : Dot {
        let y = (this.distance - x * Math.cos(this.angle)) / Math.sin(this.angle);
        return new Dot(x, y);
    }

    // 将一条标准线分为若干个标准点
    getDots (num) : Dot[]{
        let dots = new Dot[num];
        let deltaX = (this.end.x - this.start.x) / (num - 1);
        let deltaY = (this.end.y - this.start.y) / (num - 1);
        dots.add(this.start);
        for (let i = 1; i < num - 1; i++) {
            dots.add(new Dot(this.start.x + i * deltaX, this.start.y + i * deltaY))
        }
        dots.add(this.end);
        return dots;
    }

    // 线段长
    getLength () : number{
        return this.start.getDistance(this.end)
    }

    // 线段容差带，公式为线段长开根号乘以系数 + 两倍的宽度（此处为2）
    // 容差带用于计算 过度绘制/绘制缺失 等需要拟合点集的场合
    getToleranceZoneWidth () {
        return 3/4 * Math.sqrt(this.getLength()) + 2;
    }

    // 获取镜像点
    getMirrorDot(dot : Dot) : Dot {
        let cos = Math.cos(this.angle);
        let sin = Math.sin(this.angle);
        let x = 2 * (sin * sin * dot.x - cos * sin * dot.y + cos * this.distance) - dot.x;
        let y = 2 * (cos * cos * dot.y - cos * sin * dot.x + sin * this.distance) - dot.y;
        return new Dot(x, y);
    }
}

export class Bow{
    r : number;
    o: Dot;
    start: Dot;
    end: Dot;
    isMajorArc : boolean;
    isCircle : boolean;

    // 构造方法
    constructor() {

    }

    setR(r : number) {
        this.r = r;
    }

    setO(o : Dot) {
        this.o = o
    }

    setStart(start : Dot) {
        this.start = start
    }

    setEnd(end : Dot) {
        this.end = end
    }

    // 获取相对于x轴和圆心的夹角(-pi ~ +pi)
    getAngleX (dot : Dot) : number{
        let acos = (dot.x - this.o.x)  / this.r;
        return dot.y > this.o.y ? Math.acos(acos) : 2 * Math.PI - Math.acos(acos);
    }

    // 获取弧的圆心角
    getAngleO () : number{
        if (this.isCircle) {
            return 2 * Math.PI;
        } else {
            let angle = Math.abs(this.getAngleX(this.start) - this.getAngleX(this.end));
            if (this.isMajorArc) {
               return angle > Math.PI ? angle : 2 * Math.PI - angle;
            } else {
                return angle < Math.PI ? angle : 2 * Math.PI - angle;
            }
        }
    }

    // 获取弧长
    getLength() {
        return this.getAngleO() * this.r;
    }

    // 是否在圆上
    onCircle(dot : Dot) : boolean {
        return equals(this.o.getDistance(dot), this.r)
    }

    onCircleBelt(dot : Dot, tolerance : number) : boolean {
        return this.o.getDistance(dot) <= tolerance;
    }

    // 是否在弧上
    onBow(dot : Dot) : boolean {
        if (!this.onCircle(dot)) {
            return false;
        }
        let a1 = this.getAngleX(this.start);
        let a2 = this.getAngleX(this.end);
        let a0 = this.getAngleX(dot);
        if (!this.isMajorArc) {
            // 交界处
            if(Math.abs(a2 - a1) > Math.PI) {
                return (0 <= a0 && a0 <= a1) || (a2 <= a0 && a0 <= 2 * Math.PI);
            } else {
                return (a1 <= a0 && a0 <= a2);
            }
        } else {
            // 与上面相反即可
            if(Math.abs(a2 - a1) > Math.PI) {
                return !(0 <= a0 && a0 <= a1) && !(a2 <= a0 && a0 <= 2 * Math.PI);
            } else {
                return !(a1 <= a0 && a0 <= a2);
            }
        }
    }

    getLeftUp() : Dot{
        let left = new Dot(this.o.x - this.r, this.o.y);
        let up = new Dot(this.o.x, this.o.y + this.r);
        let x1, y1;
        if (this.onBow(up)) {
            y1 = this.o.y + this.r;
        } else {
            y1 = Math.max(this.start.y, this.end.y);
        }

        if (this.onBow(left)) {
            x1 = this.o.x - this.r;
        } else {
            x1 = Math.min(this.start.x, this.end.x);
        }
        return new Dot(x1, y1);
    }

    getRightDown() : Dot{
        let right = new Dot(this.o.x + this.r, this.o.y);
        let down = new Dot(this.o.x, this.o.y - this.r);
        let x2, y2;
        if (this.onBow(down)) {
            y2 = this.o.y - this.r;
        } else {
            y2 = Math.min(this.start.y, this.end.y);
        }
        if (this.onBow(right)) {
            x2 = this.o.x + this.r;
        } else {
            x2 = Math.max(this.start.x, this.end.x);
        }
        return new Dot(x2, y2);
    }

    getDiagonal() {
        return this.getLeftUp().getDistance(this.getRightDown());
    }

    // 获取最小矩形
    getStandardSquare() {
        let leftup = this.getLeftUp();
        let rightdown = this.getRightDown();
        return (rightdown.x - leftup.x) * (leftup.y - rightdown.y);
    }

    getDots(num : number) :Dot[] {
        let dots = new Dot[num];
        dots.add(this.start);
        let averageAngle = this.getAngleO() / (num -1);
        for (let i = 1; i < num -1 ;i++) {
            let newDot = this.getNewDotFromStart(averageAngle * (i + 1));
            dots.add(newDot);
        }
        dots.add(this.end);
        return dots;
    }

    getNewDotFromStart(biaAngle : number) : Dot{
        let startAngle = this.getAngleX(this.start);
        let realAngle = startAngle - biaAngle;
        let x = this.o.x + Math.cos(realAngle) * this.r;
        let y = this.o.y + Math.sin(realAngle) * this.r;
        return new Dot(x, y);
    }

    // 如果第二条弧的起始及结束点均在圆的容错带内，且第二条弧的起始点与第一条弧的结束点相接，
    // 则可以认为两者可以连接为一条弧(实际上两个tolerance应当不同)
    canLink (l1d2 : Dot, l2d1 : Dot, l2d2 : Dot, tolerance : number) : boolean{
        return equals(l2d1.getDistance(this.o), tolerance) && equals(l2d2.getDistance(this.o), tolerance) && equals(l2d1.getDistance(l1d2), tolerance);
    }

    getSimilarity(bow : Bow) {
        let ratio1 = bow.r > this.r ? this.r / bow.r : bow.r / this.r;
        let angle1 = this.getAngleO();
        let angle2 = bow.getAngleO();
        let ratio2 = angle1 > angle2 ? angle2 / angle1 : angle1 / angle2;
        let ratio3 = this.o.getDistance(bow.o) / getLongDiagonal(this, bow);
        return ratio1 * ratio2 * (1 - ratio3);
    }

    getDotFromAngle(angle : number) : Dot {
        let x = this.o.x + this.r * Math.cos(angle);
        let y = this.o.y + this.r * Math.sin(angle);
        return new Dot(x, y);
    }

    // 线段容差带，公式为弧长开根号乘以系数 + 两倍的宽度（此处为2）
    getToleranceZoneWidth () {
        return 3/4 * Math.sqrt(this.getLength()) + 2;
    }

    // 获取镜像点
    getMirrorDot(dot : Dot) : Dot {
        let angle = this.getAngleX(dot);
        let length = 2 * dot.getDistance(this.o) - this.r;
        let x = this.o.x + length * Math.cos(angle);
        let y = this.o.y + length * Math.sin(angle);
        return new Dot(x, y);
    }
}

function getLongDiagonal(bow1 : Bow, bow2 : Bow) : number{
    let lu1 = bow1.getLeftUp();
    let lu2 = bow2.getLeftUp();
    let rd1 = bow1.getRightDown();
    let rd2 = bow2.getRightDown();
    let d1 = lu1.getDistance(rd1);
    let d2 = lu1.getDistance(rd2);
    let d3 = lu2.getDistance(rd1);
    let d4 = lu2.getDistance(rd2);
    let max = d1 > d2 ? d1 : d2;
    max = max > d3 ? max : d3;
    return max > d4 ? max : d4;
}

export class Dot {

    x: number;
    y: number;
    time: number;

    // 构造方法
    constructor(x : number, y : number) {
        this.x = x;
        this.y = y;
    }

    // 获取两点间距离
    getDistance (dot : Dot) : number{
        return Math.sqrt(Math.pow(dot.x - this.x, 2) + Math.pow(dot.y - this.y, 2))
    }

    //获取点1和点2关于中心点的夹角
    getAngle(dot1 : Dot, dot2 : Dot) : number{
        let acos = ((dot1.x - this.x) * (dot2.x - this.x) + (dot1.y - this.y) * (dot2.y - this.y))
            / (this.getDistance(dot1) * this.getDistance(dot2));
        return Math.acos(acos);
    }

    getDistanceFromLine(line : Line) : number {
        return Math.abs(Math.cos(line.angle) * this.x + Math.sin(line.angle) * this.y - line.distance)
    }
}

export class Sketch{
    strokes: Stroke[]

    // 获取点的数量
    getDotsNumber () {
        let num = 0;
        for (let stroke in this.strokes) {
            num += stroke.length
        }
        return num
    }

    // 将一维线列表改为二维点列表
    getDots () : Dot[]{
        let dots = new Dot[this.getDotsNumber()]
        for (let stroke in this.strokes) {
            (<Stroke><unknown>stroke).dots.forEach(r => dots.add(r))
        }
        return dots
    }

    // 获取豪斯多夫距离
    getHausdorff (targetDots : Dot[]) : number{
        let realDots = this.getDots();
        let max = 0;
        for (let realDot in realDots) {
            let min = Number.MAX_VALUE
            for (let targetDot in targetDots) {
                let distance = (<Dot><unknown>targetDot).getDistance(<Dot><unknown>realDot);
                if (distance < min) {
                    min = distance;
                }
            }
            if (min > max) {
                max = min;
            }
        }
        return max;
    }

    // 获取两点集的相似性
    getSimilarityLine (lines : Line[]) {
        let targetDots = getTargetDotsLine(this, lines);
        let hausdorff = this.getHausdorff(targetDots);
        let targetDiagonal = this.getDiagonal();
        let realDiagonal = getDiagonal(lines);
        let ratio = 1;
        if (targetDiagonal > realDiagonal) {
            ratio = realDiagonal / targetDiagonal;
        } else {
            ratio =  targetDiagonal / realDiagonal;
        }
        return ratio * (1 - (hausdorff / targetDiagonal));
    }
    // 获取两点集的相似性
    getSimilarityBow (bow : Bow) {
        let targetDots = getTargetDotsBow(this, bow);
        let hausdorff = this.getHausdorff(targetDots);
        let targetDiagonal = this.getDiagonal();
        let realDiagonal = bow.getDiagonal();
        let ratio = 1;
        if (targetDiagonal > realDiagonal) {
            ratio = realDiagonal / targetDiagonal;
        } else {
            ratio =  targetDiagonal / realDiagonal;
        }
        return ratio * (1 - (hausdorff / targetDiagonal));
    }


    // 获取点集的对角线长度
    getDiagonal() : number{
        let left = (this.strokes[0].dots[0]).x;
        let right = (this.strokes[0].dots[0]).x;
        let up = (this.strokes[0].dots[0]).y;
        let down = (this.strokes[0].dots[0]).y;
        for (let stroke in this.strokes) {
            for (let dot in <Dot[]><unknown>stroke) {
                let x = (<Dot><unknown>dot).x
                let y = (<Dot><unknown>dot).y
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
        }
        return Math.sqrt(Math.pow(right - left, 2) + Math.pow(up - down, 2));
    }

    getStandardSquare () : number{
        let left = (this.strokes[0].dots[0]).x;
        let right = (this.strokes[0].dots[0]).x;
        let up = (this.strokes[0].dots[0]).y;
        let down = (this.strokes[0].dots[0]).y;
        for (let stroke in this.strokes) {
            for (let dot in <Dot[]><unknown>stroke) {
                let x = (<Dot><unknown>dot).x
                let y = (<Dot><unknown>dot).y
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
        }
        return (right - left) * (up - down);
    }
}

export class Stroke {
    dots: Dot[];

    // 根据dots获取拟合线(霍夫空间)
    getLine () : Line{
        // 求出若干条直线，对于每条直线，求其angle从0 ~ pi时对应的distance值，这样得到霍夫空间
        let outerSpace = new Mode[180];
        for (let i = 0; i < 180; i++) {
            let angle = i / Math.PI;
            let innerSpace = new Number[this.dots.length]
            for (let dot in this.dots) {
                let distance = Math.cos(angle) * (<Dot><unknown>dot).x + Math.sin(angle) * (<Dot><unknown>dot).y
                innerSpace.add(Math.floor(distance));
            }
            outerSpace.add(getMode(innerSpace))
        }
        let targetAngle = 0;
        let targetDistance = outerSpace[0].mode;
        let max = outerSpace[0].num;
        for (let i = 1; i < 180; i++) {
            if ((<Mode>outerSpace[i]).num > max) {
                targetAngle = i;
                targetDistance = outerSpace[i].mode;
                max = outerSpace[i].num;
            }
        }

        let line = new Line(targetAngle, targetDistance)
        line.setStart(this.dots[0])
        // 构造结束点
        let x = this.dots[this.dots.length - 1].x;
        let y = (targetDistance - x * Math.cos(targetAngle)) / Math.sin(targetAngle);
        line.setEnd(new Dot(x, y))
        return line
    }

    // 任取4点，其中三点构成圆，判断另一点是否在其上，若在，则对于剩余点，
    //  .继续检测是否在其上，达到某个标准，则认为找到相应圆
    getBow() : Bow{
        let bow = new Bow();
        outer:  for (let i = 0; i < Math.floor(this.dots.length); i += 4) {
            let dot1 = this.dots[i];
            let dot2 = this.dots[i + 1];
            let dot3 = this.dots[i];

            // 三点构造圆
            let s1 = Math.pow(dot1.x,2) + Math.pow(dot1.y,2);
            let s2 = Math.pow(dot2.x,2) + Math.pow(dot2.y,2);
            let s3 = Math.pow(dot3.x,2) + Math.pow(dot3.y,2);

            let m1 = 2 *((s2 - s1) * (dot3.y - dot1.y) - (s3 - s1) * (dot2.y - dot1.y));
            let m2 = 2 *((s3 - s1) * (dot2.x - dot1.x) - (s2 - s1) * (dot3.x - dot1.x));
            let m3 = 4 * ((dot2.x - dot1.x)*(dot3.y - dot1.y) - (dot3.x - dot1.x) * (dot2.y - dot1.y));
            // 三点共线，开启下一次寻找
            if (m3 == 0) {
                continue;
            }
            bow.setO(new Dot(m1/m3, m2/m3));
            bow.setR(dot1.getDistance(bow.o));

            let dot4 = this.dots[i + 1];
            if (bow.onCircleBelt(dot4, 5)) {
                let sum = 0;
                for (let j = 0; j < this.dots.length ; j++){
                    if (bow.onCircleBelt(this.dots[j], 5)) {
                        sum++;
                    }
                    if (sum > this.dots.length / 4) {
                        break outer;
                    }
                }
            }
        }
        let startAngle = bow.getAngleX(this.dots[0]);
        let endAngle = bow.getAngleX(this.dots[this.dots.length - 1]);
        bow.setStart(bow.getDotFromAngle(startAngle));
        bow.setEnd(bow.getDotFromAngle(endAngle));
        return bow;
    }
}

// 众数结构
export class Mode {
    mode: Number;
    num: Number;

    // 构造方法
    constructor(mode : number, num : number) {
        this.mode = mode;
        this.num = num;
    }
}






