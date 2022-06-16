package paderborn;

import br.usp.icmc.labes.jstatemodeltest.common.FSM2Dot;
import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.pcomplete.PCompleteTestGenerator;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.CharacterizationSetConstructor;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.WMethod;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class Example01 {
  @Test
  public void test01() {
    try {
      File file = new File("./test/dorofeeva.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      System.out.println("# states: " + fsm.getNumberOfStates());
      System.out.println("# transitions: " + fsm.getNumberOfTransitions());
      System.out.println("# input symbols: " + fsm.getInputAlphabet().size());
      System.out.println("# output symbols: " + fsm.getOutputAlphabet().size());
      WMethod method = new WMethod(fsm);
      for (String seq : method.getSequences())
        System.out.println(seq); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./test/proj02-exgreater5.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      System.out.println("# states: " + fsm.getNumberOfStates());
      System.out.println("# transitions: " + fsm.getNumberOfTransitions());
      System.out.println("# input symbols: " + fsm.getInputAlphabet().size());
      System.out.println("# output symbols: " + fsm.getOutputAlphabet().size());
      fsm.checkMinimality();
      WMethod me = new WMethod(fsm);
      CharacterizationSetConstructor csconst = new CharacterizationSetConstructor(fsm);
      System.out.println("W: " + csconst.getWset());
      System.out.println("W: ");
      System.out.println("W: " + TestSet.size(me.getSequencesWithoutPrefixes()));
      System.out.println("Number of resets: " + me.getSequencesWithoutPrefixes().size());
      for (String s : me.getSequencesWithoutPrefixes())
        System.out.println(s.replace(",", "")); 
      PCompleteTestGenerator gen = new PCompleteTestGenerator(fsm);
      gen.generate();
      System.out.println("P: " + TestSet.size(gen.getFinalTestSet()));
      System.out.println("Resets: " + gen.getFinalTestSet().size());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    try {
      File file = new File("/home/andre/workInGermany/Project02/stateCounting/sc-ts-5.txt");
      TestSuiteReader tsr = new TestSuiteReader(file, false);
      ArrayList<String> noPrefs = TestSequence.getNoPrefixes(tsr.getTestSuite());
      for (String tc : noPrefs)
        System.out.println(tc); 
      System.out.println("size: " + TestSet.size(noPrefs));
      System.out.println("resets: " + noPrefs.size());
      File file1 = new File("./test/proj02-ex1.txt");
      FsmModelReader reader = new FsmModelReader(file1);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator gen = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.OFF);
      gen.setInitialTestSet(noPrefs);
      gen.generate();
      System.out.println("P: " + TestSet.size(gen.getFinalTestSet()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test04() {
    try {
      File file = new File("./test/specials_fsm.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      System.out.println("# states: " + fsm.getNumberOfStates());
      System.out.println("# transitions: " + fsm.getNumberOfTransitions());
      System.out.println("# input symbols: " + fsm.getInputAlphabet().size());
      System.out.println("# input symbols: " + fsm.getInputAlphabet());
      System.out.println("# output symbols: " + fsm.getOutputAlphabet().size());
      System.out.println("# output symbols: " + fsm.getOutputAlphabet());
      System.out.println("# transitions: " + fsm.getTransitions().size());
      fsm.checkMinimality();
      fsm.printNumbersFSM();
      FSM2Dot f2d = new FSM2Dot(fsm);
      f2d.printDot();
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test05() {
    try {
      File file = new File("./test/ISELTA_specials_small_reduced.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      System.out.println("# states: " + fsm.getNumberOfStates());
      System.out.println("# transitions: " + fsm.getNumberOfTransitions());
      System.out.println("# input symbols: " + fsm.getInputAlphabet().size());
      System.out.println("# output symbols: " + fsm.getOutputAlphabet().size());
      PCompleteTestGenerator gen = new PCompleteTestGenerator(fsm);
      gen.generate();
      for (String t : gen.getFinalTestSet())
        System.out.println(t); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
