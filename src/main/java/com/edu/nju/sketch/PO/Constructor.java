package com.edu.nju.sketch.PO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Constructor {

  private java.sql.Timestamp lasttime;
  private long lastid;
  private String keyword;


  public java.sql.Timestamp getLasttime() {
    return lasttime;
  }

  public void setLasttime(java.sql.Timestamp lasttime) {
    this.lasttime = lasttime;
  }


  public long getLastid() {
    return lastid;
  }

  public void setLastid(long lastid) {
    this.lastid = lastid;
  }


  @javax.persistence.Id
  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

}
