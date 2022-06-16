package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

public class FSMStats {
  FiniteStateMachine fsm;
  
  public FSMStats(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public void print() {
    float incTrans = 0.0F;
    float outTrans = 0.0F;
    System.out.println("State,inc,out");
    for (State si : this.fsm.getStates()) {
      System.out.println(si + "," + si.getIn().size() + "," + si.getOut().size());
      incTrans += si.getIn().size();
      outTrans += si.getOut().size();
    } 
    incTrans /= this.fsm.getNumberOfStates();
    outTrans /= this.fsm.getNumberOfStates();
    System.out.println("Average incoming transitions: " + incTrans);
    System.out.println("Average outgoing transitions: " + outTrans);
  }
}
