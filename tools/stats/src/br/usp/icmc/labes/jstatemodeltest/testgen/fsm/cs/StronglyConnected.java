package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.io.File;
import java.util.ArrayList;

public class StronglyConnected {
  public StronglyConnected(FiniteStateMachine fsm) {
    for (State si : fsm.getStates()) {
      for (State sj : fsm.getStates()) {
        if (si != sj) {
          ArrayList<String> queue = new ArrayList<String>();
          queue.add(TestSequence.EPSILON);
          while (queue.size() > 0) {
            String curr = queue.remove(0);
            if (fsm.isDefinedSeq(curr, si) && fsm.isDefinedSeq(curr, sj) && fsm.nextStateWithSequence(si, curr) == sj)
              break; 
            for (String x : fsm.getInputAlphabet())
              queue.add(TestSequence.concat(curr, x)); 
            if (TestSequence.lenght(curr) > fsm.getNumberOfStates() + 2) {
              System.out.println("wwee");
              break;
            } 
          } 
        } 
      } 
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
        StronglyConnected stronglyConnected = new StronglyConnected(fsm);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
