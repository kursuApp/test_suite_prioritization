package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.util.ArrayList;

public abstract class AbstractSetsConstructor {
  protected FiniteStateMachine fsm;
  
  protected ArrayList<String> stateCover;
  
  protected ArrayList<String> transitionCover;
  
  public AbstractSetsConstructor(FiniteStateMachine fsm) {
    this.fsm = fsm;
    construct();
  }
  
  public abstract void construct();
  
  public ArrayList<String> getStateCover() {
    return this.stateCover;
  }
  
  public ArrayList<String> getTransitionCover() {
    return this.transitionCover;
  }
}
