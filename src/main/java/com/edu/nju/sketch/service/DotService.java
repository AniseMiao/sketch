package com.edu.nju.sketch.service;

import com.edu.nju.sketch.model.Dot;
import com.edu.nju.sketch.model.Line;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface DotService {
    public void addDots(String pid, ArrayList<Dot> dots);

    public ArrayList<Dot> getDots(String lid);
}
