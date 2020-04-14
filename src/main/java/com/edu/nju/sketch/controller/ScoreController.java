package com.edu.nju.sketch.controller;

import com.edu.nju.sketch.PO.Score;
import com.edu.nju.sketch.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sketch/api/v1/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/addScore")
    public void addScore(String pid, double[] scores) {
        scoreService.addScore(pid, scores);
    }

    @GetMapping("/getScore")
    public Score getScore(String pid) {
        return scoreService.getScore(pid);
    }
}
