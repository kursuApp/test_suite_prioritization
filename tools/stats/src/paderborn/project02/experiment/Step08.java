package paderborn.project02.experiment;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.highorder.TestingSimulationEnvironment;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

public class Step08 {
  ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
  
  @Test
  public void test00() throws Exception {
    performSimulation("./project02-experiment/additionals/", "additionals-main-reduced-NoEnabledEvents.fsm");
  }
  
  private void performSimulation(String DIR, String modelName) throws Exception {
    String TESTMODEL = String.valueOf(DIR) + modelName;
    String SC = String.valueOf(DIR) + "TS-StateCoverage.txt";
    String TC = String.valueOf(DIR) + "TS-TransitionCoverage.txt";
    String HSI = String.valueOf(DIR) + "TS-hsi-n.txt";
    String HSIN1 = String.valueOf(DIR) + "TS-hsi-n1.txt";
    String HSIN2 = String.valueOf(DIR) + "TS-hsi-n2.txt";
    String K2 = String.valueOf(DIR) + "TS-k-sequence-1.txt";
    String K3 = String.valueOf(DIR) + "TS-k-sequence-2.txt";
    String K4 = String.valueOf(DIR) + "TS-k-sequence-3.txt";
    System.out.println("--------------------------");
    System.out.println("model: " + modelName);
    File file = new File(TESTMODEL);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    run(SC, fsm, "State Coverage");
    run(TC, fsm, "Transition Coverage");
    run(HSI, fsm, "HSI(n)");
    run(HSIN1, fsm, "HSI(n+1)");
    run(HSIN2, fsm, "HSI(n+2)");
    run(K2, fsm, "(k=2)");
    run(K3, fsm, "(k=3)");
    run(K4, fsm, "(k=4)");
    for (ArrayList<Integer> r : this.results)
      System.out.println(r); 
  }
  
  private void run(String SC, FiniteStateMachine fsm, String testSuiteName) {
    int begin = 11;
    int end = 11;
    for (int i = begin; i <= end; i++) {
      try {
        System.out.println("Seed: " + i);
        System.out.println("Test Suite: " + testSuiteName);
        File file = new File(SC);
        TestSuiteReader tsr = new TestSuiteReader(file, true);
        ArrayList<String> testsuite = tsr.getTestSuite();
        System.out.println("Test Suite Length: " + TestSet.size(testsuite));
        TestingSimulationEnvironment env = new TestingSimulationEnvironment(fsm);
        ArrayList<Integer> res = env.performSimulation(testsuite, 25, i);
        this.results.add(res);
      } catch (Exception exception) {}
    } 
  }
}
