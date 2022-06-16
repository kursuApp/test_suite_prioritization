package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.highorder;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.Operator;

public class OperatorApplication {
  Operator operator;
  
  State state = null;
  
  Transition transition = null;
  
  String input = null, output = null;
  
  public Operator getOperator() {
    return this.operator;
  }
  
  public void setOperator(Operator operator) {
    this.operator = operator;
  }
  
  public State getState() {
    return this.state;
  }
  
  public void setState(State state) {
    this.state = state;
  }
  
  public Transition getTransition() {
    return this.transition;
  }
  
  public void setTransition(Transition transition) {
    this.transition = transition;
  }
  
  public String getInput() {
    return this.input;
  }
  
  public void setInput(String input) {
    this.input = input;
  }
  
  public String getOutput() {
    return this.output;
  }
  
  public void setOutput(String output) {
    this.output = output;
  }
  
  public String toString() {
    return this.operator + ";" + this.state + ";" + this.transition + ";" + this.input + ";" + this.output;
  }
}
