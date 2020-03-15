package service.impl;

import PO.DotPO;
import dao.DotDao;
import model.Dot;
import org.springframework.stereotype.Service;
import service.DotService;
import util.IdUtil;

import java.util.ArrayList;

@Service("DotService")
public class DotServiceImpl implements DotService {
    private DotDao dotDao;

    @Override
    public void addDots(String lid, ArrayList<Dot> dots) {
        ArrayList<DotPO> list = new ArrayList<>();
        for (int i = 0; i < dots.size(); i++) {
           DotPO dot = new DotPO();
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
    public ArrayList<Dot> getDots(String lid) {
        ArrayList<DotPO> dotPOList = dotDao.getDots(lid);
        ArrayList<Dot> ret = new ArrayList<>();
        for (DotPO po : dotPOList) {
            Dot dot = new Dot();
            dot.setX(po.getX());
            dot.setY(po.getY());
            dot.setT(po.getT());
            ret.add(dot);
        }
        return ret;
    }
}
