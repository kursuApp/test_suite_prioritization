package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.util.ArrayList;

public abstract class AbstractGenerator {
  FiniteStateMachine fsm;
  
  ArrayList<String> sequences;
  
  public AbstractGenerator(FiniteStateMachine fsm) {
    this.fsm = fsm;
    generateSequences();
  }
  
  public ArrayList<String> getSequences() {
    return this.sequences;
  }
  
  public abstract void generateSequences();
}
