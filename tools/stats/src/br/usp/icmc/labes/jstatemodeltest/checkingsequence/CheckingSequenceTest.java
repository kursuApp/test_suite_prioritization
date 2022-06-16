package br.usp.icmc.labes.jstatemodeltest.checkingsequence;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.pcomplete.PCompleteTestGenerator;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.transitiontour.TransitionTour;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class CheckingSequenceTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 01");
    try {
      File file = new File("./test/hierons.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.setInitialTestSet(TestSequence.getAllPrefixesFrom("a,a,a,a,b,a,a,b,a,a,b,a,a,b,a,b,a,a,a,a,b,b,a,b,a,a,b,b,a,a,b,a,a,b,a,a,a,b"));
      generator.generate();
      System.out.println(TestSet.size(generator.getFinalTestSet()));
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 02");
    try {
      File file = new File("./test/hierons.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      TransitionTour ttgen = new TransitionTour(fsm);
      System.out.println(ttgen.getTestset());
      ArrayList<State> states = fsm.getStates();
      System.out.println(String.valueOf(fsm.nextOutput(states.get(0), "a,a,a")) + " - " + fsm.nextStateWithSequence(states.get(0), "a,a,a"));
      System.out.println(String.valueOf(fsm.nextOutput(states.get(1), "a,a,a")) + " - " + fsm.nextStateWithSequence(states.get(1), "a,a,a"));
      System.out.println(String.valueOf(fsm.nextOutput(states.get(2), "a,b")) + " - " + fsm.nextStateWithSequence(states.get(2), "a,b"));
      System.out.println(String.valueOf(fsm.nextOutput(states.get(3), "a,b")) + " - " + fsm.nextStateWithSequence(states.get(3), "a,b"));
      System.out.println(String.valueOf(fsm.nextOutput(states.get(4), "a,a,a")) + " - " + fsm.nextStateWithSequence(states.get(4), "a,a,a"));
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
      File file = new File("./test/hierons.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.setInitialTestSet(TestSequence.getAllPrefixesFrom("a,a,a,a,b,a,a,b,a,a,b,a,a,b,a,b,a,a,a,a,b,b,a,b,a,a,b,b,a,a,b,a,a,b,a,a,a,b"));
      generator.generate();
      System.out.println(TestSet.size(generator.getFinalTestSet()));
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test04() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/hierons.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.setInitialTestSet(TestSequence.getAllPrefixesFrom("a,a,a,a,b,a,a,b,a,a,b,a,a,b,a,b,a,a,a,a,b,b,a,b,a,a,b,b,a,a,b,a,a,b,a,a,a,b"));
      generator.generate();
      System.out.println(TestSet.size(generator.getFinalTestSet()));
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test05() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/journal-ex01.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.generate();
      System.out.println(TestSet.size(generator.getFinalTestSet()));
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
