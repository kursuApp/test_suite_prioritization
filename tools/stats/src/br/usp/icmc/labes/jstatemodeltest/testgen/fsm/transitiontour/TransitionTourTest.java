package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.transitiontour;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.pcomplete.PCompleteTestGenerator;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class TransitionTourTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      gen(fsm);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/dorofeeva.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      gen(fsm);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/chen.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      gen(fsm);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test04() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/bbtas.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      gen(fsm);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test05() {
    System.out.println("-----------------------------------------");
    try {
      File file = new File("./test/dk15.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      gen(fsm);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  private void gen(FiniteStateMachine fsm) {
    TransitionTour tt = new TransitionTour(fsm);
    ArrayList<String> set1 = tt.getTestset();
    System.out.println(set1);
    System.out.println("n of states: " + fsm.getNumberOfStates());
    System.out.println("TT:" + TestSet.size(set1));
    PCompleteTestGenerator pc = new PCompleteTestGenerator(fsm);
    pc.generate();
    System.out.println("P: " + TestSet.size(pc.getFinalTestSet()));
    pc.setP(fsm.getNumberOfStates() - 1);
    pc.generate();
    System.out.println("P-1: " + TestSet.size(pc.getFinalTestSet()));
    ArrayList<String> initialTestSet = new ArrayList<String>();
    for (String newTest : set1)
      TestSet.addAllPrefsOf(initialTestSet, newTest); 
    pc.setInitialTestSet(initialTestSet);
    pc.setP(fsm.getNumberOfStates());
    pc.generate();
    System.out.println("P(TT): " + TestSet.size(pc.getFinalTestSet()));
  }
}
