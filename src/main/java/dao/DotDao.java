package dao;

import PO.DotPO;
import model.Dot;

import java.util.ArrayList;

public interface DotDao {

    public void addDots(String lid, ArrayList<DotPO> dots);

    public ArrayList<DotPO> getDots(String lid);
}
