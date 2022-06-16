package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.util.ArrayList;

public class Pair {
  private String left;
  
  private String right;
  
  public Pair(String left, String right) {
    this.left = left;
    this.right = right;
  }
  
  public String getLeft() {
    return this.left;
  }
  
  public String getRight() {
    return this.right;
  }
  
  public String getShorter() {
    if (TestSequence.lenght(this.left) <= TestSequence.lenght(this.right))
      return this.left; 
    return this.right;
  }
  
  public String getlonger() {
    if (TestSequence.lenght(this.left) > TestSequence.lenght(this.right))
      return this.left; 
    return this.right;
  }
  
  public String toString() {
    return "(" + this.left + ";" + this.right + ")";
  }
  
  public static boolean add(ArrayList<Pair> pairs, Pair pair) {
    if (!in(pair, pairs)) {
      pairs.add(pair);
      return true;
    } 
    return false;
  }
  
  public static boolean in(Pair pair, ArrayList<Pair> pairs) {
    for (Pair p : pairs) {
      if (p.equals(pair))
        return true; 
    } 
    return false;
  }
  
  private boolean equals(Pair pair) {
    if (this.left.equals(pair.getLeft()) && this.right.equals(pair.getRight()))
      return true; 
    if (this.right.equals(pair.getLeft()) && this.left.equals(pair.getRight()))
      return true; 
    return false;
  }
  
  public static ArrayList<String> getPartition(String alpha, ArrayList<Pair> C) {
    ArrayList<String> ret = new ArrayList<String>();
    for (Pair p : C) {
      if (p.contains(alpha))
        ret.add(p.getNot(alpha)); 
    } 
    return ret;
  }
  
  private String getNot(String alpha) {
    if (this.left.equals(alpha))
      return this.right; 
    return this.left;
  }
  
  private boolean contains(String alpha) {
    if (this.left.equals(alpha) || this.right.equals(alpha))
      return true; 
    return false;
  }
}
