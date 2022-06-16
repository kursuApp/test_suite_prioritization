package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs.datastructure;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import java.util.ArrayList;

public class SequenceStatesPair {
  ArrayList<State> states;
  
  String sequence;
  
  public SequenceStatesPair(String x) {
    this.states = new ArrayList<State>();
    this.sequence = x;
  }
  
  public void setSequence(String sequence) {
    this.sequence = sequence;
  }
  
  public String getSequence() {
    return this.sequence;
  }
  
  public ArrayList<State> getStates() {
    return this.states;
  }
  
  public void addState(State s) {
    this.states.add(s);
  }
  
  public String toString() {
    return "[" + this.sequence + ";" + this.states + "]";
  }
}
