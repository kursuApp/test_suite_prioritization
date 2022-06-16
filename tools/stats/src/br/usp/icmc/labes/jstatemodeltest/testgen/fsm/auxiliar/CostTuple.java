package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar;

public class CostTuple {
  private String gamma;
  
  private String alpha;
  
  private String beta;
  
  int alphacost;
  
  int betacost;
  
  public String getGamma() {
    return this.gamma;
  }
  
  public void setGamma(String gamma) {
    this.gamma = gamma;
  }
  
  public String getAlpha() {
    return this.alpha;
  }
  
  public void setAlpha(String alpha) {
    this.alpha = alpha;
  }
  
  public String getBeta() {
    return this.beta;
  }
  
  public void setBeta(String beta) {
    this.beta = beta;
  }
  
  public int getAlphacost() {
    return this.alphacost;
  }
  
  public void setAlphacost(int alphacost) {
    this.alphacost = alphacost;
  }
  
  public int getBetacost() {
    return this.betacost;
  }
  
  public void setBetacost(int betacost) {
    this.betacost = betacost;
  }
}
