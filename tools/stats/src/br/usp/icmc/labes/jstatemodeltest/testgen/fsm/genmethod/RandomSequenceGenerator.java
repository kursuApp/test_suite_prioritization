package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RandomSequenceGenerator extends AbstractGenerator {
  int numberOfSequences;
  
  int lengthOfSequence;
  
  public RandomSequenceGenerator(FiniteStateMachine fsm) {
    super(fsm);
  }
  
  public void generateSequences() {
    this.numberOfSequences = 50;
    this.lengthOfSequence = 10;
    this.sequences = new ArrayList<String>();
    System.out.println(this.numberOfSequences);
    for (int i = 0; i < this.numberOfSequences; i++) {
      String newSeq = "EPSILON";
      for (int j = 0; j < this.lengthOfSequence; j++)
        newSeq = String.valueOf(newSeq) + "," + selectRandomInput(this.fsm.getInputAlphabet()) + "/UNDEFINED"; 
      this.sequences.add(newSeq);
    } 
  }
  
  private String selectRandomInput(HashSet<String> inputAlphabet) {
    Random random = new Random(System.nanoTime());
    int i = random.nextInt(inputAlphabet.size());
    return (String)inputAlphabet.toArray()[i];
  }
}
