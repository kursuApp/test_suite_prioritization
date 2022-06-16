package paderborn.project02.experiment;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.MutantEnvironment;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.Test;

public class Step06 {
  @Test
  public void test00() throws Exception {
    runForAllTestSuite("./project02-experiment/specials/", "specials-reduced-noEnabled.fsm");
  }
  
  @Test
  public void test01() throws Exception {
    runForAllTestSuite("./project02-experiment/specials/", "specials.fsm");
  }
  
  @Test
  public void test02() throws Exception {
    runForAllTestSuite("./project02-experiment/additionals/", "additionals-main-reduced-NoEnabledEvents.fsm");
  }
  
  @Test
  public void test03() throws Exception {
    runForAllTestSuite("./project02-experiment/additionals/", "additionals-main.fsm");
  }
  
  @Test
  public void test04() throws Exception {
    runForAllTestSuite("./project02-experiment/prices/", "prices-reduced-noEnabled.fsm");
  }
  
  @Test
  public void test05() throws Exception {
    runForAllTestSuite("./project02-experiment/prices/", "prices.fsm");
  }
  
  private void runForAllTestSuite(String DIR, String modelName) throws Exception {
    String SC = String.valueOf(DIR) + "TS-StateCoverage.txt";
    String TC = String.valueOf(DIR) + "TS-TransitionCoverage.txt";
    String HSI = String.valueOf(DIR) + "TS-hsi-n.txt";
    String HSIN1 = String.valueOf(DIR) + "TS-hsi-n1.txt";
    String HSIN2 = String.valueOf(DIR) + "TS-hsi-n2.txt";
    String K2 = String.valueOf(DIR) + "TS-k-sequence-1.txt";
    String K3 = String.valueOf(DIR) + "TS-k-sequence-2.txt";
    String K4 = String.valueOf(DIR) + "TS-k-sequence-3.txt";
    String TESTMODEL = String.valueOf(DIR) + modelName;
    System.out.println("--------------------------");
    System.out.println("model: " + modelName);
    File file = new File(TESTMODEL);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    MutantEnvironment env = new MutantEnvironment(fsm);
    LinkedHashMap<String, ArrayList<String>> testSuites = new LinkedHashMap<String, ArrayList<String>>();
    file = new File(SC);
    TestSuiteReader tsr = new TestSuiteReader(file, true);
    ArrayList<String> testsuite = tsr.getTestSuite();
    testSuites.put("State-Coverage", testsuite);
    file = new File(TC);
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    testSuites.put("Transition-Coverage", testsuite);
    file = new File(HSI);
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    testSuites.put("HSI(n)", testsuite);
    file = new File(HSIN1);
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    testSuites.put("HSI(n+1)", testsuite);
    file = new File(HSIN2);
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    testSuites.put("HSI(n+2)", testsuite);
    file = new File(K2);
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    testSuites.put("k=2", testsuite);
    file = new File(K3);
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    testSuites.put("k=3", testsuite);
    file = new File(K4);
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    testSuites.put("k=4", testsuite);
    env.executeOpt(testSuites);
    System.out.println("--------------------------");
    File logfile = new File(String.valueOf(DIR) + "/log2-" + modelName + ".txt");
    FileWriter fw = new FileWriter(logfile);
    fw.write(env.getLog());
    fw.close();
  }
}
