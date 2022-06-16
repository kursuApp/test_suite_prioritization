package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.CharacterizationSetConstructor;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.TestingTreeSetsConstructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WMethod {
  FiniteStateMachine fsm;
  
  ArrayList<String> sequences;
  
  private static Logger logger = Logger.getAnonymousLogger();
  
  int traversal = 0;
  
  public WMethod(FiniteStateMachine fsm, int traversal) {
    this.traversal = traversal;
    this.fsm = fsm;
    generateSequences();
  }
  
  public WMethod(FiniteStateMachine fsm) {
    this.fsm = fsm;
    generateSequences();
  }
  
  public void setTraversal(int traversal) {
    this.traversal = traversal;
  }
  
  public int getTraversal() {
    return this.traversal;
  }
  
  public ArrayList<String> getSequences() {
    return this.sequences;
  }
  
  private void generateSequences() {
    logger.setLevel(Level.ALL);
    this.sequences = new ArrayList<String>();
    TestingTreeSetsConstructor ttc = new TestingTreeSetsConstructor(this.fsm);
    ArrayList<String> P = ttc.getTransitionCover();
    logger.info("P: " + P);
    CharacterizationSetConstructor csconst = new CharacterizationSetConstructor(this.fsm);
    ArrayList<String> W = csconst.getWset();
    logger.info("W: " + W);
    ArrayList<String> Z = W;
    for (int i = 1; i <= this.traversal; i++) {
      Set<String> Xi = SymbolOperator.power(this.fsm.getInputAlphabet(), i);
      Collection<String> Xi_W = SymbolOperator.concatenate(Xi, W);
      Z = SymbolOperator.union(Z, Xi_W);
    } 
    logger.info("Z: " + Z);
    this.sequences = (ArrayList<String>)SymbolOperator.concatenate(P, Z);
    this.sequences = getSequencesWithoutPrefixes();
  }
  
  public ArrayList<String> getSequencesWithoutPrefixes() {
    ArrayList<String> noPrefixes = new ArrayList<String>();
    for (String s : this.sequences) {
      boolean isPrefix = false;
      for (String pref : this.sequences) {
        if (!s.equals(pref) && pref.startsWith(s)) {
          isPrefix = true;
          break;
        } 
      } 
      if (!isPrefix)
        noPrefixes.add(s); 
    } 
    return noPrefixes;
  }
}
