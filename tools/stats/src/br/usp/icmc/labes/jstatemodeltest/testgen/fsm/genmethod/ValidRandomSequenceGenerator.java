package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import java.util.ArrayList;
import java.util.Random;

public class ValidRandomSequenceGenerator extends AbstractGenerator {
  int numberOfSequences;
  
  int lengthOfSequence;
  
  public ValidRandomSequenceGenerator(FiniteStateMachine fsm) {
    super(fsm);
  }
  
  public void generateSequences() {
    this.numberOfSequences = 50;
    this.lengthOfSequence = 10;
    this.sequences = new ArrayList<String>();
    System.out.println(this.numberOfSequences);
    for (int i = 0; i < this.numberOfSequences; i++) {
      State current = this.fsm.getInitialState();
      String newSeq = "EPSILON";
      for (int j = 0; j < this.lengthOfSequence; j++) {
        Transition t = selectRandomTransition(current);
        newSeq = String.valueOf(newSeq) + "," + t.getInput();
        current = t.getOut();
      } 
      this.sequences.add(newSeq);
    } 
  }
  
  private Transition selectRandomTransition(State current) {
    ArrayList<Transition> transitions = current.getOut();
    Random random = new Random(System.nanoTime());
    int i = random.nextInt(transitions.size());
    return transitions.get(i);
  }
}
