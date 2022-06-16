package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HighOrderMutantGenerator {
  private static Logger logger = Logger.getAnonymousLogger();
  
  FiniteStateMachine fsm;
  
  int equivalent;
  
  ArrayList<String> hsiTS;
  
  MutantExecutor tempExec;
  
  public static Logger getLogger() {
    return logger;
  }
  
  public HighOrderMutantGenerator(FiniteStateMachine fsm) {
    logger.setLevel(Level.OFF);
    this.fsm = fsm;
  }
  
  public LinkedHashSet<FiniteStateMachine> getMutants() {
    LinkedHashSet<FiniteStateMachine> mutants = new LinkedHashSet<FiniteStateMachine>();
    MutantGenerator mutgen = new MutantGenerator(this.fsm);
    LinkedHashSet<FiniteStateMachine> mutants_extraState = mutgen.operatorES(this.fsm);
    System.out.println(mutants_extraState.size());
    for (FiniteStateMachine firstOrderMut : mutants_extraState) {
      LinkedHashSet<FiniteStateMachine> mut_cis = mutgen.operatorCIS(firstOrderMut);
      mutants.addAll(mut_cis);
      System.out.println(mut_cis.size());
      LinkedHashSet<FiniteStateMachine> mut_co = mutgen.operatorCO(firstOrderMut);
      mutants.addAll(mut_co);
      System.out.println(mut_co.size());
      LinkedHashSet<FiniteStateMachine> mut_tf = mutgen.operatorTF(firstOrderMut);
      mutants.addAll(mut_tf);
      System.out.println(mut_tf.size());
    } 
    return mutants;
  }
  
  public void execute(ArrayList<String> testSuite) {
    double total = 0.0D;
    double killed = 0.0D;
    MutantGenerator mutgen = new MutantGenerator(this.fsm);
    LinkedHashSet<FiniteStateMachine> mutants_extraState = mutgen.operatorES(this.fsm);
    if (mutants_extraState.isEmpty())
      mutants_extraState.add(this.fsm); 
    MutantExecutor mexec = new MutantExecutor(this.fsm);
    for (FiniteStateMachine firstOrderMut : mutants_extraState) {
      LinkedHashSet<FiniteStateMachine> mutants = new LinkedHashSet<FiniteStateMachine>();
      LinkedHashSet<FiniteStateMachine> mut_cis = mutgen.operatorCIS(firstOrderMut);
      mutants.addAll(mut_cis);
      LinkedHashSet<FiniteStateMachine> mut_co = mutgen.operatorCO(firstOrderMut);
      mutants.addAll(mut_co);
      LinkedHashSet<FiniteStateMachine> mut_tf = mutgen.operatorTF(firstOrderMut);
      mutants.addAll(mut_tf);
      mexec.executeMutants(mutants, testSuite);
      total += mexec.getTotal();
      killed += mexec.getKilled();
    } 
    double alive = total - killed;
    double score = killed / total;
    System.out.println(String.valueOf(total) + "," + killed + "," + alive + "," + score);
  }
  
  public void executeN(ArrayList<String> testSuite, int numberofExtraStates) {
    double total = 0.0D;
    double killed = 0.0D;
    MutantGenerator mutgen = new MutantGenerator(this.fsm);
    LinkedHashSet<FiniteStateMachine> mutants_extraState = mutgen.operatorES(this.fsm);
    logger.info("ES - Order 1: " + mutants_extraState.size());
    MutantExecutor mexec = new MutantExecutor(this.fsm);
    for (int i = 2; i <= numberofExtraStates; i++) {
      LinkedHashSet<FiniteStateMachine> tempMutants = new LinkedHashSet<FiniteStateMachine>();
      for (FiniteStateMachine mutant : mutants_extraState) {
        LinkedHashSet<FiniteStateMachine> tmp_extraStateMuts = mutgen.operatorES(mutant);
        tempMutants.addAll(tmp_extraStateMuts);
      } 
      mutants_extraState = tempMutants;
      logger.info("ES - Order 2: " + mutants_extraState.size());
    } 
    for (FiniteStateMachine nOrderMut : mutants_extraState) {
      LinkedHashSet<FiniteStateMachine> mutants = new LinkedHashSet<FiniteStateMachine>();
      LinkedHashSet<FiniteStateMachine> mut_cis = mutgen.operatorCIS(nOrderMut);
      mutants.addAll(mut_cis);
      LinkedHashSet<FiniteStateMachine> mut_co = mutgen.operatorCO(nOrderMut);
      mutants.addAll(mut_co);
      LinkedHashSet<FiniteStateMachine> mut_tf = mutgen.operatorTF(nOrderMut);
      mutants.addAll(mut_tf);
      mexec.executeMutants(mutants, testSuite);
      total += mexec.getTotal();
      killed += mexec.getKilled();
    } 
    double alive = total - killed;
    double score = killed / total;
    System.out.println(String.valueOf(total) + "," + killed + "," + alive + "," + score);
  }
  
  public LinkedHashSet<FiniteStateMachine> getMutantsWithN(int numberofExtraStates) {
    LinkedHashSet<FiniteStateMachine> mutants = new LinkedHashSet<FiniteStateMachine>();
    MutantGenerator mutgen = new MutantGenerator(this.fsm);
    LinkedHashSet<FiniteStateMachine> mutants_extraState = mutgen.operatorES(this.fsm);
    for (int i = 2; i <= numberofExtraStates; i++) {
      LinkedHashSet<FiniteStateMachine> tempMutants = new LinkedHashSet<FiniteStateMachine>();
      for (FiniteStateMachine mutant : mutants_extraState) {
        LinkedHashSet<FiniteStateMachine> tmp_extraStateMuts = mutgen.operatorES(mutant);
        tempMutants.addAll(tmp_extraStateMuts);
      } 
      mutants_extraState = tempMutants;
    } 
    for (FiniteStateMachine nOrderMut : mutants_extraState) {
      LinkedHashSet<FiniteStateMachine> mut_cis = mutgen.operatorCIS(nOrderMut);
      mutants.addAll(mut_cis);
      LinkedHashSet<FiniteStateMachine> mut_co = mutgen.operatorCO(nOrderMut);
      mutants.addAll(mut_co);
      LinkedHashSet<FiniteStateMachine> mut_tf = mutgen.operatorTF(nOrderMut);
      mutants.addAll(mut_tf);
    } 
    return mutants;
  }
  
  private void eliminateEquivalentMutants(LinkedHashSet<FiniteStateMachine> mutants) {
    for (FiniteStateMachine mutant : mutants) {
      if (!this.tempExec.executeMutant(mutant, this.hsiTS)) {
        mutant.printByStructure();
        this.equivalent++;
      } 
    } 
  }
  
  public void executeAllEquivalent() {
    double total = 0.0D;
    double equivalent = 0.0D;
    MutantGenerator mutgen = new MutantGenerator(this.fsm);
    LinkedHashSet<FiniteStateMachine> mutants_extraState = mutgen.operatorES(this.fsm);
    for (FiniteStateMachine firstOrderMut : mutants_extraState) {
      LinkedHashSet<FiniteStateMachine> mutants = new LinkedHashSet<FiniteStateMachine>();
      LinkedHashSet<FiniteStateMachine> mut_cis = mutgen.operatorCIS(firstOrderMut);
      mutants.addAll(mut_cis);
      LinkedHashSet<FiniteStateMachine> mut_co = mutgen.operatorCO(firstOrderMut);
      mutants.addAll(mut_co);
      LinkedHashSet<FiniteStateMachine> mut_tf = mutgen.operatorTF(firstOrderMut);
      mutants.addAll(mut_tf);
      for (FiniteStateMachine secOrderMutant : mutants) {
        if (EquivalentMutantDetector.isEquivalent(this.fsm, secOrderMutant))
          equivalent++; 
      } 
      total += mutants.size();
    } 
    System.out.println(String.valueOf(total) + "," + equivalent);
  }
}
