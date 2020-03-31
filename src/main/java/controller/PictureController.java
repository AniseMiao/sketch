package controller;

import PO.LinePO;
import PO.PicturePO;
import model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.LineService;
import service.PictureService;

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
    public PicturePO getPicture(String pid) {
        return pictureService.getPicture(pid);
    }

    @GetMapping("/getPictures")
    public ArrayList<PicturePO> getPictures(String aid) {
        return pictureService.getPictures(aid);
    }
}
