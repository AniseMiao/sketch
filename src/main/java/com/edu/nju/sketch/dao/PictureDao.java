package com.edu.nju.sketch.dao;


import com.edu.nju.sketch.PO.Picture;

import java.util.ArrayList;

public interface PictureDao {
    public void addPicture(Picture picture);

    public void deletePicture(String pid);

    public Picture getPicture(String pid);

    public ArrayList<Picture> getPictures(String aid);
}
