package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

public class MutantExecutorTest {
  protected void callTest(String filename, String testSuiteName) throws Exception {
    File file = new File(filename);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    file = new File(testSuiteName);
    TestSuiteReader tsr = new TestSuiteReader(file, true);
    ArrayList<String> testsuite = tsr.getTestSuite();
    MutantGenerator mg = new MutantGenerator(fsm);
    mg.generate();
    MutantExecutor me = new MutantExecutor(fsm);
    me.executeMutants(mg.getMutants(), testsuite);
  }
  
  @Test
  public void test01() {
    try {
      callTest("./test/ISELTA_specials_small_def_red_noE.txt", "./test/ISELTA_specials_small_def_red_noE_HSI.txt");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test02() {
    try {
      callTest("./upb_models/specials_fsm-part3_orig_noE.txt", "./upb_models/specials_fsm-part3_orig_noE_HSI.txt");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test03() {
    try {
      callTest("./upb_models/specials_fsm-part3_orig_noE.txt", "./upb_models/specials_testsuite_k2.txt");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test04() {
    try {
      callTest("./upb_models/specials_fsm-part3_orig_noE.txt", "./upb_models/specials_testsuite_k3.txt");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
