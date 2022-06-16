package br.usp.icmc.labes.experiments.qualipso;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.pcomplete.PCompleteTestGenerator;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class Test01 {
  @Test
  public void test01() {
    try {
      File file = new File("./test/TestModel04.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      System.out.println(fsm.getNumberOfStates());
      System.out.println(fsm.getNumberOfTransitions());
      System.out.println(fsm.getInputAlphabet().size());
      System.out.println(fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.generate();
      float average = 0.0F;
      for (String test : generator.getFinalTestSet()) {
        average += TestSequence.lenght(test);
        System.out.println("runner.addTestCase(\"test 00\", \"" + fsm.getIOSequence(test) + "\");");
      } 
      System.out.println(generator.getFinalTestSet().size());
      System.out.println(average / generator.getFinalTestSet().size());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
