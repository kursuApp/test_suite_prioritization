package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class PartialPCompleteTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 01");
    try {
      File file = new File("./test/TestModel01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(4L, fsm.getStates().size());
      Assert.assertEquals(13L, fsm.getTransitions().size());
      Assert.assertEquals(8L, fsm.getInputAlphabet().size());
      Assert.assertEquals(3L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      for (String test : generator.getFinalTestSet()) {
        if (!fsm.isDefinedSeq(test, fsm.getInitialState())) {
          Assert.fail();
          continue;
        } 
        System.out.println(fsm.getIOSequence(test));
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 03");
    try {
      File file = new File("./test/TestModel03.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(5L, fsm.getStates().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.generate();
      System.out.println("SIZE: " + TestSet.size(generator.getFinalTestSet()));
      for (String test : generator.getFinalTestSet()) {
        if (!fsm.isDefinedSeq(test, fsm.getInitialState())) {
          Assert.fail();
          continue;
        } 
        System.out.println(fsm.getIOSequence(test));
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
