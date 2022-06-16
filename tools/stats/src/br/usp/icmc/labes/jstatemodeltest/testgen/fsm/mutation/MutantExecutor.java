package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class MutantExecutor {
  protected FiniteStateMachine original;
  
  HashMap<String, String> origOutForTest;
  
  double killed;
  
  double alive;
  
  double total;
  
  double score;
  
  public double getAlive() {
    return this.alive;
  }
  
  public double getKilled() {
    return this.killed;
  }
  
  public double getTotal() {
    return this.total;
  }
  
  public double getScore() {
    return this.score;
  }
  
  public MutantExecutor(FiniteStateMachine original) {
    this.original = original;
  }
  
  public void executeMutants(LinkedHashSet<FiniteStateMachine> mutants, ArrayList<String> testSuite) {
    this.total = mutants.size();
    executeOriginal(testSuite);
    executeEachMutant(mutants, testSuite);
  }
  
  private void executeEachMutant(LinkedHashSet<FiniteStateMachine> mutants, ArrayList<String> testSuite) {
    this.killed = 0.0D;
    for (FiniteStateMachine mutant : mutants) {
      if (executeMutant(mutant, testSuite))
        this.killed++; 
    } 
    this.alive = this.total - this.killed;
    this.score = this.killed / this.total;
  }
  
  public boolean executeMutant(FiniteStateMachine mutant, ArrayList<String> testSuite) {
    for (String testCase : testSuite) {
      String mutantOutput = mutant.output(testCase);
      String originalOutput = this.origOutForTest.get(testCase);
      if (!mutantOutput.equals(originalOutput))
        return true; 
    } 
    return false;
  }
  
  public void executeOriginal(ArrayList<String> testSuite) {
    this.origOutForTest = new HashMap<String, String>();
    for (String testCase : testSuite)
      this.origOutForTest.put(testCase, this.original.output(testCase)); 
  }
  
  public void executeMutantsOperatorTF(MutantGenerator mutantGen, ArrayList<String> testsuite) {
    executeOriginal(testsuite);
    int tmp_total = 0, tmp_killed = 0;
    for (Transition t : this.original.getTransitions()) {
      LinkedHashSet<FiniteStateMachine> tmpMutants = mutantGen.operatorTF(this.original, t);
      tmp_total += tmpMutants.size();
      executeEachMutant(tmpMutants, testsuite);
      tmp_killed = (int)(tmp_killed + this.killed);
    } 
    this.total = tmp_total;
    this.killed = tmp_killed;
    this.alive = this.total - this.killed;
    this.score = this.killed / this.total;
  }
  
  public void executeMutantsOperatorOEAND(MutantGenerator mutantGen, ArrayList<String> testsuite) {
    executeOriginal(testsuite);
    int tmp_total = 0, tmp_killed = 0;
    for (Transition t : this.original.getTransitions()) {
      LinkedHashSet<FiniteStateMachine> tmpMutants = mutantGen.operatorOEAND(this.original, t);
      tmp_total += tmpMutants.size();
      executeEachMutant(tmpMutants, testsuite);
      tmp_killed = (int)(tmp_killed + this.killed);
    } 
    this.total = tmp_total;
    this.killed = tmp_killed;
    this.alive = this.total - this.killed;
    this.score = this.killed / this.total;
  }
  
  public void executeMutantsOperatorCO(MutantGenerator mutantGen, ArrayList<String> testsuite) {
    executeOriginal(testsuite);
    int tmp_total = 0, tmp_killed = 0;
    for (Transition t : this.original.getTransitions()) {
      LinkedHashSet<FiniteStateMachine> tmpMutants = mutantGen.operatorCO(this.original, t);
      tmp_total += tmpMutants.size();
      executeEachMutant(tmpMutants, testsuite);
      tmp_killed = (int)(tmp_killed + this.killed);
    } 
    this.total = tmp_total;
    this.killed = tmp_killed;
    this.alive = this.total - this.killed;
    this.score = this.killed / this.total;
  }
}
