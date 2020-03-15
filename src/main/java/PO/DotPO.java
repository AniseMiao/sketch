package PO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DotPO {

  private String did;
  private double X;
  private double Y;
  private long T;
  private String lid;


  @Id
  public String getDid() {
    return did;
  }

  public void setDid(String did) {
    this.did = did;
  }


  public double getX() {
    return X;
  }

  public void setX(double X) {
    this.X = X;
  }


  public double getY() {
    return Y;
  }

  public void setY(double Y) {
    this.Y = Y;
  }


  public long getT() {
    return T;
  }

  public void setT(long T) {
    this.T = T;
  }


  public String getLid() {
    return lid;
  }

  public void setLid(String lid) {
    this.lid = lid;
  }

}
