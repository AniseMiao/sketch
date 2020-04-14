package com.edu.nju.sketch.service.impl;


import com.edu.nju.sketch.PO.Line;
import com.edu.nju.sketch.dao.LineDao;
import com.edu.nju.sketch.service.DotService;
import com.edu.nju.sketch.service.LineService;
import com.edu.nju.sketch.util.IdUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("LineService")
public class LineServiceImpl implements LineService {

    private LineDao lineDao;
    private DotService dotService;

    @Override
    public void addLines(String pid, ArrayList<com.edu.nju.sketch.model.Line> lines) {
        for (com.edu.nju.sketch.model.Line line : lines) {
            String lid = IdUtil.getLineId();
            Line linePO = new Line();
            linePO.setLid(lid);
            linePO.setPid(pid);
            lineDao.addLine(linePO);
            dotService.addDots(lid, line.getDots());
        }
    }

    @Override
    public ArrayList<com.edu.nju.sketch.model.Line> getLines(String pid) {
        ArrayList<com.edu.nju.sketch.model.Line> ret = new ArrayList<>();
        ArrayList<String> pidList = lineDao.getLids(pid);
        for (String lid : pidList) {
            com.edu.nju.sketch.model.Line line = new com.edu.nju.sketch.model.Line();
            line.setDots(dotService.getDots(lid));
            ret.add(line);
        }
        return ret;
    }
}
