package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class MutantEnvironment {
  private FiniteStateMachine fsm;
  
  private String log = "";
  
  public MutantEnvironment(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public String getLog() {
    return this.log;
  }
  
  public void execute(ArrayList<String> testsuite) {
    System.out.println("OPERATORS;LIVE-MUTANTS;KILLED-MUTANTS;TOTAL-MUTANTS;SCORE");
    double totalMutants = 0.0D, alive = 0.0D, killed = 0.0D;
    MutantGenerator mutantGen = new MutantGenerator(this.fsm);
    MutantExecutor mutantExec = new MutantExecutor(this.fsm);
    LinkedHashSet<FiniteStateMachine> mutants = mutantGen.operatorCIS(this.fsm);
    mutantExec.executeMutants(mutants, testsuite);
    double op_score = mutantExec.getScore();
    double op_live = mutantExec.getAlive();
    double op_killed = mutantExec.getKilled();
    double op_totalmut = mutantExec.getTotal();
    totalMutants += op_totalmut;
    alive += op_live;
    killed += op_killed;
    printOperator("CIS", op_live, op_killed, op_totalmut, op_score);
    mutants = mutantGen.operatorIEAND(this.fsm);
    mutantExec.executeMutants(mutants, testsuite);
    op_score = mutantExec.getScore();
    op_live = mutantExec.getAlive();
    op_killed = mutantExec.getKilled();
    op_totalmut = mutantExec.getTotal();
    totalMutants += op_totalmut;
    alive += op_live;
    killed += op_killed;
    printOperator("CI", op_live, op_killed, op_totalmut, op_score);
    mutants = mutantGen.operatorCO(this.fsm);
    mutantExec.executeMutants(mutants, testsuite);
    op_score = mutantExec.getScore();
    op_live = mutantExec.getAlive();
    op_killed = mutantExec.getKilled();
    op_totalmut = mutantExec.getTotal();
    totalMutants += op_totalmut;
    alive += op_live;
    killed += op_killed;
    printOperator("CO", op_live, op_killed, op_totalmut, op_score);
    mutants = mutantGen.operatorMT(this.fsm);
    mutantExec.executeMutants(mutants, testsuite);
    op_score = mutantExec.getScore();
    op_live = mutantExec.getAlive();
    op_killed = mutantExec.getKilled();
    op_totalmut = mutantExec.getTotal();
    totalMutants += op_totalmut;
    alive += op_live;
    killed += op_killed;
    printOperator("MT", op_live, op_killed, op_totalmut, op_score);
    mutantExec.executeMutantsOperatorTF(mutantGen, testsuite);
    op_score = mutantExec.getScore();
    op_live = mutantExec.getAlive();
    op_killed = mutantExec.getKilled();
    op_totalmut = mutantExec.getTotal();
    totalMutants += op_totalmut;
    alive += op_live;
    killed += op_killed;
    printOperator("TSE", op_live, op_killed, op_totalmut, op_score);
    mutantExec.executeMutantsOperatorOEAND(mutantGen, testsuite);
    op_score = mutantExec.getScore();
    op_live = mutantExec.getAlive();
    op_killed = mutantExec.getKilled();
    op_totalmut = mutantExec.getTotal();
    totalMutants += op_totalmut;
    alive += op_live;
    killed += op_killed;
    printOperator("HSE", op_live, op_killed, op_totalmut, op_score);
    printOperator("Total", alive, killed, totalMutants, killed / totalMutants);
  }
  
  public void executeOpt(HashMap<String, ArrayList<String>> testSuites) {
    System.out.println("METHOD;OPERATORS;LIVE-MUTANTS;KILLED-MUTANTS;TOTAL-MUTANTS;SCORE");
    this.log = "METHOD;OPERATORS;LIVE-MUTANTS;KILLED-MUTANTS;TOTAL-MUTANTS;SCORE\n";
    MutantGenerator mutantGen = new MutantGenerator(this.fsm);
    MutantExecutor mutantExec = new MutantExecutor(this.fsm);
    LinkedHashSet<FiniteStateMachine> mutants = mutantGen.operatorCIS(this.fsm);
    for (Map.Entry<String, ArrayList<String>> ts : testSuites.entrySet()) {
      mutantExec.executeMutants(mutants, ts.getValue());
      double op_score = mutantExec.getScore();
      double op_live = mutantExec.getAlive();
      double op_killed = mutantExec.getKilled();
      double op_totalmut = mutantExec.getTotal();
      printOperator(ts.getKey(), "CIS", op_live, op_killed, op_totalmut, op_score);
    } 
    mutants = mutantGen.operatorIEAND(this.fsm);
    for (Map.Entry<String, ArrayList<String>> ts : testSuites.entrySet()) {
      mutantExec.executeMutants(mutants, ts.getValue());
      double op_score = mutantExec.getScore();
      double op_live = mutantExec.getAlive();
      double op_killed = mutantExec.getKilled();
      double op_totalmut = mutantExec.getTotal();
      printOperator(ts.getKey(), "CI", op_live, op_killed, op_totalmut, op_score);
    } 
    for (Map.Entry<String, ArrayList<String>> ts : testSuites.entrySet()) {
      mutantExec.executeMutantsOperatorCO(mutantGen, ts.getValue());
      double op_score = mutantExec.getScore();
      double op_live = mutantExec.getAlive();
      double op_killed = mutantExec.getKilled();
      double op_totalmut = mutantExec.getTotal();
      printOperator(ts.getKey(), "CO", op_live, op_killed, op_totalmut, op_score);
    } 
    mutants = mutantGen.operatorMT(this.fsm);
    for (Map.Entry<String, ArrayList<String>> ts : testSuites.entrySet()) {
      mutantExec.executeMutants(mutants, ts.getValue());
      double op_score = mutantExec.getScore();
      double op_live = mutantExec.getAlive();
      double op_killed = mutantExec.getKilled();
      double op_totalmut = mutantExec.getTotal();
      printOperator(ts.getKey(), "MT", op_live, op_killed, op_totalmut, op_score);
    } 
    for (Map.Entry<String, ArrayList<String>> ts : testSuites.entrySet()) {
      mutantExec.executeMutantsOperatorTF(mutantGen, ts.getValue());
      double op_score = mutantExec.getScore();
      double op_live = mutantExec.getAlive();
      double op_killed = mutantExec.getKilled();
      double op_totalmut = mutantExec.getTotal();
      printOperator(ts.getKey(), "TSE", op_live, op_killed, op_totalmut, op_score);
    } 
    for (Map.Entry<String, ArrayList<String>> ts : testSuites.entrySet()) {
      mutantExec.executeMutantsOperatorOEAND(mutantGen, ts.getValue());
      double op_score = mutantExec.getScore();
      double op_live = mutantExec.getAlive();
      double op_killed = mutantExec.getKilled();
      double op_totalmut = mutantExec.getTotal();
      printOperator(ts.getKey(), "HSE", op_live, op_killed, op_totalmut, op_score);
    } 
  }
  
  private void printOperator(String operator, double opLive, double opKilled, double opTotalmut, double opScore) {
    StringBuilder print = new StringBuilder(operator);
    print.append(";" + opLive);
    print.append(";" + opKilled);
    print.append(";" + opTotalmut);
    print.append(";" + opScore);
    System.out.println(print);
  }
  
  private void printOperator(String method, String operator, double opLive, double opKilled, double opTotalmut, double opScore) {
    StringBuilder print = new StringBuilder(method);
    print.append(";" + operator);
    print.append(";" + opLive);
    print.append(";" + opKilled);
    print.append(";" + opTotalmut);
    print.append(";" + opScore);
    this.log = String.valueOf(this.log) + print + "\n";
    System.out.println(print);
  }
  
  public void detectEquivalents() {
    MutantGenerator mutantGen = new MutantGenerator(this.fsm);
    LinkedHashSet<FiniteStateMachine> mutants = mutantGen.operatorCIS(this.fsm);
    int equivalent = 0;
    for (FiniteStateMachine mut : mutants) {
      if (EquivalentMutantDetector.isEquivalent(this.fsm, mut))
        equivalent++; 
    } 
    System.out.println("CIS," + mutants.size() + "," + equivalent);
    mutants = mutantGen.operatorIEAND(this.fsm);
    equivalent = 0;
    for (FiniteStateMachine mut : mutants) {
      if (EquivalentMutantDetector.isEquivalent(this.fsm, mut))
        equivalent++; 
    } 
    System.out.println("CI," + mutants.size() + "," + equivalent);
    equivalent = 0;
    int total = 0;
    for (Transition t : this.fsm.getTransitions()) {
      LinkedHashSet<FiniteStateMachine> tmpMutants = mutantGen.operatorCO(this.fsm, t);
      total += tmpMutants.size();
      for (FiniteStateMachine mut : tmpMutants) {
        if (EquivalentMutantDetector.isEquivalent(this.fsm, mut))
          equivalent++; 
      } 
    } 
    System.out.println("CO," + total + "," + equivalent);
    mutants = mutantGen.operatorMT(this.fsm);
    equivalent = 0;
    for (FiniteStateMachine mut : mutants) {
      if (EquivalentMutantDetector.isEquivalent(this.fsm, mut))
        equivalent++; 
    } 
    System.out.println("MT," + mutants.size() + "," + equivalent);
    equivalent = 0;
    total = 0;
    for (Transition t : this.fsm.getTransitions()) {
      LinkedHashSet<FiniteStateMachine> tmpMutants = mutantGen.operatorTF(this.fsm, t);
      total += tmpMutants.size();
      for (FiniteStateMachine mut : tmpMutants) {
        if (EquivalentMutantDetector.isEquivalent(this.fsm, mut))
          equivalent++; 
      } 
    } 
    System.out.println("TSE," + total + "," + equivalent);
    equivalent = 0;
    total = 0;
    for (Transition t : this.fsm.getTransitions()) {
      LinkedHashSet<FiniteStateMachine> tmpMutants = mutantGen.operatorOEAND(this.fsm, t);
      total += tmpMutants.size();
      for (FiniteStateMachine mut : tmpMutants) {
        if (EquivalentMutantDetector.isEquivalent(this.fsm, mut))
          equivalent++; 
      } 
    } 
    System.out.println("HSE," + total + "," + equivalent);
  }
}
