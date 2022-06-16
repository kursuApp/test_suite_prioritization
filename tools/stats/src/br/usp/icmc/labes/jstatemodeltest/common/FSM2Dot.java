package br.usp.icmc.labes.jstatemodeltest.common;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;

public class FSM2Dot {
  FiniteStateMachine fsm;
  
  public FSM2Dot(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public void printDot() {
    System.out.println("digraph fsm {");
    System.out.println("rankdir=LR;");
    System.out.println("node [shape = doublecircle]; \"" + this.fsm.getInitialState() + "\";");
    System.out.println("node [shape = circle];");
    for (Transition t : this.fsm.getTransitions())
      System.out.println("\"" + t.getIn() + "\" -> \"" + t.getOut() + "\" [ label = \"" + t.getInput() + " / " + t.getOutput() + "\" ];"); 
    System.out.println("}");
  }
}
