package dao;

import PO.PicturePO;

import java.util.ArrayList;

public interface PictureDao {
    public void addPicture(PicturePO picture);

    public void deletePicture(String pid);

    public PicturePO getPicture(String pid);

    public ArrayList<PicturePO> getPictures(String aid);
}
