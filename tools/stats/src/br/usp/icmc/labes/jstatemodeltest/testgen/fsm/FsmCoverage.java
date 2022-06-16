package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import java.util.ArrayList;

public class FsmCoverage {
  FiniteStateMachine fsm;
  
  public FsmCoverage(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public float transitionCoverage(ArrayList<String> testset) {
    float total = this.fsm.getNumberOfTransitions();
    ArrayList<Transition> covered = new ArrayList<Transition>();
    for (String test : testset) {
      ArrayList<Transition> current = this.fsm.reachedTransitionsWithSequence(this.fsm.getInitialState(), test);
      if (current.size() != 0) {
        Transition t = current.get(current.size() - 1);
        String x = t.getInput();
        String alpha = TestSequence.getPrefixFrom(test, x);
        if (!covered.contains(t) && testset.contains(alpha))
          covered.add(t); 
      } 
    } 
    return covered.size() / total;
  }
  
  public ArrayList<Transition> getNonCoveredtransitions(ArrayList<String> testset) {
    float total = this.fsm.getNumberOfTransitions();
    ArrayList<Transition> covered = new ArrayList<Transition>();
    ArrayList<Transition> uncovered = new ArrayList<Transition>();
    for (String test : testset) {
      ArrayList<Transition> current = this.fsm.reachedTransitionsWithSequence(this.fsm.getInitialState(), test);
      if (current.size() != 0) {
        Transition t = current.get(current.size() - 1);
        String x = t.getInput();
        String alpha = TestSequence.getPrefixFrom(test, x);
        if (!covered.contains(t) && testset.contains(alpha))
          covered.add(t); 
      } 
    } 
    for (Transition t : this.fsm.getTransitions()) {
      if (!covered.contains(t))
        uncovered.add(t); 
    } 
    return uncovered;
  }
  
  public ArrayList<Transition> getCoveredtransitions(ArrayList<String> testset) {
    ArrayList<Transition> covered = new ArrayList<Transition>();
    for (String test : testset) {
      ArrayList<Transition> current = this.fsm.reachedTransitionsWithSequence(this.fsm.getInitialState(), test);
      for (Transition t : current) {
        if (!covered.contains(t))
          covered.add(t); 
      } 
    } 
    return covered;
  }
  
  public boolean isInitializedTransitionCoverage(ArrayList<String> testset) {
    float tcoverage = transitionCoverage(testset);
    if (tcoverage == 1.0F && testset.contains("EPSILON"))
      return true; 
    return false;
  }
  
  public boolean isTransitionCoverage(ArrayList<String> testset) {
    float tcoverage = transitionCoverage(testset);
    if (tcoverage == 1.0F)
      return true; 
    return false;
  }
}
