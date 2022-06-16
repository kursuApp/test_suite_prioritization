package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.transitiontour;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.io.File;

public class TransitionTourToInitial extends TransitionTour {
  public TransitionTourToInitial(FiniteStateMachine fsm) {
    super(fsm);
    bringToinitialState();
  }
  
  private void bringToinitialState() {
    String ttSeq = getTestset().get(0);
    State currentState = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), ttSeq);
    if (currentState != this.fsm.getInitialState()) {
      String transferSeq = findPathBetween(currentState, this.fsm.getInitialState());
      ttSeq = TestSequence.concat(ttSeq, transferSeq);
      getTestset().remove(0);
      getTestset().add(ttSeq);
    } 
  }
  
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("No FSM file specified.");
    } else {
      File file = new File(args[0]);
      FsmModelReader reader = new FsmModelReader(file, true);
      try {
        FiniteStateMachine fsm = reader.getFsm();
        TransitionTourToInitial tt = new TransitionTourToInitial(fsm);
        System.out.println(((String)tt.getTestset().get(0)).replace(",", ""));
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
