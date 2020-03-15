package service;

import model.Line;

import java.util.ArrayList;

public interface LineService {

    public void addLines(String pid, ArrayList<Line> lines);

    public ArrayList<Line> getLines(String pid);
}
