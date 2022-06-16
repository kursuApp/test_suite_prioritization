package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import org.junit.Test;

public class MutantGeneratorTest {
  @Test
  public void test00() {
    try {
      callTest("./test/endo11.txt");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test01() {
    try {
      callTest("./test/ISELTA_specials_small_def_red.txt");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test02() {
    try {
      callTest("./upb_models/specials_fsm-part3_orig_noE.txt");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  protected void callTest(String filename) throws Exception {
    File file = new File(filename);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    MutantGenerator mg = new MutantGenerator(fsm);
    mg.generate();
  }
}
