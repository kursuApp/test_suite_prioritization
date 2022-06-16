package paderborn.project02.experiment;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FSMStats;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

public class Step05 {
  @Test
  public void test01() throws Exception {
    File file = new File("./project02-experiment/prices/prices-reduced-noEnabled.fsm");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printStats();
    FSMStats stats = new FSMStats(fsm);
    stats.print();
  }
  
  @Test
  public void test02() throws Exception {
    String dir = "./project02-experiment/prices/";
    System.out.println("-----------");
    System.out.println("State coverage");
    File file = new File(String.valueOf(dir) + "/TS-StateCoverage.txt");
    TestSuiteReader tsr = new TestSuiteReader(file, true);
    ArrayList<String> testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("Transition coverage");
    file = new File(String.valueOf(dir) + "/TS-TransitionCoverage.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("HSI (n)");
    file = new File(String.valueOf(dir) + "/TS-hsi-n.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("HSI (n+1)");
    file = new File(String.valueOf(dir) + "/TS-hsi-n1.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("HSI (n+2)");
    file = new File(String.valueOf(dir) + "/TS-hsi-n2.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("2-sequence");
    file = new File(String.valueOf(dir) + "/TS-k-sequence-1.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("3-sequence");
    file = new File(String.valueOf(dir) + "/TS-k-sequence-2.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("4-sequence");
    file = new File(String.valueOf(dir) + "/TS-k-sequence-3.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
  }
  
  @Test
  public void test03() throws Exception {
    String dir = "./project02-experiment/prices/";
    String modelFile = "./project02-experiment/prices/prices-reduced-noEnabled.fsm";
    File fModel = new File(modelFile);
    FsmModelReader reader = new FsmModelReader(fModel, true);
    FiniteStateMachine fsm = reader.getFsm();
    System.out.println("-----------");
    System.out.println("HSI (n)");
    File file = new File(String.valueOf(dir) + "/TS-hsi-n.txt");
    checkDefinedSequences(file, fsm);
    System.out.println("-----------");
    System.out.println("HSI (n+1)");
    file = new File(String.valueOf(dir) + "/TS-hsi-n1.txt");
    checkDefinedSequences(file, fsm);
    System.out.println("-----------");
    System.out.println("2-sequence");
    file = new File(String.valueOf(dir) + "/TS-k-sequence-1.txt");
    checkDefinedSequences(file, fsm);
    System.out.println("-----------");
    System.out.println("3-sequence");
    file = new File(String.valueOf(dir) + "/TS-k-sequence-2.txt");
    checkDefinedSequences(file, fsm);
    System.out.println("-----------");
    System.out.println("4-sequence");
    file = new File(String.valueOf(dir) + "/TS-k-sequence-3.txt");
    checkDefinedSequences(file, fsm);
  }
  
  private void checkDefinedSequences(File file, FiniteStateMachine fsm) {
    TestSuiteReader tsr = new TestSuiteReader(file, true);
    ArrayList<String> testsuite = tsr.getTestSuite();
    for (String test : testsuite) {
      if (!fsm.isDefinedSeq(test, fsm.getInitialState()))
        System.out.println(" : " + test); 
    } 
  }
}
