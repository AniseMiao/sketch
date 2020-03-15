package dao;

import PO.LinePO;
import model.Line;

import java.util.ArrayList;

public interface LineDao {

    public void addLine(LinePO linePO);

    public ArrayList<String> getLids(String pid);

}
