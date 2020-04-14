package com.edu.nju.sketch.service.impl;


import com.edu.nju.sketch.PO.Picture;
import com.edu.nju.sketch.dao.PictureDao;
import com.edu.nju.sketch.service.PictureService;
import com.edu.nju.sketch.util.IdUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("PictureService")
public class PictureServiceImpl implements PictureService {

    private PictureDao pictureDao;
    @Override
    public void addPicture(String aid, int copyid, long starttime) {
        Picture picture = new Picture();
        String pid = IdUtil.getPictureId();
        picture.setAid(aid);
        picture.setPid(pid);
        picture.setCopyid(copyid);
        picture.setStarttime(starttime);
        pictureDao.addPicture(picture);
    }

    @Override
    public void deletePicture(String pid) {
        pictureDao.deletePicture(pid);
    }

    @Override
    public Picture getPicture(String pid) {
        return pictureDao.getPicture(pid);
    }

    @Override
    public ArrayList<Picture> getPictures(String aid) {
        return pictureDao.getPictures(aid);
    }
}
