package com.edu.nju.sketch.PO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Picture {

  private String pid;
  private String aid;
  private long starttime;
  private double score;
  private long copyid;


  @Id
  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }


  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
  }


  public long getStarttime() {
    return starttime;
  }

  public void setStarttime(long starttime) {
    this.starttime = starttime;
  }


  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }


  public long getCopyid() {
    return copyid;
  }

  public void setCopyid(long copyid) {
    this.copyid = copyid;
  }

}
