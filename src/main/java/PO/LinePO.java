package PO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LinePO {

  private String lid;
  private String pid;


  @Id
  public String getLid() {
    return lid;
  }

  public void setLid(String lid) {
    this.lid = lid;
  }


  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

}
