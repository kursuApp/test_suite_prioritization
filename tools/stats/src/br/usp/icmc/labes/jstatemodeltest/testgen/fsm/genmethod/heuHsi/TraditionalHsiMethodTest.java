package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import org.junit.Test;

public class TraditionalHsiMethodTest {
  @Test
  public void test01() throws Exception {
    File file = new File("./test/endo11.txt");
    FsmModelReader reader = new FsmModelReader(file, false);
    FiniteStateMachine fsm = reader.getFsm();
    TraditionalHsiMethod method = new TraditionalHsiMethod(fsm);
    method.generateN1();
    for (String t : method.getSequences())
      System.out.println(t); 
  }
  
  @Test
  public void test02() throws Exception {
    File file = new File("./project02-experiment/example/example01.fsm");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    TraditionalHsiMethod method = new TraditionalHsiMethod(fsm);
    method.setTraversalSize(0);
    method.generateN1();
    for (String t : method.getSequences())
      System.out.println(t); 
  }
}
