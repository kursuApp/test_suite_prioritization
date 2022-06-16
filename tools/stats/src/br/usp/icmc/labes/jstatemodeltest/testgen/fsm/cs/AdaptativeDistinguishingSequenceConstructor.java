package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs.datastructure.SequenceStatesPair;
import java.util.ArrayList;
import java.util.HashMap;

public class AdaptativeDistinguishingSequenceConstructor {
  private FiniteStateMachine fsm;
  
  private HashMap<State, String> adaptativeDS;
  
  public AdaptativeDistinguishingSequenceConstructor(FiniteStateMachine fsm) {
    this.fsm = fsm;
    build();
  }
  
  public HashMap<State, String> getAdaptativeDS() {
    return this.adaptativeDS;
  }
  
  private void build() {
    ArrayList<HashMap<String, SequenceStatesPair>> queue = new ArrayList<HashMap<String, SequenceStatesPair>>();
    HashMap<String, SequenceStatesPair> pairs = new HashMap<String, SequenceStatesPair>();
    SequenceStatesPair pair = new SequenceStatesPair(TestSequence.EPSILON);
    for (State si : this.fsm.getStates())
      pair.addState(si); 
    pairs.put(TestSequence.EPSILON, pair);
    queue.add(pairs);
    while (queue.size() > 0) {
      pairs = queue.remove(0);
      if (checkPossibleADS(pairs)) {
        if (isUnique(pairs)) {
          this.adaptativeDS = new HashMap<State, String>();
          for (SequenceStatesPair sp : pairs.values()) {
            State si = sp.getStates().get(0);
            this.adaptativeDS.put(si, sp.getSequence());
          } 
          break;
        } 
        if (pairs.values().size() == 1) {
          for (String x : this.fsm.getInputAlphabet()) {
            HashMap<String, SequenceStatesPair> newpairs = createPair(x);
            queue.add(newpairs);
          } 
          continue;
        } 
        for (SequenceStatesPair sspair : pairs.values()) {
          if (sspair.getStates().size() > 1) {
            for (String x : this.fsm.getInputAlphabet()) {
              HashMap<String, SequenceStatesPair> newpairs = createPair(pairs, sspair, x);
              queue.add(newpairs);
            } 
            break;
          } 
        } 
      } 
    } 
  }
  
  private HashMap<String, SequenceStatesPair> createPair(HashMap<String, SequenceStatesPair> pairs, SequenceStatesPair sspair, String x) {
    HashMap<String, SequenceStatesPair> newpairs = new HashMap<String, SequenceStatesPair>(pairs);
    newpairs.values().remove(sspair);
    String newseq = TestSequence.concat(sspair.getSequence(), x);
    for (State si : sspair.getStates()) {
      String siout = this.fsm.nextOutput(si, newseq);
      SequenceStatesPair temppair = newpairs.get(siout);
      if (temppair == null) {
        temppair = new SequenceStatesPair(newseq);
        temppair.addState(si);
        newpairs.put(siout, temppair);
        continue;
      } 
      temppair.addState(si);
    } 
    return newpairs;
  }
  
  private HashMap<String, SequenceStatesPair> createPair(String x) {
    HashMap<String, SequenceStatesPair> ret = new HashMap<String, SequenceStatesPair>();
    for (State si : this.fsm.getStates()) {
      String siout = this.fsm.nextOutput(si, x);
      SequenceStatesPair pair = ret.get(siout);
      if (pair == null) {
        pair = new SequenceStatesPair(x);
        pair.addState(si);
        ret.put(siout, pair);
        continue;
      } 
      pair.addState(si);
    } 
    return ret;
  }
  
  private boolean checkPossibleADS(HashMap<String, SequenceStatesPair> pairs) {
    boolean ok = true;
    for (SequenceStatesPair p : pairs.values()) {
      if (!DistinguishingSequenceConstructor.canBeDs(this.fsm, p.getSequence())) {
        ok = false;
        break;
      } 
    } 
    return ok;
  }
  
  private boolean isUnique(HashMap<String, SequenceStatesPair> pairs) {
    for (SequenceStatesPair p : pairs.values()) {
      if (p.getStates().size() != 1)
        return false; 
    } 
    return true;
  }
}
