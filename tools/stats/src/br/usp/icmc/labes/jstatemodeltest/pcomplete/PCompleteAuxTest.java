package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class PCompleteAuxTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 01");
    try {
      File file = new File("./test/chen.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(5L, fsm.getStates().size());
      Assert.assertEquals(10L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(4);
      ArrayList<Pair> C = new ArrayList<Pair>();
      ArrayList<String> K = new ArrayList<String>();
      C.add(new Pair("alpha", "beta"));
      C.add(new Pair("beta1", "alpha"));
      C.add(new Pair("beta2", "alpha2"));
      K.add("alpha");
      K.add("alpha2");
      System.out.println(generator.findCunionK(C, K));
      Assert.assertEquals(3L, generator.findCunionK(C, K).size());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
