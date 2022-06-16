package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import java.util.ArrayList;

public class Node {
  private State state;
  
  private ArrayList<Node> children = new ArrayList<Node>();
  
  private ArrayList<String> labels = new ArrayList<String>();
  
  public Node(State st) {
    this.state = st;
  }
  
  public State getState() {
    return this.state;
  }
  
  public void addChild(Node n, String label) {
    this.children.add(n);
    this.labels.add(label);
  }
  
  public ArrayList<Node> getChildren() {
    return this.children;
  }
  
  public ArrayList<String> getLabels() {
    return this.labels;
  }
}
