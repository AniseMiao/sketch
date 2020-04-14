package com.edu.nju.sketch.controller;


import com.edu.nju.sketch.PO.Picture;
import com.edu.nju.sketch.model.Line;
import com.edu.nju.sketch.service.LineService;
import com.edu.nju.sketch.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;

@RestController
@RequestMapping("sketch/api/v1/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;
    private LineService lineService;

    @PostMapping("/addPicture")
    public void addPicture(String aid, int copyid, long starttime) {
        pictureService.addPicture(aid, copyid, starttime);
    }

    @PostMapping("/deletePicture")
    public void deletePicture(String pid) {
        pictureService.deletePicture(pid);
    }

    @PostMapping("/addLines")
    public void addLines(String pid, ArrayList<Line> lines) {
        lineService.addLines(pid, lines);
    }

    @GetMapping("/getLines")
    public ArrayList<Line> getLines(String pid) {
        return lineService.getLines(pid);
    }

    @GetMapping("/getPicture")
    public Picture getPicture(String pid) {
        return pictureService.getPicture(pid);
    }

    @GetMapping("/getPictures")
    public ArrayList<Picture> getPictures(String aid) {
        return pictureService.getPictures(aid);
    }
}
