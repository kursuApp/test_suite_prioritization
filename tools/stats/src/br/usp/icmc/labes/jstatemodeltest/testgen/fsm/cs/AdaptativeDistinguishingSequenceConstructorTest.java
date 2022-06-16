package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class AdaptativeDistinguishingSequenceConstructorTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/endo11.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      AdaptativeDistinguishingSequenceConstructor adsc = new AdaptativeDistinguishingSequenceConstructor(fsm);
      System.out.println(adsc.getAdaptativeDS());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
