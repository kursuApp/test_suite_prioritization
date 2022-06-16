package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.transitiontour;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.TestingTreeSetsConstructor;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Ntree;
import java.util.ArrayList;
import java.util.HashMap;

public class TransitionTour {
  protected FiniteStateMachine fsm;
  
  protected ArrayList<String> testset;
  
  private Ntree tree;
  
  public TransitionTour(FiniteStateMachine fsm) {
    this.fsm = fsm;
    build();
  }
  
  public ArrayList<String> getTestset() {
    return this.testset;
  }
  
  private void build() {
    TestingTreeSetsConstructor ttsc = new TestingTreeSetsConstructor(this.fsm);
    this.tree = ttsc.getTree();
    this.testset = new ArrayList<String>();
    String initial = TestSequence.getLongestSequence(ttsc.getTransitionCover());
    this.testset.add(initial);
    ArrayList<Transition> uncovered = getNonCoveredtransitions(this.testset);
    while (uncovered.size() > 0) {
      State currentstate = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), initial);
      boolean added = false;
      for (Transition t : uncovered) {
        if (t.getIn() == currentstate) {
          initial = TestSequence.concat(initial, t.getInput());
          this.testset.clear();
          this.testset.add(initial);
          added = true;
          break;
        } 
      } 
      if (!added)
        for (Transition t : uncovered) {
          String transferseq = findPathBetween(currentstate, t.getIn());
          if (transferseq != null) {
            initial = TestSequence.concat(initial, transferseq);
            initial = TestSequence.concat(initial, t.getInput());
            this.testset.clear();
            this.testset.add(initial);
            added = true;
            break;
          } 
        }  
      if (!added) {
        System.out.println(currentstate);
        System.out.println(uncovered);
        break;
      } 
      uncovered = getNonCoveredtransitions(this.testset);
    } 
  }
  
  protected String findPathBetween(State origin, State target) {
    HashMap<Transition, String> visited = new HashMap<Transition, String>();
    return reachState(origin, target, TestSequence.EPSILON, visited);
  }
  
  private String reachState(State origin, State target, String sequence, HashMap<Transition, String> visited) {
    if (origin == target)
      return sequence; 
    for (Transition t : origin.getOut()) {
      if (visited.get(t) == null) {
        visited.put(t, "ok");
        String seq = reachState(t.getOut(), target, TestSequence.concat(sequence, t.getInput()), visited);
        if (seq != null)
          return seq; 
      } 
    } 
    return null;
  }
  
  public ArrayList<Transition> getNonCoveredtransitions(ArrayList<String> testset) {
    ArrayList<Transition> covered = new ArrayList<Transition>();
    ArrayList<Transition> uncovered = new ArrayList<Transition>();
    for (String test : testset) {
      ArrayList<Transition> current = this.fsm.reachedTransitionsWithSequence(this.fsm.getInitialState(), test);
      for (Transition t : current) {
        if (!covered.contains(t))
          covered.add(t); 
      } 
    } 
    for (Transition t : this.fsm.getTransitions()) {
      if (!covered.contains(t))
        uncovered.add(t); 
    } 
    return uncovered;
  }
  
  private void build2() {
    TestingTreeSetsConstructor ttsc = new TestingTreeSetsConstructor(this.fsm);
    this.tree = ttsc.getTree();
    this.testset = new ArrayList<String>();
    HashMap<Transition, String> visited = new HashMap<Transition, String>();
    State initial = this.fsm.getInitialState();
    generate(initial, visited, TestSequence.EPSILON);
    if (visited.size() < this.fsm.getNumberOfTransitions())
      System.out.println("aa"); 
  }
  
  private void generate(State current, HashMap<Transition, String> visited, String sequence) {
    boolean visit = false;
    for (Transition transition : current.getOut()) {
      if (!visited.containsKey(transition)) {
        visit = true;
        visited.put(transition, "y");
        String newsequence = TestSequence.concat(sequence, transition.getInput());
        generate(transition.getOut(), visited, newsequence);
        break;
      } 
    } 
    if (!visit && !this.testset.contains(sequence))
      this.testset.add(sequence); 
  }
}
