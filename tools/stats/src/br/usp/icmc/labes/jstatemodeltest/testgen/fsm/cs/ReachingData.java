package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;

public class ReachingData {
  Transition transition;
  
  String sequence;
  
  public ReachingData(Transition transition, String sequence) {
    this.transition = transition;
    this.sequence = sequence;
  }
  
  public String getSequence() {
    return this.sequence;
  }
  
  public Transition getTransition() {
    return this.transition;
  }
  
  public void setSequence(String sequence) {
    this.sequence = sequence;
  }
  
  public void setTransition(Transition transition) {
    this.transition = transition;
  }
}
