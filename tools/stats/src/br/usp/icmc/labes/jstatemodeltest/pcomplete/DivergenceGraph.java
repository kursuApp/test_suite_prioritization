package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Graph;
import java.util.ArrayList;

public class DivergenceGraph {
  ArrayList<String> T;
  
  ArrayList<Pair> D;
  
  ArrayList<String> K;
  
  int n;
  
  public DivergenceGraph(ArrayList<String> T, ArrayList<Pair> D, int n) {
    this.T = T;
    this.D = D;
    this.n = n;
    build();
  }
  
  private void build() {
    Graph graph = new Graph();
    for (String test : this.T)
      graph.addNode(test); 
    for (Pair pair : this.D)
      graph.addArc(pair.getLeft(), pair.getRight()); 
    ArrayList<String> clique = graph.getMaxCliqueV2(this.n);
    this.K = clique;
  }
  
  public ArrayList<String> getK() {
    return this.K;
  }
}
