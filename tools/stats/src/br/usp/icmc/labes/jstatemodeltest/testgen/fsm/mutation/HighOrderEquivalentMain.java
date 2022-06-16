package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;

public class HighOrderEquivalentMain {
  public static void main(String[] args) {
    File file = new File(args[0]);
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
      mgen.executeAllEquivalent();
    } catch (Exception exception) {}
  }
}
