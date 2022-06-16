package br.usp.icmc.labes.jstatemodeltest.common;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.WMethod;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class TestSuiteReaderTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 01");
    try {
      File file = new File("./test/ts1.txt");
      TestSuiteReader reader = new TestSuiteReader(file, false);
      System.out.println(reader.getTestSuite());
      file = new File("./test/ts1-1.txt");
      reader = new TestSuiteReader(file, true);
      System.out.println(reader.getTestSuite());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./test/TestModel01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      WMethod wmethod = new WMethod(fsm);
      System.out.println(wmethod.getSequencesWithoutPrefixes());
      for (String test : wmethod.getSequencesWithoutPrefixes()) {
        if (fsm.isDefinedSeq(test, fsm.getInitialState()))
          System.out.println(fsm.getIOSequence(test)); 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
