package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.highorder;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.Operator;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

public class OperatorSelector {
  FiniteStateMachine fsm;
  
  Random random;
  
  public OperatorSelector(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  ArrayList<OperatorApplication> getOperatorApplication(int numberOfApplications, int seed) {
    ArrayList<OperatorApplication> ret = new ArrayList<OperatorApplication>();
    LinkedHashSet<Transition> usedTransitions = new LinkedHashSet<Transition>();
    this.random = new Random(seed);
    int numberOfTransitions = this.fsm.getNumberOfTransitions();
    if (numberOfApplications > numberOfTransitions) {
      System.out.println("There is no enough transitions.");
      return null;
    } 
    for (int i = 1; i <= numberOfApplications; i++) {
      int transitionIndex = this.random.nextInt(numberOfTransitions);
      Transition selectedTransition = this.fsm.getTransitions().get(transitionIndex);
      if (!usedTransitions.contains(selectedTransition)) {
        usedTransitions.add(selectedTransition);
        int operatorIndex = this.random.nextInt(6);
        OperatorApplication opApp = getOperatorApplication(operatorIndex, selectedTransition);
        if (opApp != null) {
          ret.add(opApp);
        } else {
          i--;
        } 
      } else {
        i--;
      } 
    } 
    return ret;
  }
  
  private OperatorApplication getOperatorApplication(int opIndex, Transition transition) {
    State headSt, newTailState, newHeadState;
    OperatorApplication newOpApp = new OperatorApplication();
    switch (opIndex) {
      case 0:
        newOpApp.setOperator(Operator.CIS);
        if (transition.getIn() == this.fsm.getInitialState())
          return null; 
        newOpApp.setState(transition.getIn());
        return newOpApp;
      case 1:
        newOpApp.setOperator(Operator.CI);
        headSt = transition.getIn();
        if (headSt.getOut().size() == this.fsm.getInputAlphabet().size())
          return null; 
        for (String x : this.fsm.getInputAlphabet()) {
          if (!this.fsm.isDefinedSeq(x, headSt)) {
            newOpApp.setTransition(transition);
            newOpApp.setInput(x);
            break;
          } 
        } 
        return newOpApp;
      case 2:
        newOpApp.setOperator(Operator.CO);
        newOpApp.setTransition(transition);
        for (String y : this.fsm.getOutputAlphabet()) {
          if (!transition.getOutput().equals(y)) {
            newOpApp.setOutput(y);
            break;
          } 
        } 
        return newOpApp;
      case 3:
        newOpApp.setOperator(Operator.MT);
        newOpApp.setTransition(transition);
        return newOpApp;
      case 4:
        newOpApp.setOperator(Operator.TSE);
        newOpApp.setTransition(transition);
        newTailState = getRandomState(transition.getOut());
        newOpApp.setState(newTailState);
        return newOpApp;
      case 5:
        newOpApp.setOperator(Operator.HSE);
        newOpApp.setTransition(transition);
        newHeadState = getRandomState(transition.getIn());
        newOpApp.setState(newHeadState);
        return newOpApp;
      case 6:
        newOpApp.setOperator(Operator.ES);
        return newOpApp;
    } 
    return null;
  }
  
  private State getRandomState(State state) {
    while (true) {
      int index = this.random.nextInt(this.fsm.getStates().size());
      if (this.fsm.getStates().get(index) != state)
        return this.fsm.getStates().get(index); 
    } 
  }
}
