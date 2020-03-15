package PO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ScorePO {

  private String pid;
  private double timescore;
  private double sizescore;
  private double numscore;
  private double sequencescore;
  private double smoothnessscore;
  private double similarityscore;
  private double continutyscore;
  private double excessivescore;
  private double lossscore;


  @Id
  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }


  public double getTimescore() {
    return timescore;
  }

  public void setTimescore(double timescore) {
    this.timescore = timescore;
  }


  public double getSizescore() {
    return sizescore;
  }

  public void setSizescore(double sizescore) {
    this.sizescore = sizescore;
  }


  public double getNumscore() {
    return numscore;
  }

  public void setNumscore(double numscore) {
    this.numscore = numscore;
  }


  public double getSequencescore() {
    return sequencescore;
  }

  public void setSequencescore(double sequencescore) {
    this.sequencescore = sequencescore;
  }


  public double getSmoothnessscore() {
    return smoothnessscore;
  }

  public void setSmoothnessscore(double smoothnessscore) {
    this.smoothnessscore = smoothnessscore;
  }


  public double getSimilarityscore() {
    return similarityscore;
  }

  public void setSimilarityscore(double similarityscore) {
    this.similarityscore = similarityscore;
  }


  public double getContinutyscore() {
    return continutyscore;
  }

  public void setContinutyscore(double continutyscore) {
    this.continutyscore = continutyscore;
  }


  public double getExcessivescore() {
    return excessivescore;
  }

  public void setExcessivescore(double excessivescore) {
    this.excessivescore = excessivescore;
  }


  public double getLossscore() {
    return lossscore;
  }

  public void setLossscore(double lossscore) {
    this.lossscore = lossscore;
  }

}
