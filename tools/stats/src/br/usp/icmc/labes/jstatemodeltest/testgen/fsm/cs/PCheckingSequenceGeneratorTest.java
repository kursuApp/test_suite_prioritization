package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.transitiontour.TransitionTour;
import java.io.File;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class PCheckingSequenceGeneratorTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/hierons.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCheckingSequenceGenerator gen = new PCheckingSequenceGenerator(fsm);
      PCheckingSequenceGenerator.getLogger().setLevel(Level.ALL);
      gen.generate();
      System.out.println("CS: " + gen.getCheckingSequence());
      System.out.println("CS: " + gen.getCheckingSequence().replace(",", ""));
      System.out.println("|CS| = " + TestSequence.lenght(gen.getCheckingSequence()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/hierons.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCheckingSequenceGenerator gen = new PCheckingSequenceGenerator(fsm);
      gen.generate2();
      System.out.println("CS: " + gen.getCheckingSequence());
      System.out.println("CS: " + gen.getCheckingSequence().replace(",", ""));
      System.out.println("|CS| = " + (TestSequence.lenght(gen.getCheckingSequence()) - 1));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCheckingSequenceGenerator gen = new PCheckingSequenceGenerator(fsm);
      gen.setUserSequence("a,a,a,a");
      PCheckingSequenceGenerator.getLogger().setLevel(Level.ALL);
      gen.generate2();
      System.out.println("CS: " + gen.getCheckingSequence());
      System.out.println("CS: " + gen.getCheckingSequence().replace(",", ""));
      System.out.println("|CS| = " + TestSequence.lenght(gen.getCheckingSequence()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test04() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/endo11-cs.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCheckingSequenceGenerator gen = new PCheckingSequenceGenerator(fsm);
      gen.setUserSequence("a,a,a");
      PCheckingSequenceGenerator.getLogger().setLevel(Level.ALL);
      gen.generate2();
      System.out.println("CS: " + gen.getCheckingSequence());
      System.out.println("CS: " + gen.getCheckingSequence().replace(",", ""));
      System.out.println("|CS| = " + (TestSequence.lenght(gen.getCheckingSequence()) - 1));
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
      PCheckingSequenceGenerator gen = new PCheckingSequenceGenerator(fsm);
      gen.setUserSequence("a,b,a,b,a,a,a,b,a,b,b,b,b");
      PCheckingSequenceGenerator.getLogger().setLevel(Level.ALL);
      gen.generate2();
      System.out.println("CS: " + gen.getCheckingSequence());
      System.out.println("CS: " + gen.getCheckingSequence().replace(",", ""));
      System.out.println("|CS| = " + TestSequence.lenght(gen.getCheckingSequence()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test06() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/journal-ex01.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      DistinguishingSequenceConstructor dsconst = new DistinguishingSequenceConstructor(fsm);
      System.out.println(dsconst.getDs());
      for (State s : fsm.getStates())
        System.out.println(s + " " + fsm.nextOutput(s, "a,a,a")); 
      TransitionTour tt = new TransitionTour(fsm);
      System.out.println(tt.getTestset());
      System.out.println(fsm.reachedTransitionsWithSequence(fsm.getInitialState(), "a,b,a,b,a,a,a,b,a,b,b,b,b"));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
