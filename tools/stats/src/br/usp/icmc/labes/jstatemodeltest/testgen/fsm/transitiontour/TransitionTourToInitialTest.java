package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.transitiontour;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class TransitionTourToInitialTest {
  private void test(File file) throws Exception {
    FsmModelReader reader = new FsmModelReader(file);
    FiniteStateMachine fsm = reader.getFsm();
    TransitionTourToInitial tt = new TransitionTourToInitial(fsm);
    Assert.assertEquals(1L, tt.getTestset().size());
    Assert.assertEquals(fsm.getInitialState(), fsm.nextStateWithSequence(fsm.getInitialState(), tt.getTestset().get(0)));
  }
  
  @Test
  public void test01() {
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      test(file);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./test/dorofeeva.txt");
      test(file);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    try {
      File file = new File("./test/chen.txt");
      test(file);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test04() {
    try {
      File file = new File("./test/bbtas.txt");
      test(file);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test05() {
    try {
      File file = new File("./test/dk15.txt");
      test(file);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
