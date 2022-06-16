package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import java.util.ArrayList;

public class GammaAlphas {
  private String gamma;
  
  private ArrayList<String> alphas;
  
  public GammaAlphas(String gamma, ArrayList<String> alphas) {
    this.gamma = gamma;
    this.alphas = alphas;
  }
  
  public ArrayList<String> getAlphas() {
    return this.alphas;
  }
  
  public String getGamma() {
    return this.gamma;
  }
  
  public void setAlphas(ArrayList<String> alphas) {
    this.alphas = alphas;
  }
  
  public void setGamma(String gamma) {
    this.gamma = gamma;
  }
}
