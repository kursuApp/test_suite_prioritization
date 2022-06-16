package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.WMethod;
import java.io.File;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class PaperTest {
  @Test
  public void test01() {
    try {
      File file = new File("./test/sbes-mef.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.generate();
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
      System.out.println(TestSet.size(generator.getFinalTestSet()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./test/sbes-mef.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      WMethod generator = new WMethod(fsm);
      for (String test : generator.getSequences())
        System.out.println(test); 
      System.out.println(TestSet.size(generator.getSequences()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
