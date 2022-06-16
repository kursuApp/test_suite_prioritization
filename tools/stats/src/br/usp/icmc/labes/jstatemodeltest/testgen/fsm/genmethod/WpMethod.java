package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.CharacterizationSetConstructor;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.TestingTreeSetsConstructor;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi.TraditionalHsiMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WpMethod {
  FiniteStateMachine fsm;
  
  ArrayList<String> sequences;
  
  private static Logger logger = Logger.getAnonymousLogger();
  
  public WpMethod(FiniteStateMachine fsm) {
    this.fsm = fsm;
    generateSequences();
  }
  
  public ArrayList<String> getSequences() {
    return this.sequences;
  }
  
  private void generateSequences() {
    logger.setLevel(Level.OFF);
    this.sequences = new ArrayList<String>();
    TestingTreeSetsConstructor ttc = new TestingTreeSetsConstructor(this.fsm);
    ArrayList<String> P = ttc.getTransitionCover();
    logger.info("P: " + P);
    ArrayList<String> Q = ttc.getStateCover();
    logger.info("Q: " + Q);
    ArrayList<String> R = TestSet.minus(P, Q);
    logger.info("R: " + R);
    CharacterizationSetConstructor csconst = new CharacterizationSetConstructor(this.fsm);
    ArrayList<String> W = csconst.getWset();
    logger.info("W: " + W);
    HashMap<State, ArrayList<String>> Wis = produceWis();
    logger.info("Wi: " + Wis);
    for (String seq : Q) {
      for (String dif : W) {
        String newseq = TestSequence.concat(seq, dif);
        this.sequences.add(newseq);
      } 
    } 
    for (String seq : R) {
      State sj = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), seq);
      ArrayList<String> Wi = Wis.get(sj);
      for (String dif : Wi) {
        String newseq = TestSequence.concat(seq, dif);
        this.sequences.add(newseq);
      } 
    } 
    this.sequences = TestSequence.getNoPrefixes(this.sequences);
  }
  
  private HashMap<State, ArrayList<String>> produceWis() {
    HashMap<State, ArrayList<String>> ret = new HashMap<State, ArrayList<String>>();
    TraditionalHsiMethod hsi = new TraditionalHsiMethod(this.fsm);
    for (State si : this.fsm.getStates()) {
      ArrayList<String> wi = new ArrayList<String>();
      for (State sj : this.fsm.getStates()) {
        if (si != sj && !this.fsm.separe(wi, si, sj)) {
          String newseq = hsi.findSequence(si, sj);
          wi.add(newseq);
        } 
      } 
      ret.put(si, wi);
    } 
    for (State si : this.fsm.getStates()) {
      ArrayList<String> wi = ret.get(si);
      wi = getShorterSet(si, wi);
      ret.put(si, wi);
    } 
    return ret;
  }
  
  private ArrayList<String> getShorterSet(State si, ArrayList<String> wi) {
    if (wi.size() == 1)
      return wi; 
    ArrayList<ArrayList<String>> sets = new ArrayList<ArrayList<String>>();
    for (String seq : wi) {
      ArrayList<String> set1 = new ArrayList<String>();
      set1.add(seq);
      sets.add(set1);
    } 
    for (int i = 2; i <= wi.size(); i++) {
      for (ArrayList<String> set1 : sets) {
        if (set1.size() == i - 1)
          for (String seq : wi) {
            if (!set1.contains(seq)) {
              ArrayList<String> newset = new ArrayList<String>(set1);
              newset.add(seq);
            } 
          }  
      } 
    } 
    for (ArrayList<String> set1 : sets) {
      if (this.fsm.isWi(si, set1))
        return set1; 
    } 
    return wi;
  }
}
