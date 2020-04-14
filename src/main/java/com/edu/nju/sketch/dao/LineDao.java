package com.edu.nju.sketch.dao;

import com.edu.nju.sketch.PO.Line;

import java.util.ArrayList;

public interface LineDao {

    public void addLine(Line line);

    public ArrayList<String> getLids(String pid);

}
