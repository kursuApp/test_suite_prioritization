package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import java.util.ArrayList;

public class State {
  private String label;
  
  private ArrayList<Transition> in;
  
  private ArrayList<Transition> out;
  
  public State(String l) {
    this.label = l;
    this.in = new ArrayList<Transition>();
    this.out = new ArrayList<Transition>();
  }
  
  public String toString() {
    return this.label;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }
  
  public String getLabel() {
    return this.label;
  }
  
  public ArrayList<Transition> getIn() {
    return this.in;
  }
  
  public void setIn(ArrayList<Transition> in) {
    this.in = in;
  }
  
  public ArrayList<Transition> getOut() {
    return this.out;
  }
  
  public void setOut(ArrayList<Transition> out) {
    this.out = out;
  }
  
  public void addInTransition(Transition t) {
    this.in.add(t);
  }
  
  public void addOutTransition(Transition t) {
    this.out.add(t);
  }
  
  public boolean isDefinedForInput(String input) {
    for (Transition t : this.out) {
      if (t.getInput().equals(input))
        return true; 
    } 
    return false;
  }
}
