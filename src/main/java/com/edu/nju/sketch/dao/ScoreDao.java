package com.edu.nju.sketch.dao;


import com.edu.nju.sketch.PO.Score;

public interface ScoreDao {
    public void addScore(Score score);

    public Score getScore(String pid);
}
