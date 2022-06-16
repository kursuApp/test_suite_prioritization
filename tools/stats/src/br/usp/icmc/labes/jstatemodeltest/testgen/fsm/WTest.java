package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.CharacterizationSetConstructor;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class WTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 01");
    try {
      File file = new File("./test/bbtas.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      CharacterizationSetConstructor csc = new CharacterizationSetConstructor(fsm);
      System.out.println(csc.getWset());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
