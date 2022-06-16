package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.util.ArrayList;
import java.util.HashMap;

public class EquivalentMutantDetector {
  public static boolean isEquivalent(FiniteStateMachine fsm1, FiniteStateMachine fsm2) {
    String dseq = findSeparationSequence(fsm1, fsm2);
    return (dseq == null);
  }
  
  public static String findSeparationSequence(FiniteStateMachine fsm1, FiniteStateMachine fsm2) {
    int numberOfStatesM1 = fsm1.getNumberOfStates();
    int numberOfStatesM2 = fsm2.getNumberOfStates();
    int n = numberOfStatesM1;
    if (numberOfStatesM2 > n)
      n = numberOfStatesM2; 
    State st1 = fsm1.getInitialState();
    State st2 = fsm2.getInitialState();
    ArrayList<String> queue = new ArrayList<String>();
    HashMap<String, String> visitedPairs = new HashMap<String, String>();
    for (String x : fsm1.getInputAlphabet())
      queue.add(x); 
    while (!queue.isEmpty()) {
      String seq = queue.remove(0);
      if (!fsm1.output(seq).equals(fsm2.output(seq)))
        return seq; 
      State reachedFromSi = fsm1.nextStateWithSequence(st1, seq);
      State reachedFromSj = fsm2.nextStateWithSequence(st2, seq);
      if (reachedFromSi == null && reachedFromSj == null)
        continue; 
      String pairKey = String.valueOf(reachedFromSi.getLabel()) + "$$" + reachedFromSj.getLabel();
      if (visitedPairs.get(pairKey) == null && TestSequence.lenght(seq) <= n * (n - 1) / 2) {
        visitedPairs.put(pairKey, "ok");
        for (String x : fsm1.getInputAlphabet()) {
          String newseq = TestSequence.concat(seq, x);
          queue.add(newseq);
        } 
      } 
    } 
    return null;
  }
}
