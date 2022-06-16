package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.TestingTreeSetsConstructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TraditionalHsiMethod {
  FiniteStateMachine fsm;
  
  ArrayList<String> sequences;
  
  int traversalSize = 1;
  
  public void setTraversalSize(int traversalSize) {
    this.traversalSize = traversalSize;
  }
  
  public TraditionalHsiMethod(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public ArrayList<String> getSequences() {
    return this.sequences;
  }
  
  public void generateN1() {
    this.sequences = new ArrayList<String>();
    TestingTreeSetsConstructor ttc = new TestingTreeSetsConstructor(this.fsm);
    ArrayList<String> P = ttc.getTransitionCover();
    for (int i = 1; i <= this.traversalSize; i++)
      P = appendTraversalSet(P); 
    HashMap<State, HashSet<String>> hsi_sets = defineSpecialHsiSets();
    for (String ts : P) {
      State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), ts);
      HashSet<String> hi = hsi_sets.get(si);
      for (String seqh : hi) {
        String newsequence = TestSequence.concat(ts, seqh);
        if (!this.sequences.contains(newsequence))
          this.sequences.add(newsequence); 
      } 
    } 
    this.sequences = TestSequence.getNoPrefixes(this.sequences);
  }

  public void generateAtlas(ArrayList<String> P ) {
    this.sequences = new ArrayList<>();
    HashMap<State, HashSet<String>> hsi_sets = defineSpecialHsiSets();
    for (String ts : P) {
      State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), ts);
      HashSet<String> hi = hsi_sets.get(si);
      for (String seqh : hi) {
        String newsequence = TestSequence.concat(ts, seqh);
        if (!this.sequences.contains(newsequence))
          this.sequences.add(newsequence);
      }
    }
    this.sequences = TestSequence.getNoPrefixes(this.sequences);
  }
  
  private ArrayList<String> appendTraversalSet(ArrayList<String> P) {
    ArrayList<String> tempP = new ArrayList<String>(P);
    for (String ts : P) {
      State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), ts);
      for (Transition t : si.getOut()) {
        String newSeq = TestSequence.concat(ts, t.getInput());
        if (!tempP.contains(newSeq))
          tempP.add(newSeq); 
      } 
    } 
    return tempP;
  }
  
  protected HashMap<State, HashSet<String>> defineSpecialHsiSets() {
    HashMap<State, HashSet<String>> map = new HashMap<State, HashSet<String>>();
    for (State si : this.fsm.getStates()) {
      HashSet<String> hsi = new HashSet<String>();
      for (State sj : this.fsm.getStates()) {
        if (si != sj) {
          String dSeq = findSequence(si, sj);
          if (dSeq != null) {
            hsi.add(dSeq);
            continue;
          } 
          System.out.println("ERROR: Non-reduced machine");
        } 
      } 
      map.put(si, hsi);
    } 
    return map;
  }
  
  public String findSequence(State si, State sj) {
    int n = this.fsm.getNumberOfStates();
    ArrayList<String> queue = new ArrayList<String>();
    HashMap<String, String> visitedPairs = new HashMap<String, String>();
    for (String x : this.fsm.getInputAlphabet()) {
      if (this.fsm.isDefinedSeq(x, si) && this.fsm.isDefinedSeq(x, sj))
        queue.add(x); 
    } 
    while (!queue.isEmpty()) {
      String seq = queue.remove(0);
      if (this.fsm.separe(seq, si, sj))
        return seq; 
      State reachedFromSi = this.fsm.nextStateWithSequence(si, seq);
      State reachedFromSj = this.fsm.nextStateWithSequence(sj, seq);
      String pairKey = reachedFromSi.getLabel() + "$$" + reachedFromSj.getLabel();
      if (reachedFromSi != reachedFromSj && visitedPairs.get(pairKey) == null && 
        TestSequence.lenght(seq) <= n * (n - 1) / 2) {
        visitedPairs.put(pairKey, "ok");
        for (String x : this.fsm.getInputAlphabet()) {
          String newseq = TestSequence.concat(seq, x);
          if (this.fsm.isDefinedSeq(newseq, si) && this.fsm.isDefinedSeq(newseq, sj))
            queue.add(newseq); 
        } 
      } 
    } 
    return null;
  }
}
