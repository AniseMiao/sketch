package service.impl;

import PO.PicturePO;
import dao.PictureDao;
import org.springframework.stereotype.Service;
import service.PictureService;
import util.IdUtil;

import java.util.ArrayList;

@Service("PictureService")
public class PictureServiceImpl implements PictureService {

    private PictureDao pictureDao;
    @Override
    public void addPicture(String aid, int copyid, long starttime) {
        PicturePO picture = new PicturePO();
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
    public PicturePO getPicture(String pid) {
        return pictureDao.getPicture(pid);
    }

    @Override
    public ArrayList<PicturePO> getPictures(String aid) {
        return pictureDao.getPictures(aid);
    }
}
