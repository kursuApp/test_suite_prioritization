package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.highorder;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

public class OperatorSelectorTest {
  @Test
  public void test01() throws Exception {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    OperatorSelector selector = new OperatorSelector(fsm);
    ArrayList<OperatorApplication> opApps = selector.getOperatorApplication(4, 10);
    System.out.println(opApps);
  }
  
  @Test
  public void test02() throws Exception {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    file = new File("./test/Simao09CJ-fsm01-tsp.txt");
    TestSuiteReader treader = new TestSuiteReader(file, false);
    ArrayList<String> testSuite = treader.getTestSuite();
    TestingSimulationEnvironment env = new TestingSimulationEnvironment(fsm);
    env.performSimulation(testSuite, 4, 13);
  }
}
