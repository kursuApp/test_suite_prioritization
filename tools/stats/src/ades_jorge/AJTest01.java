package ades_jorge;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import org.junit.Test;

public class AJTest01 {
  @Test
  public void test01() throws Exception {
    File file = new File("./examples/ades-jorge/confCase-c.fsm");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printStats();
    System.out.println(fsm.getInputAlphabet());
    fsm.printLettersFSM();
  }
  
  @Test
  public void test02() throws Exception {
    File file = new File("./examples/ades-jorge/inres-c.fsm");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printStats();
    System.out.println(fsm.getInputAlphabet());
    fsm.printLettersFSM();
  }
  
  @Test
  public void test03() throws Exception {
    File file = new File("./examples/ades-jorge/inres-c.fsm");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printStats();
    System.out.println(fsm.getInputAlphabet());
  }
}
