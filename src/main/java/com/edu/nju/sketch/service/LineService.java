package com.edu.nju.sketch.service;

import com.edu.nju.sketch.model.Line;

import java.util.ArrayList;

public interface LineService {

    public void addLines(String pid, ArrayList<Line> lines);

    public ArrayList<Line> getLines(String pid);
}
