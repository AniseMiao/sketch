package controller;

import PO.ScorePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ScoreService;

@RestController
@RequestMapping("sketch/api/v1/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/addScore")
    public void addScore(String pid, double[] scores) {
        scoreService.addScore(pid, scores);
    }

    @PostMapping("/getScore")
    public ScorePO getScore(String pid) {
        return scoreService.getScore(pid);
    }
}
