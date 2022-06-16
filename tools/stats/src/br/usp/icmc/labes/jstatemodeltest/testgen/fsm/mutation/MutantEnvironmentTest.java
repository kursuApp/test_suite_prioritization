package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

public class MutantEnvironmentTest {
  private static String TESTMODEL = "./upb_models/specials_fsm-part3_orig_noE.txt";
  
  private static String HSI = "./upb_models/specials_fsm-part3_orig_noE_HSI.txt";
  
  private static String HSIN1 = "./upb_models/specials_fsm-part3_orig_noE_HSI_n1.txt";
  
  private static String K2 = "./upb_models/specials_testsuite_k2.txt";
  
  private static String K3 = "./upb_models/specials_testsuite_k3.txt";
  
  protected void callTest(String filename, String testSuiteName) throws Exception {
    File file = new File(filename);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    file = new File(testSuiteName);
    TestSuiteReader tsr = new TestSuiteReader(file, true);
    ArrayList<String> testsuite = tsr.getTestSuite();
    MutantEnvironment me = new MutantEnvironment(fsm);
    me.execute(testsuite);
  }
  
  @Test
  public void test02() {
    try {
      System.out.println("--------------------------------------------");
      System.out.println("--------HSI");
      callTest(TESTMODEL, HSI);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test021() {
    try {
      System.out.println("--------------------------------------------");
      System.out.println("--------HSI (n+1)");
      callTest(TESTMODEL, HSIN1);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test03() {
    try {
      System.out.println("--------------------------------------------");
      System.out.println("--------k=2");
      callTest(TESTMODEL, K2);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test04() {
    try {
      System.out.println("--------------------------------------------");
      System.out.println("--------k=3");
      callTest(TESTMODEL, K3);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
