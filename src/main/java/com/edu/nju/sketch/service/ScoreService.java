package com.edu.nju.sketch.service;

import com.edu.nju.sketch.PO.Score;

public interface ScoreService {
    public void addScore(String pid, double[] scores);

    public Score getScore(String pid);
}
