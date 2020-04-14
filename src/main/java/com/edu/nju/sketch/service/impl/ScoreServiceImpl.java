package com.edu.nju.sketch.service.impl;


import com.edu.nju.sketch.PO.Score;
import com.edu.nju.sketch.dao.ScoreDao;
import com.edu.nju.sketch.service.ScoreService;
import org.springframework.stereotype.Service;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {

    private ScoreDao scoreDao;

    @Override
    public void addScore(String pid, double[] scores) {
        Score score = new Score();
        score.setPid(pid);
        score.setTimescore(scores[0]);
        score.setSizescore(scores[1]);
        score.setNumscore(scores[2]);
        score.setSequencescore(scores[3]);
        score.setSmoothnessscore(scores[4]);
        score.setSimilarityscore(scores[5]);
        score.setContinutyscore(scores[6]);
        score.setExcessivescore(scores[7]);
        score.setLossscore(scores[8]);
        scoreDao.addScore(score);
    }

    @Override
    public Score getScore(String pid) {
        return scoreDao.getScore(pid);
    }
}
