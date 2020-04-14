package com.edu.nju.sketch.dao;

import com.edu.nju.sketch.PO.Dot;

import java.util.ArrayList;

public interface DotDao {

    public void addDots(String lid, ArrayList<Dot> dots);

    public ArrayList<Dot> getDots(String lid);
}
