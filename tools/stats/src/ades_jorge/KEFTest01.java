package ades_jorge;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.pcomplete.PCompleteTestGenerator;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.WMethod;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

public class KEFTest01 {
  private static void print(ArrayList<String> testset) {
    System.out.println("-------------");
    for (String s : testset)
      System.out.println(s); 
    System.out.println("Length: " + TestSet.size(testset));
    System.out.println("-------------");
  }
  
  @Test
  public void test01() throws Exception {
    File file = new File("./examples/incremental-el-fakih/fig1.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    PCompleteTestGenerator p = new PCompleteTestGenerator(fsm);
    p.generate();
    print(p.getFinalTestSet());
  }
  
  @Test
  public void test02() throws Exception {
    File file = new File("./examples/incremental-el-fakih/fig2.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    WMethod m = new WMethod(fsm);
    System.out.println("------");
    print(m.getSequences());
    System.out.println("------");
    PCompleteTestGenerator p = new PCompleteTestGenerator(fsm);
    ArrayList<String> initialSet = new ArrayList<String>();
    for (String testseq : m.getSequences())
      TestSet.addAllPrefsOf(initialSet, testseq); 
    p.setInitialTestSet(initialSet);
    p.generate();
    print(p.getFinalTestSet());
  }
  
  @Test
  public void test03() throws Exception {
    File file = new File("./examples/incremental-el-fakih/fig1-hsi.txt");
    TestSuiteReader reader = new TestSuiteReader(file, true);
    ArrayList<String> testset = TestSequence.getNoPrefixes(reader.getTestSuite());
    print(testset);
    System.out.println(TestSet.size(testset));
  }
  
  @Test
  public void testModificationsAndApplyingP_01() throws Exception {
    File file = new File("./examples/incremental-el-fakih/fig1-hsi.txt");
    TestSuiteReader reader = new TestSuiteReader(file, true);
    ArrayList<String> sethsi = reader.getTestSuite();
    ArrayList<String> initialSet = new ArrayList<String>();
    for (String testseq : sethsi)
      TestSet.addAllPrefsOf(initialSet, testseq); 
    File fileoriginal = new File("./examples/incremental-el-fakih/fig1.txt");
    FsmModelReader origreader = new FsmModelReader(fileoriginal, true);
    FiniteStateMachine original = origreader.getFsm();
    System.out.println("HSI on original");
    ArrayList<String> ioTS_orig = print(sethsi, original);
    File filefsm = new File("./examples/incremental-el-fakih/fig1-m1.txt");
    FsmModelReader fsmreader = new FsmModelReader(filefsm, true);
    FiniteStateMachine fsm = fsmreader.getFsm();
    PCompleteTestGenerator p = new PCompleteTestGenerator(fsm);
    p.setInitialTestSet(initialSet);
    p.generate();
    System.out.println("P(HSI) on modified");
    ArrayList<String> ioTS_modified = print(p.getFinalTestSet(), fsm);
    ArrayList<String> toApply = TestSet.minus(ioTS_modified, ioTS_orig);
    System.out.println("IO( P(HSI) ) - IO( HSI )");
    print(toApply);
  }
  
  private static ArrayList<String> print(ArrayList<String> testset, FiniteStateMachine sm) {
    System.out.println("-------------");
    ArrayList<String> ret = new ArrayList<String>();
    for (String s : testset) {
      System.out.println(sm.getIOSequence(s));
      ret.add(sm.getIOSequence(s));
    } 
    System.out.println("Length: " + TestSet.size(testset));
    System.out.println("-------------");
    return ret;
  }
}
