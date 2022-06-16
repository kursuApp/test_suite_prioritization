package paderborn.project02.experiment;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi.FsmMinimization;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class Step02 {
  @Test
  public void test01() {
    try {
      File file = new File("./project02-experiment/prices/prices.fsm");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printInfo();
      fsm.addEnabledInputs();
      fsm.printFSM();
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./project02-experiment/specials/specials-enabled.fsm");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printInfo();
      FsmMinimization min = new FsmMinimization(fsm);
      min.minimize();
      fsm.printInfo();
      System.out.println("Det: " + fsm.checkDeterminism());
      fsm.checkMinimality();
      fsm.printFSM();
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    try {
      File file = new File("./project02-experiment/prices/prices-reduced-enabledEvents-tmp.fsm");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printInfo();
      fsm.checkMinimality();
      fsm.printInfo();
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
