package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import org.junit.Test;

public class WpMethodTest {
  @Test
  public void test01() throws Exception {
    File file = new File("./test/endo11.txt");
    FsmModelReader reader = new FsmModelReader(file);
    FiniteStateMachine fsm = reader.getFsm();
    WpMethod wp = new WpMethod(fsm);
    System.out.println(wp.getSequences());
    System.out.println(TestSet.size(wp.getSequences()));
    WMethod w = new WMethod(fsm);
    System.out.println(w.getSequences());
    System.out.println(TestSet.size(w.getSequences()));
  }
}
