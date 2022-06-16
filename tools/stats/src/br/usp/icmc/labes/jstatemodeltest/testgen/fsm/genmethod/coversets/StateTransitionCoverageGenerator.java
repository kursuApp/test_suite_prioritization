package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.coversets;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.TestingTreeSetsConstructor;
import java.util.ArrayList;

public class StateTransitionCoverageGenerator {
  FiniteStateMachine fsm;
  
  ArrayList<String> stateCoverageTestSuite;
  
  ArrayList<String> transitionCoverageTestSuite;
  
  public StateTransitionCoverageGenerator(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public void generate() {
    TestingTreeSetsConstructor ttsc = new TestingTreeSetsConstructor(this.fsm);
    this.stateCoverageTestSuite = ttsc.getStateCover();
    this.stateCoverageTestSuite = TestSequence.getNoPrefixes(this.stateCoverageTestSuite);
    this.transitionCoverageTestSuite = ttsc.getTransitionCover();
    this.transitionCoverageTestSuite = TestSequence.getNoPrefixes(this.transitionCoverageTestSuite);
  }
  
  public ArrayList<String> getStateCoverageTestSuite() {
    return this.stateCoverageTestSuite;
  }
  
  public ArrayList<String> getTransitionCoverageTestSuite() {
    return this.transitionCoverageTestSuite;
  }
}
