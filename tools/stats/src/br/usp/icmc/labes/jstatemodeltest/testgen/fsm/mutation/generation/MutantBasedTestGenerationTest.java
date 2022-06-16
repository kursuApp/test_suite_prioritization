package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.generation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import org.junit.Test;

public class MutantBasedTestGenerationTest {
  @Test
  public void test01() throws Exception {
    File file = new File("./test/sbes-mef.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    MutantBasedTestGeneration gen = new MutantBasedTestGeneration(fsm);
    gen.generate();
    System.out.println(TestSet.size(gen.getTestSuite()));
    for (String test : gen.getTestSuite())
      System.out.println(test); 
  }
  
  @Test
  public void test02() throws Exception {
    File file = new File("./mutants/biggest/fsm1.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    MutantBasedTestGeneration gen = new MutantBasedTestGeneration(fsm);
    gen.generate();
    System.out.println("M: " + TestSet.size(gen.getTestSuite()));
    File file2 = new File("./mutants/biggest/tsw.txt");
    TestSuiteReader ts_reader = new TestSuiteReader(file2, false);
    System.out.println("W: " + TestSet.size(ts_reader.getTestSuite()));
    file2 = new File("./mutants/biggest/tshsi.txt");
    ts_reader = new TestSuiteReader(file2, false);
    System.out.println("HSI: " + TestSet.size(ts_reader.getTestSuite()));
    file2 = new File("./mutants/biggest/tsp3.txt");
    ts_reader = new TestSuiteReader(file2, false);
    System.out.println("P: " + TestSet.size(ts_reader.getTestSuite()));
  }
}
