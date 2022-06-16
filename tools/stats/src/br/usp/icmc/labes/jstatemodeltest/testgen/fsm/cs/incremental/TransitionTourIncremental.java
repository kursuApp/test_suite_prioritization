package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs.incremental;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs.DistinguishingSequenceConstructor;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs.PCheckingSequenceGenerator;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.transitiontour.TransitionTour;
import java.io.File;

public class TransitionTourIncremental {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("No FSM file specified.");
    } else {
      File file = new File(args[0]);
      FsmModelReader reader = new FsmModelReader(file, true);
      try {
        FiniteStateMachine fsm = reader.getFsm();
        TransitionTour tt = new TransitionTour(fsm);
        String ttSeq = tt.getTestset().get(0);
        if (!DistinguishingSequenceConstructor.canBeInitializedCheckingSequence(fsm, ttSeq)) {
          System.out.println("a");
        } else {
          PCheckingSequenceGenerator gen = new PCheckingSequenceGenerator(fsm);
          gen.setUserSequence(ttSeq);
          gen.generate2();
          System.out.println(gen.getCheckingSequence().replace(",", ""));
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
