package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.TestingTreeSetsConstructor;
import java.util.ArrayList;
import java.util.HashSet;

public class UndefinedSymbolsGenerator extends AbstractGenerator {
  public UndefinedSymbolsGenerator(FiniteStateMachine fsm) {
    super(fsm);
  }
  
  public void generateSequences() {
    this.sequences = new ArrayList<String>();
    TestingTreeSetsConstructor ttsc = new TestingTreeSetsConstructor(this.fsm);
    ArrayList<String> stateCover = ttsc.getStateCover();
    HashSet<String> inputSet = this.fsm.getInputAlphabet();
    for (String st_seq : stateCover) {
      State st = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), st_seq);
      for (String in : inputSet) {
        if (!st.isDefinedForInput(in))
          this.sequences.add(String.valueOf(st_seq) + "," + in); 
      } 
    } 
  }
}
