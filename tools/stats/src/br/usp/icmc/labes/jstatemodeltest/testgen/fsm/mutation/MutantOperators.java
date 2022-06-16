package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;

public class MutantOperators {
  FiniteStateMachine original;
  
  public MutantOperators(FiniteStateMachine fsm) {
    this.original = fsm;
  }
  
  public FiniteStateMachine operatorCIS(State state) {
    if (!this.original.getStates().contains(state))
      return null; 
    if (this.original.getInitialState() == state)
      return null; 
    FiniteStateMachine mutant = MutantGenerator.clone(this.original);
    State newInit = mutant.getStateWithLabel(state.getLabel());
    mutant.setInitialState(newInit);
    return mutant;
  }
  
  public FiniteStateMachine operatorCI(Transition t, String newInput) {
    if (!this.original.getTransitions().contains(t))
      return null; 
    if (!this.original.getInputAlphabet().contains(newInput))
      return null; 
    if (t.getInput().equals(newInput))
      return null; 
    if (this.original.isDefinedSeq(newInput, t.getIn()))
      return null; 
    FiniteStateMachine mutant = MutantGenerator.clone(this.original);
    Transition mutateTransition = MutantGenerator.getSameTransition(t, mutant);
    mutateTransition.setInput(newInput);
    return mutant;
  }
  
  public FiniteStateMachine operatorCO(Transition t, String newOutput) {
    if (!this.original.getTransitions().contains(t))
      return null; 
    if (!this.original.getOutputAlphabet().contains(newOutput))
      return null; 
    if (t.getOutput().equals(newOutput))
      return null; 
    FiniteStateMachine mutant = MutantGenerator.clone(this.original);
    Transition mutateTransition = MutantGenerator.getSameTransition(t, mutant);
    mutateTransition.setOutput(newOutput);
    return mutant;
  }
  
  public FiniteStateMachine operatorMT(Transition t) {
    if (!this.original.getTransitions().contains(t))
      return null; 
    FiniteStateMachine mutant = MutantGenerator.clone(this.original);
    Transition mutateTransition = MutantGenerator.getSameTransition(t, mutant);
    State head = mutateTransition.getIn();
    head.getOut().remove(mutateTransition);
    State tail = mutateTransition.getOut();
    tail.getIn().remove(mutateTransition);
    mutant.getTransitions().remove(mutateTransition);
    return mutant;
  }
  
  public FiniteStateMachine operatorTSE(Transition t, State changedState) {
    if (!this.original.getTransitions().contains(t))
      return null; 
    if (!this.original.getStates().contains(changedState))
      return null; 
    if (t.getOut().equals(changedState))
      return null; 
    FiniteStateMachine mutant = MutantGenerator.clone(this.original);
    Transition mutateTransition = MutantGenerator.getSameTransition(t, mutant);
    State newDiffState = mutant.getStateWithLabel(changedState.getLabel());
    State oldState = mutant.getStateWithLabel(t.getOut().getLabel());
    oldState.getIn().remove(mutateTransition);
    mutateTransition.setOut(newDiffState);
    newDiffState.getIn().add(mutateTransition);
    return mutant;
  }
  
  public FiniteStateMachine operatorHSE(Transition t, State changedState) {
    if (!this.original.getTransitions().contains(t))
      return null; 
    if (!this.original.getStates().contains(changedState))
      return null; 
    if (t.getIn().equals(changedState))
      return null; 
    if (this.original.isDefinedSeq(t.getInput(), changedState))
      return null; 
    FiniteStateMachine mutant = MutantGenerator.clone(this.original);
    Transition mutateTransition = MutantGenerator.getSameTransition(t, mutant);
    State newDiffState = mutant.getStateWithLabel(changedState.getLabel());
    State oldState = mutant.getStateWithLabel(t.getIn().getLabel());
    oldState.getOut().remove(mutateTransition);
    mutateTransition.setIn(newDiffState);
    newDiffState.getOut().add(mutateTransition);
    return mutant;
  }
  
  public FiniteStateMachine operatorES(Transition t, State changedState) {
    if (!this.original.getTransitions().contains(t))
      return null; 
    if (!this.original.getStates().contains(changedState))
      return null; 
    if (changedState.getIn().size() <= 1)
      return null; 
    if (t.getOut() != changedState)
      return null; 
    FiniteStateMachine mutant = MutantGenerator.clone(this.original);
    String newStateName = MutantGenerator.getNewStateName(this.original);
    State extraState = new State(newStateName);
    mutant.addState(extraState);
    for (Transition out_t : changedState.getOut()) {
      Transition newout_t = new Transition(extraState, mutant.getStateWithLabel(out_t.getOut().getLabel()), out_t.getInput(), out_t.getOutput());
      mutant.addTransition(newout_t);
    } 
    Transition newin_t = MutantGenerator.getSameTransition(t, mutant);
    State oldState = mutant.getStateWithLabel(t.getOut().getLabel());
    oldState.getIn().remove(newin_t);
    newin_t.setOut(extraState);
    extraState.getIn().add(newin_t);
    return mutant;
  }
  
  public FiniteStateMachine applyOperator(Operator operator, State state, Transition transition, String input, String output) {
    switch (operator) {
      case CIS:
        return operatorCIS(state);
      case CI:
        return operatorCI(transition, input);
      case CO:
        return operatorCO(transition, output);
      case MT:
        return operatorMT(transition);
      case HSE:
        return operatorHSE(transition, state);
      case TSE:
        return operatorTSE(transition, state);
      case ES:
        return operatorES(transition, state);
    } 
    return null;
  }
}
