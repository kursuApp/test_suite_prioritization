package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class HeuHsiMethodTest {
  @Test
  public void test01() {
    try {
      callTest("./test/ISELTA_specials_small_def_red.txt");
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      callTest("./upb_models/specials_fsm-part3_orig.txt");
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  protected void callTest(String filename) throws Exception {
    File file = new File(filename);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    HeuHsiMethod method = new HeuHsiMethod(fsm);
    method.generateN1();
    System.out.println("--------------------------------------------------------------");
    System.out.println("WITH EnabledInputs");
    System.out.println("length: " + TestSet.size(method.getSequences()));
    System.out.println("# TCs: " + method.getSequences().size());
    System.out.println("--------------------------------------------------------------");
    for (String seq : method.getSequences())
      System.out.println(seq); 
    System.out.println("--------------------------------------------------------------");
    System.out.println("NO EnabledInputs");
    System.out.println("length: " + TestSet.size(method.getNoEnabledInputsequences()));
    System.out.println("# TCs: " + method.getNoEnabledInputsequences().size());
    System.out.println("length (no resets): " + (TestSet.size(method.getNoEnabledInputsequences()) - method.getNoEnabledInputsequences().size()));
    System.out.println("--------------------------------------------------------------");
    for (String seq : method.getNoEnabledInputsequences())
      System.out.println(seq); 
  }
}
