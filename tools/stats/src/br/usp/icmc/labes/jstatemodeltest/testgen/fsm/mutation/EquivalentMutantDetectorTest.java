package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.LinkedHashSet;
import org.junit.Test;

public class EquivalentMutantDetectorTest {
  @Test
  public void test01() throws Exception {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    File file2 = new File("./test/Simao09CJ-fsm01-tsp.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    TestSuiteReader ts_reader = new TestSuiteReader(file2, false);
    FiniteStateMachine fsm = reader.getFsm();
    HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
    mgen.executeN(ts_reader.getTestSuite(), 2);
    LinkedHashSet<FiniteStateMachine> mutants = mgen.getMutantsWithN(2);
    System.out.println(mutants.size());
    int equi = 0;
    for (FiniteStateMachine mut : mutants) {
      if (EquivalentMutantDetector.isEquivalent(fsm, mut)) {
        equi++;
        mut.printByStructure();
      } 
    } 
    System.out.println(equi);
  }
}
