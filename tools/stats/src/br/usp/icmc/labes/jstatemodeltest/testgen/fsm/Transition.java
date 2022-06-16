package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import java.util.ArrayList;

public class Transition {
  private String input;
  
  private String output;
  
  private State in;
  
  private State out;
  
  public Transition(State in, State out, String input, String output) {
    this.in = in;
    this.in.addOutTransition(this);
    this.out = out;
    this.out.addInTransition(this);
    this.input = input;
    this.output = output;
  }
  
  public String toString() {
    return this.in + " -- " + this.input + " / " + this.output + " -> " + this.out;
  }
  
  public State getIn() {
    return this.in;
  }
  
  public State getOut() {
    return this.out;
  }
  
  public String getInput() {
    return this.input;
  }
  
  public String getOutput() {
    return this.output;
  }
  
  public void setIn(State in) {
    this.in = in;
  }
  
  public void setOut(State out) {
    this.out = out;
  }
  
  public void setOutput(String output) {
    this.output = output;
  }
  
  public void setInput(String input) {
    this.input = input;
  }
  
  public static ArrayList<Transition> diff(ArrayList<Transition> t1, ArrayList<Transition> t2) {
    ArrayList<Transition> ret = new ArrayList<Transition>();
    for (Transition t : t1) {
      if (!t2.contains(t))
        ret.add(t); 
    } 
    return ret;
  }
}
