package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import java.util.HashSet;

public class FsmMinimization {
  FiniteStateMachine fsm;
  
  public FsmMinimization(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public void minimize() {
    boolean again = false;
    for (int i = 0; i < this.fsm.getStates().size(); i++) {
      boolean go = true;
      State si = this.fsm.getStates().get(i);
      for (int j = i + 1; j < this.fsm.getStates().size(); j++) {
        State sj = this.fsm.getStates().get(j);
        String distseq = this.fsm.distiguishSeqN(si, sj);
        if (distseq == null && toSameStates(si, sj)) {
          System.out.println("(" + si + "," + sj + ") -> " + distseq);
          go = false;
          merge(si, sj);
          break;
        } 
      } 
      if (!go) {
        again = true;
        break;
      } 
    } 
    if (again)
      minimize(); 
  }
  
  private boolean toSameStates(State si, State sj) {
    for (Transition ti : si.getOut()) {
      if (!ti.getInput().equals("enabledInputs"))
        for (Transition tj : sj.getOut()) {
          if (tj.getInput().equals(ti.getInput())) {
            if (ti.getOut() != tj.getOut())
              return false; 
            break;
          } 
        }  
    } 
    return true;
  }
  
  private void merge(State si, State sj) {
    si.setLabel(String.valueOf(si.getLabel()) + "_" + sj.getLabel());
    this.fsm.getStates().remove(sj);
    HashSet<Transition> toRemove = new HashSet<Transition>();
    for (Transition t : this.fsm.getTransitions()) {
      if (t.getOut() == sj)
        t.setOut(si); 
      if (t.getIn() == sj)
        toRemove.add(t); 
    } 
    for (Transition t : toRemove)
      this.fsm.getTransitions().remove(t); 
  }
}
