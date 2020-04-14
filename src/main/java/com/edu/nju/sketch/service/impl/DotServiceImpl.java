package com.edu.nju.sketch.service.impl;

import com.edu.nju.sketch.PO.Dot;
import com.edu.nju.sketch.dao.DotDao;
import com.edu.nju.sketch.service.DotService;
import com.edu.nju.sketch.util.IdUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("DotService")
public class DotServiceImpl implements DotService {
    private DotDao dotDao;

    @Override
    public void addDots(String lid, ArrayList<com.edu.nju.sketch.model.Dot> dots) {
        ArrayList<Dot> list = new ArrayList<>();
        for (int i = 0; i < dots.size(); i++) {
           Dot dot = new Dot();
           String did = IdUtil.getDotId();
           dot.setDid(did);
           dot.setLid(lid);
           dot.setX(dots.get(i).getX());
           dot.setY(dots.get(i).getY());
           dot.setT(dots.get(i).getT());
        }
        dotDao.addDots(lid, list);
    }

    @Override
    public ArrayList<com.edu.nju.sketch.model.Dot> getDots(String lid) {
        ArrayList<Dot> dotList = dotDao.getDots(lid);
        ArrayList<com.edu.nju.sketch.model.Dot> ret = new ArrayList<>();
        for (Dot po : dotList) {
            com.edu.nju.sketch.model.Dot dot = new com.edu.nju.sketch.model.Dot();
            dot.setX(po.getX());
            dot.setY(po.getY());
            dot.setT(po.getT());
            ret.add(dot);
        }
        return ret;
    }
}
