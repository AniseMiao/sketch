package dao;

import PO.ScorePO;

public interface ScoreDao {
    public void addScore(ScorePO score);

    public ScorePO getScore(String pid);
}
