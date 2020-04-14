package com.edu.nju.sketch.util;

import com.edu.nju.sketch.targetModel.Sketch;

public class PictureUtil{
    private static Sketch sketch;
    private static String sketchId = "";

    public static void setSketch(String pid){
        if (pid.equals(sketchId)) {
            return;
        }
        sketch = Sketch.getSketch(pid);
        sketchId = pid;
    }

    public static Sketch getSketch(String pid){
        setSketch(pid);
        return sketch;
    }
}
