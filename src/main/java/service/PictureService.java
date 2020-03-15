package service;

import PO.PicturePO;
import java.util.ArrayList;

public interface PictureService {

    public void addPicture(String aid, int copyid, long starttime);

    public void deletePicture(String pid);

    public PicturePO getPicture(String pid);

    public ArrayList<PicturePO> getPictures(String aid);
}
