package com.edu.nju.sketch.service;

import com.edu.nju.sketch.PO.Picture;
import java.util.ArrayList;

public interface PictureService {

    public void addPicture(String aid, int copyid, long starttime);

    public void deletePicture(String pid);

    public Picture getPicture(String pid);

    public ArrayList<Picture> getPictures(String aid);
}
