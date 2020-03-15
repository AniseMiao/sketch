package service.impl;

import PO.LinePO;
import dao.LineDao;
import model.Line;
import org.springframework.stereotype.Service;
import service.DotService;
import service.LineService;
import util.IdUtil;

import java.util.ArrayList;

@Service("LineService")
public class LineServiceImpl implements LineService {

    private LineDao lineDao;
    private DotService dotService;

    @Override
    public void addLines(String pid, ArrayList<Line> lines) {
        for (Line line : lines) {
            String lid = IdUtil.getLineId();
            LinePO linePO = new LinePO();
            linePO.setLid(lid);
            linePO.setPid(pid);
            lineDao.addLine(linePO);
            dotService.addDots(lid, line.getDots());
        }
    }

    @Override
    public ArrayList<Line> getLines(String pid) {
        ArrayList<Line> ret = new ArrayList<>();
        ArrayList<String> pidList = lineDao.getLids(pid);
        for (String lid : pidList) {
            Line line = new Line();
            line.setDots(dotService.getDots(lid));
            ret.add(line);
        }
        return ret;
    }
}
