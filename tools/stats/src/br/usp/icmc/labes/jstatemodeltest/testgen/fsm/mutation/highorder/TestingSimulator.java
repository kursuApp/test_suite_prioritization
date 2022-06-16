package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.highorder;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.EquivalentMutantDetector;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.MutantOperators;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TestingSimulator {
  private static Logger logger = Logger.getAnonymousLogger();
  
  FiniteStateMachine original;
  
  ArrayList<OperatorApplication> opApplications;
  
  ArrayList<OperatorApplication> updatedOpApplications;
  
  ArrayList<String> testSuite;
  
  ArrayList<FiniteStateMachine> mutants;
  
  private ArrayList<Integer> executedEvents;
  
  public TestingSimulator(FiniteStateMachine fsm) {
    this.original = fsm;
  }
  
  public static Logger getLogger() {
    return logger;
  }
  
  public FiniteStateMachine generateFinalHighOrderMutant(ArrayList<OperatorApplication> operatorApplications) {
    ArrayList<FiniteStateMachine> tmp_mutants = new ArrayList<FiniteStateMachine>();
    tmp_mutants.add(this.original);
    for (OperatorApplication app : operatorApplications) {
      FiniteStateMachine current = tmp_mutants.get(tmp_mutants.size() - 1);
      MutantOperators operators = new MutantOperators(current);
      OperatorApplication up = getUpdateOperatorApplication(current, app);
      FiniteStateMachine newMutant = operators.applyOperator(up.getOperator(), up.getState(), up.getTransition(), up.getInput(), up.getOutput());
      if (newMutant != null)
        tmp_mutants.add(newMutant); 
    } 
    if (tmp_mutants.size() == 1)
      return null; 
    return tmp_mutants.get(tmp_mutants.size() - 1);
  }
  
  public void generateHighOrderMutant(ArrayList<OperatorApplication> opApplications) {
    logger.info("-----------------------------------");
    logger.info("Generating the high order mutant...");
    this.opApplications = opApplications;
    this.mutants = new ArrayList<FiniteStateMachine>();
    this.mutants.add(this.original);
    this.updatedOpApplications = new ArrayList<OperatorApplication>();
    for (OperatorApplication app : opApplications) {
      FiniteStateMachine current = this.mutants.get(this.mutants.size() - 1);
      MutantOperators operators = new MutantOperators(current);
      OperatorApplication up = getUpdateOperatorApplication(current, app);
      FiniteStateMachine newMutant = operators.applyOperator(up.getOperator(), up.getState(), up.getTransition(), up.getInput(), up.getOutput());
      if (newMutant == null)
        System.out.println("ERROR: no operators are applicable"); 
      this.mutants.add(newMutant);
    } 
    logger.info("-----------------------------------");
  }
  
  private OperatorApplication getUpdateOperatorApplication(FiniteStateMachine current, OperatorApplication app) {
    OperatorApplication updated = new OperatorApplication();
    updated.setOperator(app.getOperator());
    if (app.getInput() != null)
      updated.setInput(app.getInput()); 
    if (app.getOutput() != null)
      updated.setOutput(app.getOutput()); 
    if (app.getState() != null)
      updated.setState(current.getStateWithLabel(app.getState().getLabel())); 
    if (app.getTransition() != null)
      updated.setTransition(current.getTransitionWithLabel(app.getTransition().getIn().getLabel(), app.getTransition().getInput())); 
    return updated;
  }
  
  public boolean performTestingSimulation(ArrayList<String> testSuite) {
    this.executedEvents = new ArrayList<Integer>();
    logger.info("*************************************");
    logger.info("Performing testing simulation");
    FiniteStateMachine highOrderMutant = this.mutants.get(this.mutants.size() - 1);
    System.out.println("Total number of faults: " + this.opApplications.size());
    System.out.println("Detected Faults;#Events");
    if (EquivalentMutantDetector.isEquivalent(this.original, highOrderMutant)) {
      System.out.println("Equivalent mutant: " + this.opApplications);
      return false;
    } 
    ArrayList<OperatorApplication> opApplicationsN = this.opApplications;
    int numberOfExecutedEvents = 0;
    int cumulativeNumberofEvents = 0;
    for (int i = 0; i < testSuite.size(); i++) {
      String testCase = testSuite.get(i);
      logger.info("Executing: " + testCase);
      String originalOutput = this.original.output(testCase);
      String mutantOutput = highOrderMutant.output(testCase);
      if (!originalOutput.equals(mutantOutput)) {
        logger.info("test case failed");
        numberOfExecutedEvents += getNumberOfExecutedEvents(originalOutput, mutantOutput);
        int tmp_events = numberOfExecutedEvents;
        cumulativeNumberofEvents += numberOfExecutedEvents;
        numberOfExecutedEvents = 0;
        ArrayList<OperatorApplication> opApp = findFault(testCase, opApplicationsN, originalOutput, mutantOutput);
        System.out.println(String.valueOf(opApplicationsN.size() - opApp.size()) + ";" + tmp_events + ";" + cumulativeNumberofEvents);
        this.executedEvents.add(tmp_events);
        if (opApp.size() == 0) {
          System.out.println("all faults detected.");
          break;
        } 
        highOrderMutant = generateFinalHighOrderMutant(opApp);
        if (highOrderMutant == null) {
          System.out.println(opApp);
          System.out.println("ERROR: no mutant generated");
          break;
        } 
        if (EquivalentMutantDetector.isEquivalent(this.original, highOrderMutant)) {
          System.out.println("Equivalent mutant: " + opApp);
          return false;
        } 
        opApplicationsN = opApp;
        i--;
      } else {
        numberOfExecutedEvents += TestSequence.lenght(testCase);
      } 
    } 
    logger.info("*************************************");
    return true;
  }
  
  private ArrayList<OperatorApplication> findFault(String testCase, ArrayList<OperatorApplication> operatorApps, String originalOutput, String mutantOutput) {
    ArrayList<OperatorApplication> ret = new ArrayList<OperatorApplication>();
    int exec = getNumberOfExecutedEvents(originalOutput, mutantOutput);
    String partialTestCase = TestSequence.getPartialTestCase(testCase, exec);
    logger.info("Partial: " + partialTestCase);
    String orig_partialOut = this.original.output(partialTestCase);
    ArrayList<ArrayList<OperatorApplication>> combN1 = getCombinations(operatorApps);
    boolean next = true;
    while (next) {
      for (ArrayList<OperatorApplication> combApps : combN1) {
        FiniteStateMachine nless1Mutant = generateFinalHighOrderMutant(combApps);
        if (nless1Mutant != null) {
          String mut_partialOut = nless1Mutant.output(partialTestCase);
          if (orig_partialOut.equals(mut_partialOut)) {
            logger.info("corrected fault");
            next = false;
            return combApps;
          } 
        } 
      } 
      if (combN1.size() == 0 || ((ArrayList)combN1.get(0)).size() == 1) {
        System.out.println("ERROR, no correction (remove all faults)");
        break;
      } 
      if (next) {
        ArrayList<ArrayList<OperatorApplication>> tmp_combN1 = new ArrayList<ArrayList<OperatorApplication>>();
        for (ArrayList<OperatorApplication> combApps : combN1) {
          ArrayList<ArrayList<OperatorApplication>> c = getCombinations(combApps);
          tmp_combN1.addAll(c);
        } 
        combN1 = tmp_combN1;
      } 
    } 
    return ret;
  }
  
  private ArrayList<ArrayList<OperatorApplication>> getCombinations(ArrayList<OperatorApplication> operatorApps) {
    ArrayList<ArrayList<OperatorApplication>> ret = new ArrayList<ArrayList<OperatorApplication>>();
    for (OperatorApplication tmpApp : operatorApps) {
      ArrayList<OperatorApplication> newComb = new ArrayList<OperatorApplication>(operatorApps);
      if (newComb.remove(tmpApp))
        ret.add(newComb); 
    } 
    return ret;
  }
  
  public static int getNumberOfExecutedEvents(String originalOutput, String mutantOutput) {
    String[] orig = originalOutput.split("::");
    String[] muta = mutantOutput.split("::");
    for (int i = 0; i < orig.length; i++) {
      if (i < muta.length) {
        if (!orig[i].equals(muta[i]))
          return i + 1; 
      } else {
        return i + 1;
      } 
    } 
    return orig.length;
  }
  
  public ArrayList<Integer> getExecutedEvents() {
    return this.executedEvents;
  }
}
