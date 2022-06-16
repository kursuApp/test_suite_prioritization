package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.generation;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.EquivalentMutantDetector;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.MutantGenerator;
import java.util.ArrayList;

public class MutantBasedTestGeneration {
  FiniteStateMachine fsm;
  
  ArrayList<String> testSuite;
  
  public MutantBasedTestGeneration(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public ArrayList<String> getTestSuite() {
    return this.testSuite;
  }
  
  public void generate() {
    this.testSuite = new ArrayList<String>();
    MutantGenerator mGen = new MutantGenerator(this.fsm);
    for (FiniteStateMachine mutant : mGen.operatorCIS(this.fsm)) {
      boolean killed = executeMutant(mutant, this.testSuite);
      if (!killed) {
        String seq = EquivalentMutantDetector.findSeparationSequence(this.fsm, mutant);
        if (seq != null)
          if (!this.testSuite.contains(seq))
            this.testSuite.add(seq);  
      } 
    } 
    for (Transition t : this.fsm.getTransitions()) {
      for (FiniteStateMachine mutant : mGen.operatorCO(this.fsm, t)) {
        boolean killed = executeMutant(mutant, this.testSuite);
        if (!killed) {
          String seq = EquivalentMutantDetector.findSeparationSequence(this.fsm, mutant);
          if (seq != null)
            if (!this.testSuite.contains(seq))
              this.testSuite.add(seq);  
        } 
      } 
      for (FiniteStateMachine mutant : mGen.operatorTF(this.fsm, t)) {
        boolean killed = executeMutant(mutant, this.testSuite);
        if (!killed) {
          String seq = EquivalentMutantDetector.findSeparationSequence(this.fsm, mutant);
          if (seq != null)
            if (!this.testSuite.contains(seq))
              this.testSuite.add(seq);  
        } 
      } 
    } 
    this.testSuite = TestSequence.getNoPrefixes(this.testSuite);
  }
  
  public boolean executeMutant(FiniteStateMachine mutant, ArrayList<String> testSuite) {
    for (String testCase : testSuite) {
      String mutantOutput = mutant.output(testCase);
      String originalOutput = this.fsm.output(testCase);
      if (!mutantOutput.equals(originalOutput))
        return true; 
    } 
    return false;
  }
}
