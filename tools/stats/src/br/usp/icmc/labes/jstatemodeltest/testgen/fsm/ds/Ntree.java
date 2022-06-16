package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds;

import java.util.Iterator;

public class Ntree {
  private Node root;
  
  public void setRoot(Node root) {
    this.root = root;
  }
  
  public Node getRoot() {
    return this.root;
  }
  
  public void addNode(Node n1, String label, Node n2) {
    n1.addChild(n2, label);
  }
  
  public void print() {
    System.out.println("*****************");
    System.out.println("Testing Tree");
    print("-", this.root, "ROOT");
    System.out.println("*****************");
  }
  
  private void print(String level, Node n, String arclabel) {
    System.out.println(String.valueOf(level) + n.getState().getLabel() + "(" + arclabel + ")");
    Iterator<String> arclabels = n.getLabels().iterator();
    for (Node n1 : n.getChildren())
      print(String.valueOf(level) + "-----------", n1, arclabels.next()); 
  }
}
