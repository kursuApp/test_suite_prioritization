package br.usp.icmc.labes.jstatemodeltest.pcomplete;

public class Trigger {
  private Pair pair;
  
  private Ruler.AddedTo addedto;
  
  public Trigger(Pair p, Ruler.AddedTo a) {
    this.pair = p;
    this.addedto = a;
  }
  
  public void setAddedto(Ruler.AddedTo addedto) {
    this.addedto = addedto;
  }
  
  public Ruler.AddedTo getAddedto() {
    return this.addedto;
  }
  
  public Pair getPair() {
    return this.pair;
  }
  
  public void setPair(Pair pair) {
    this.pair = pair;
  }
  
  public String toString() {
    return "( " + this.pair + " ; " + this.addedto + " )";
  }
}
