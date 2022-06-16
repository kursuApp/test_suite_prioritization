package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.util.ArrayList;

public class DistinguishingSequenceConstructor {
  FiniteStateMachine fsm;
  
  String ds;
  
  DistinguishingSequenceConstructor(FiniteStateMachine fsm) {
    this.fsm = fsm;
    findDS();
  }
  
  public String getDs() {
    return this.ds;
  }
  
  private void findDS() {
    ArrayList<String> queue = new ArrayList<String>();
    queue.add(TestSequence.EPSILON);
    this.ds = "NULL";
    while (queue.size() >= 1) {
      String pds = queue.remove(0);
      if (isDS(this.fsm, pds)) {
        this.ds = pds;
        break;
      } 
      if (canBeDs(this.fsm, pds))
        for (String x : this.fsm.getInputAlphabet()) {
          String temp = TestSequence.concat(pds, x);
          queue.add(temp);
        }  
    } 
  }
  
  public static boolean canBeDs(FiniteStateMachine fsm, String sequence) {
    if (TestSequence.lenght(sequence) > fsm.getNumberOfStates() * 2)
      return false; 
    ArrayList<State> states = fsm.getStates();
    for (int i = 0; i < states.size() - 1; i++) {
      for (int j = i + 1; j < states.size(); j++) {
        State si = states.get(i);
        State sj = states.get(j);
        if (!fsm.separe(sequence, si, sj))
          if (fsm.nextStateWithSequence(si, sequence) == fsm.nextStateWithSequence(sj, sequence))
            return false;  
      } 
    } 
    return true;
  }
  
  public static boolean canBeInitializedCheckingSequence(FiniteStateMachine fsm, String sequence) {
    ArrayList<State> states = fsm.getStates();
    State s0 = fsm.getInitialState();
    for (int i = 0; i < states.size(); i++) {
      State si = states.get(i);
      if (s0 != si)
        if (!fsm.separe(sequence, s0, si))
          if (fsm.nextStateWithSequence(s0, sequence) == fsm.nextStateWithSequence(si, sequence))
            return false;   
    } 
    return true;
  }
  
  public static boolean isDS(FiniteStateMachine fsm, String sequence) {
    ArrayList<State> states = fsm.getStates();
    for (int i = 0; i < states.size() - 1; i++) {
      for (int j = i + 1; j < states.size(); j++) {
        if (!fsm.separe(sequence, states.get(i), states.get(j)))
          return false; 
      } 
    } 
    return true;
  }
}
