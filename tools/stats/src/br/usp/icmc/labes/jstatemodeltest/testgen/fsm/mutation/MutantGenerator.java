package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class MutantGenerator {
  protected FiniteStateMachine original;
  
  protected LinkedHashSet<FiniteStateMachine> mutants;
  
  public MutantGenerator(FiniteStateMachine fsm) {
    this.original = fsm;
  }
  
  public void generate() {
    this.mutants = new LinkedHashSet<FiniteStateMachine>();
    LinkedHashSet<FiniteStateMachine> newMutants = operatorCIS(this.original);
    this.mutants.addAll(newMutants);
    System.out.println("CIS " + newMutants.size());
    newMutants = operatorCO(this.original);
    this.mutants.addAll(newMutants);
    System.out.println("CO " + newMutants.size());
    newMutants = operatorTF(this.original);
    this.mutants.addAll(newMutants);
    System.out.println("TF " + newMutants.size());
    newMutants = operatorMT(this.original);
    this.mutants.addAll(newMutants);
    System.out.println("MT " + newMutants.size());
    newMutants = operatorIEAND(this.original);
    this.mutants.addAll(newMutants);
    System.out.println("IEAND " + newMutants.size());
    newMutants = operatorOEAND(this.original);
    this.mutants.addAll(newMutants);
    System.out.println("OEAND " + newMutants.size());
    newMutants = operatorET(this.original);
    this.mutants.addAll(newMutants);
    System.out.println("ET " + newMutants.size());
    System.out.println(this.mutants.size());
  }
  //Extra transition, copy transition and add to undefined states
  public LinkedHashSet<FiniteStateMachine> operatorET(FiniteStateMachine orig) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (Transition t : orig.getTransitions()) {
      for (State diffState : orig.getStates()) {
        if (t.getIn() != diffState && !orig.isDefinedSeq(t.getInput(), diffState)) {
          FiniteStateMachine newmut = clone(orig);
          Transition mutateTransition = getSameTransition(t, newmut);
          State newDiffState = newmut.getStateWithLabel(diffState.getLabel());
          Transition newt = new Transition(newDiffState, mutateTransition.getOut(), 
              mutateTransition.getInput(), mutateTransition.getOutput());
          newmut.addTransition(newt);
          ret.add(newmut);
        } 
      } 
    } 
    return ret;
  }
  //Origin-Exchanged (avoiding non-determinism)
  public LinkedHashSet<FiniteStateMachine> operatorOEAND(FiniteStateMachine orig) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (Transition t : orig.getTransitions()) {
      for (State diffState : orig.getStates()) {
        if (t.getIn() != diffState && !orig.isDefinedSeq(t.getInput(), diffState)) {
          FiniteStateMachine newmut = clone(orig);
          Transition mutateTransition = getSameTransition(t, newmut);
          State newDiffState = newmut.getStateWithLabel(diffState.getLabel());
          State oldState = newmut.getStateWithLabel(t.getIn().getLabel());
          oldState.getOut().remove(mutateTransition);
          mutateTransition.setIn(newDiffState);
          newDiffState.getOut().add(mutateTransition);
          ret.add(newmut);
        } 
      } 
    } 
    return ret;
  }
  
  public LinkedHashSet<FiniteStateMachine> operatorOEAND(FiniteStateMachine orig, Transition t) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    if (!orig.getTransitions().contains(t))
      return null; 
    for (State diffState : orig.getStates()) {
      if (t.getIn() != diffState && !orig.isDefinedSeq(t.getInput(), diffState)) {
        FiniteStateMachine newmut = clone(orig);
        Transition mutateTransition = getSameTransition(t, newmut);
        State newDiffState = newmut.getStateWithLabel(diffState.getLabel());
        State oldState = newmut.getStateWithLabel(t.getIn().getLabel());
        oldState.getOut().remove(mutateTransition);
        mutateTransition.setIn(newDiffState);
        newDiffState.getOut().add(mutateTransition);
        ret.add(newmut);
      } 
    } 
    return ret;
  }
  //Input-Exchanged (Avoiding Non-Determinism)
  public LinkedHashSet<FiniteStateMachine> operatorIEAND(FiniteStateMachine orig) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (Transition t : orig.getTransitions()) {
      for (String diffInput : orig.getInputAlphabet()) {
        if (!orig.isDefinedSeq(diffInput, t.getIn())) {
          FiniteStateMachine newmut = clone(orig);
          Transition mutateTransition = getSameTransition(t, newmut);
          mutateTransition.setInput(diffInput);
          ret.add(newmut);
        } 
      } 
    } 
    return ret;
  }
  //missing transition
  public LinkedHashSet<FiniteStateMachine> operatorMT(FiniteStateMachine orig) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (Transition t : orig.getTransitions()) {
      FiniteStateMachine newmut = clone(orig);
      Transition mutateTransition = getSameTransition(t, newmut);
      State head = mutateTransition.getIn();
      head.getOut().remove(mutateTransition);
      State tail = mutateTransition.getOut();
      tail.getIn().remove(mutateTransition);
      newmut.getTransitions().remove(mutateTransition);
      ret.add(newmut);
    } 
    return ret;
  }
  //Transfer Fault
  //change the tail state
  public LinkedHashSet<FiniteStateMachine> operatorTF(FiniteStateMachine orig) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (Transition t : orig.getTransitions()) {
      for (State diffState : orig.getStates()) {
        if (!t.getOut().getLabel().equals(diffState.getLabel())) {
          FiniteStateMachine newmut = clone(orig);
          Transition mutateTransition = getSameTransition(t, newmut);
          State newDiffState = newmut.getStateWithLabel(diffState.getLabel());
          State oldState = newmut.getStateWithLabel(t.getOut().getLabel());
          oldState.getIn().remove(mutateTransition);
          mutateTransition.setOut(newDiffState);
          newDiffState.getIn().add(mutateTransition);
          ret.add(newmut);
        } 
      } 
    } 
    return ret;
  }
  //Transfer Fault
  //change the tail state
  public LinkedHashSet<FiniteStateMachine> operatorTF(FiniteStateMachine orig, Transition t) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    if (!orig.getTransitions().contains(t))
      return null; 
    for (State diffState : orig.getStates()) {
      if (!t.getOut().getLabel().equals(diffState.getLabel())) {
        FiniteStateMachine newmut = clone(orig);
        Transition mutateTransition = getSameTransition(t, newmut);
        State newDiffState = newmut.getStateWithLabel(diffState.getLabel());
        State oldState = newmut.getStateWithLabel(t.getOut().getLabel());
        oldState.getIn().remove(mutateTransition);
        mutateTransition.setOut(newDiffState);
        newDiffState.getIn().add(mutateTransition);
        ret.add(newmut);
      } 
    } 
    return ret;
  }
  //Change Output
  public LinkedHashSet<FiniteStateMachine> operatorCO(FiniteStateMachine orig) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (Transition t : orig.getTransitions()) {
      for (String diffOutput : orig.getOutputAlphabet()) {
        if (!t.getOutput().equals(diffOutput)) {
          FiniteStateMachine newmut = clone(orig);
          Transition mutateTransition = getSameTransition(t, newmut);
          mutateTransition.setOutput(diffOutput);
          ret.add(newmut);
        } 
      } 
    } 
    return ret;
  }
  //Change Output
  public LinkedHashSet<FiniteStateMachine> operatorCO(FiniteStateMachine orig, Transition t) {
    if (!orig.getTransitions().contains(t))
      return null; 
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (String diffOutput : orig.getOutputAlphabet()) {
      if (!t.getOutput().equals(diffOutput)) {
        FiniteStateMachine newmut = clone(orig);
        Transition mutateTransition = getSameTransition(t, newmut);
        mutateTransition.setOutput(diffOutput);
        ret.add(newmut);
      } 
    } 
    return ret;
  }
  // Extra state
  public LinkedHashSet<FiniteStateMachine> operatorES(FiniteStateMachine orig) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (State state : orig.getStates()) {
      if (state.getIn().size() > 1)
        for (Transition transition : state.getIn()) {
          FiniteStateMachine newmut = clone(orig);
          String newStateName = getNewStateName(orig);
          State extraState = new State(newStateName);
          newmut.addState(extraState);
          for (Transition out_t : state.getOut()) {
            Transition newout_t = new Transition(extraState, newmut.getStateWithLabel(out_t.getOut().getLabel()), out_t.getInput(), out_t.getOutput());
            newmut.addTransition(newout_t);
          } 
          Transition newin_t = getSameTransition(transition, newmut);
          State oldState = newmut.getStateWithLabel(transition.getOut().getLabel());
          oldState.getIn().remove(newin_t);
          newin_t.setOut(extraState);
          extraState.getIn().add(newin_t);
          ret.add(newmut);
        }  
    } 
    return ret;
  }

  public static String getNewStateName(FiniteStateMachine orig) {
    int i = 1;
    while (i <= 5000) {
      State s = orig.getStateWithLabel("extra-" + i);
      if (s == null)
        return "extra-" + i; 
      i++;
    } 
    System.out.println("ERROR");
    return null;
  }
  
  public static Transition getSameTransition(Transition t, FiniteStateMachine newmut) {
    for (Transition nt : newmut.getTransitions()) {
      if (sameTransition(t, nt))
        return nt; 
    } 
    return null;
  }
  
  private static boolean sameTransition(Transition t, Transition nt) {
    String s1i = t.getIn().getLabel();
    String s1j = t.getOut().getLabel();
    String in1 = t.getInput();
    String out1 = t.getOutput();
    String s2i = nt.getIn().getLabel();
    String s2j = nt.getOut().getLabel();
    String in2 = nt.getInput();
    String out2 = nt.getOutput();
    if (!s1i.equals(s2i))
      return false; 
    if (!s1j.equals(s2j))
      return false; 
    if (!in1.equals(in2))
      return false; 
    if (!out1.equals(out2))
      return false; 
    return true;
  }
  //Change Initial State
  public LinkedHashSet<FiniteStateMachine> operatorCIS(FiniteStateMachine orig) {
    LinkedHashSet<FiniteStateMachine> ret = new LinkedHashSet<FiniteStateMachine>();
    for (State si : orig.getStates()) {
      if (si != orig.getInitialState()) {
        FiniteStateMachine newmut = clone(orig);
        State newInit = newmut.getStateWithLabel(si.getLabel());
        newmut.setInitialState(newInit);
        ret.add(newmut);
      } 
    } 
    return ret;
  }
  
  static FiniteStateMachine clone(FiniteStateMachine fsm) {
    FiniteStateMachine clone = new FiniteStateMachine();
    HashMap<String, State> map = new HashMap<String, State>();
    State init = new State(fsm.getInitialState().getLabel());
    clone.addState(init);
    clone.setInitialState(init);
    map.put(init.getLabel(), init);
    for (State si : fsm.getStates()) {
      if (!si.getLabel().equals(init.getLabel())) {
        State newsi = new State(si.getLabel());
        clone.addState(newsi);
        map.put(newsi.getLabel(), newsi);
      } 
    } 
    for (Transition t : fsm.getTransitions()) {
      State orig = map.get(t.getIn().getLabel());
      State dest = map.get(t.getOut().getLabel());
      Transition nt = new Transition(orig, dest, t.getInput(), t.getOutput());
      clone.addTransition(nt);
    } 
    return clone;
  }
  
  public LinkedHashSet<FiniteStateMachine> getMutants() {
    return this.mutants;
  }
}
