package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.TestingTreeSetsConstructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

public class HeuHsiMethod {
  public static String ENABLEDINPUT = "enabledInputs";
  
  private static Logger logger = Logger.getAnonymousLogger();
  
  FiniteStateMachine fsm;
  
  ArrayList<String> sequences;
  
  ArrayList<String> noEnabledInputsequences;
  
  int traversalSize = 1;
  
  public static Logger getLogger() {
    return logger;
  }
  
  public void setTraversalSize(int traversalSize) {
    this.traversalSize = traversalSize;
  }
  
  public HeuHsiMethod(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public ArrayList<String> getSequences() {
    return this.sequences;
  }
  
  public ArrayList<String> getNoEnabledInputsequences() {
    return this.noEnabledInputsequences;
  }
  
  public void generate() {
    this.sequences = new ArrayList<String>();
    TestingTreeSetsConstructor ttc = new TestingTreeSetsConstructor(this.fsm);
    ArrayList<String> P = ttc.getTransitionCover();
    logger.info("P: " + P);
    HashMap<State, HashSet<String>> hsi_sets = defineSpecialHsiSets();
    logger.info("NO ENABLED INPUTS");
    this.noEnabledInputsequences = new ArrayList<String>();
    P = removeEnabledInput(P);
    logger.info("P: " + P);
    hsi_sets = removeEnabledInput(hsi_sets);
    for (String ts : P) {
      State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), ts);
      HashSet<String> hi = hsi_sets.get(si);
      for (String seqh : hi)
        this.noEnabledInputsequences.add(TestSequence.concat(ts, seqh)); 
    } 
    this.noEnabledInputsequences = TestSequence.getNoPrefixes(this.noEnabledInputsequences);
  }
  
  public void generateN1() {
    this.sequences = new ArrayList<String>();
    TestingTreeSetsConstructor ttc = new TestingTreeSetsConstructor(this.fsm);
    ArrayList<String> P = ttc.getTransitionCover();
    ArrayList<String> Q = ttc.getStateCover();
    logger.info("First P: " + P.size());
    for (int i = 1; i <= this.traversalSize; i++)
      P = appendTraversalSet(P); 
    logger.info("P: " + P);
    logger.info("P: " + P.size());
    HashMap<State, HashSet<String>> hsi_sets = defineSpecialHsiSets();
    logger.info("NO ENABLED INPUTS");
    this.noEnabledInputsequences = new ArrayList<String>();
    P = removeEnabledInput(P);
    logger.info("P: " + P);
    hsi_sets = removeEnabledInput(hsi_sets);
    Q = removeEnabledInput(Q);
    stats(hsi_sets, Q);
    logger.info("P.I^k.HSI");
    for (String ts : P) {
      State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), ts);
      HashSet<String> hi = hsi_sets.get(si);
      for (String seqh : hi) {
        String newsequence = TestSequence.concat(ts, seqh);
        if (!this.noEnabledInputsequences.contains(newsequence))
          this.noEnabledInputsequences.add(newsequence); 
      } 
    } 
    logger.info("Removing prefixes");
    this.noEnabledInputsequences = TestSequence.getNoPrefixes(this.noEnabledInputsequences);
  }
  
  private void stats(HashMap<State, HashSet<String>> hsi_sets, ArrayList<String> P) {
    float numOfSequences = 0.0F, max = 0.0F, sum = 0.0F;
    for (State si : this.fsm.getStates()) {
      Set<String> hsi = hsi_sets.get(si);
      System.out.println(si + "-> " + hsi);
      for (String seq : hsi) {
        if (TestSequence.lenght(seq) != 0) {
          sum += TestSequence.lenght(seq);
          numOfSequences++;
          if (TestSequence.lenght(seq) > max)
            max = TestSequence.lenght(seq); 
        } 
      } 
    } 
    int maxDiam = 0;
    for (String seq : P) {
      if (maxDiam < TestSequence.lenght(seq))
        maxDiam = TestSequence.lenght(seq); 
    } 
    System.out.println("max: " + max);
    System.out.println("avg: " + (sum / numOfSequences));
    System.out.println("maxDiam: " + maxDiam);
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
  
  private HashMap<State, HashSet<String>> removeEnabledInput(HashMap<State, HashSet<String>> sets) {
    for (HashSet<String> hi : sets.values()) {
      hi.remove(ENABLEDINPUT);
      if (hi.size() == 0)
        hi.add(TestSequence.EPSILON); 
    } 
    for (State si : this.fsm.getStates()) {
      HashSet<String> hsi = sets.get(si);
      logger.info("State: " + si + ", Hi: " + hsi);
    } 
    return sets;
  }
  
  private ArrayList<String> removeEnabledInput(ArrayList<String> set) {
    LinkedHashSet<String> toRemove = new LinkedHashSet<String>();
    for (String seq : set) {
      if (seq.contains(ENABLEDINPUT))
        toRemove.add(seq); 
    } 
    for (String rseq : toRemove)
      set.remove(rseq); 
    return set;
  }
  
  protected HashMap<State, HashSet<String>> defineSpecialHsiSets() {
    HashMap<State, HashSet<String>> map = new HashMap<State, HashSet<String>>();
    for (State si : this.fsm.getStates()) {
      HashSet<String> hsi = new HashSet<String>();
      for (State sj : this.fsm.getStates()) {
        if (si != sj) {
          String dSeq = findSequenceNoEnabledEvents(si, sj);
          if (dSeq != null) {
            hsi.add(dSeq);
            continue;
          } 
          dSeq = findSequenceWithEnabledEvents(si, sj);
          dSeq.toString();
          hsi.add(dSeq);
        } 
      } 
      logger.info("State: " + si + ", Hi: " + hsi);
      map.put(si, hsi);
    } 
    return map;
  }
  
  protected String findSequenceWithEnabledEvents(State si, State sj) {
    if (this.fsm.separe(ENABLEDINPUT, si, sj))
      return ENABLEDINPUT; 
    logger.warning("ERROR: non-minimal FSM");
    return null;
  }
  
  protected String findSequenceNoEnabledEvents(State si, State sj) {
    int n = this.fsm.getNumberOfStates();
    ArrayList<String> queue = new ArrayList<String>();
    HashMap<String, String> visitedPairs = new HashMap<String, String>();
    for (String x : this.fsm.getInputAlphabet()) {
      if (this.fsm.isDefinedSeq(x, si) && this.fsm.isDefinedSeq(x, sj))
        queue.add(x); 
    } 
    queue.remove(ENABLEDINPUT);
    while (!queue.isEmpty()) {
      String seq = queue.remove(0);
      if (this.fsm.separe(seq, si, sj))
        return seq; 
      State reachedFromSi = this.fsm.nextStateWithSequence(si, seq);
      State reachedFromSj = this.fsm.nextStateWithSequence(sj, seq);
      String pairKey = String.valueOf(reachedFromSi.getLabel()) + "$$" + reachedFromSj.getLabel();
      if (reachedFromSi != reachedFromSj && visitedPairs.get(pairKey) == null && 
        TestSequence.lenght(seq) <= n * (n - 1) / 2) {
        visitedPairs.put(pairKey, "ok");
        for (String x : this.fsm.getInputAlphabet()) {
          if (!x.equals(ENABLEDINPUT)) {
            String newseq = TestSequence.concat(seq, x);
            if (this.fsm.isDefinedSeq(newseq, si) && this.fsm.isDefinedSeq(newseq, sj))
              queue.add(newseq); 
          } 
        } 
      } 
    } 
    return null;
  }
}
