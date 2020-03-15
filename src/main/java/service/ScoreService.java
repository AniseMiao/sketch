package service;

import PO.ScorePO;

public interface ScoreService {
    public void addScore(String pid, double[] scores);

    public ScorePO getScore(String pid);
}
