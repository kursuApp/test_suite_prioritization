package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;

public class HighOrderMain {
  public static void main(String[] args) {
    File file = new File(args[0]);
    File file2 = new File(args[1]);
    FsmModelReader reader = new FsmModelReader(file, true);
    TestSuiteReader ts_reader = new TestSuiteReader(file2, false);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
      mgen.execute(ts_reader.getTestSuite());
    } catch (Exception exception) {}
  }
}
