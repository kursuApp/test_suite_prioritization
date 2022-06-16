package paderborn;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import org.junit.Test;

public class TransformMutluFormat {
  @Test
  public void test01() {
    File file = new File("./upb_models/specials-d_rrg-1seq-tb_2seqproductionrules_sentences_transformed.txt");
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = "";
      ArrayList<String> testsuite = new ArrayList<String>();
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        String[] parts = line.split(" ");
        String printSeq = "";
        byte b;
        int i;
        String[] arrayOfString1;
        for (i = (arrayOfString1 = parts).length, b = 0; b < i; ) {
          String p = arrayOfString1[b];
          String[] temp = p.split("_");
          printSeq = String.valueOf(printSeq) + temp[0] + ",";
          b++;
        } 
        printSeq = printSeq.substring(0, printSeq.length() - 1);
        if (!testsuite.contains(printSeq))
          testsuite.add(printSeq); 
      } 
      testsuite = TestSequence.getNoPrefixes(testsuite);
      for (String t : testsuite)
        System.out.println(t); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test02() throws Exception {
    File file = new File("./upb_models/specials_fsm-part1.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    file = new File("./upb_models/specials_testsuite_k2.txt");
    TestSuiteReader tsr = new TestSuiteReader(file, true);
    ArrayList<String> testsuite = tsr.getTestSuite();
    for (String test : testsuite)
      System.out.println(String.valueOf(fsm.isDefinedSeq(test, fsm.getInitialState())) + " : " + test); 
    file = new File("./upb_models/specials_testsuite_k3.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    for (String test : testsuite)
      System.out.println(String.valueOf(fsm.isDefinedSeq(test, fsm.getInitialState())) + " : " + test); 
    file = new File("./upb_models/specials_fsm-part3_orig_noE_HSI.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    for (String test : testsuite)
      System.out.println(String.valueOf(fsm.isDefinedSeq(test, fsm.getInitialState())) + " : " + test); 
    file = new File("./upb_models/specials_fsm-part3_orig_noE_HSI_n1.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    for (String test : testsuite)
      System.out.println(String.valueOf(fsm.isDefinedSeq(test, fsm.getInitialState())) + " : " + test); 
  }
  
  @Test
  public void test03() throws Exception {
    System.out.println("-----------");
    System.out.println("HSI (n)");
    File file = new File("./upb_models/specials_fsm-part3_orig_noE_HSI.txt");
    TestSuiteReader tsr = new TestSuiteReader(file, true);
    ArrayList<String> testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("HSI (n+1)");
    file = new File("./upb_models/specials_fsm-part3_orig_noE_HSI_n1.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("k=2");
    file = new File("./upb_models/specials_testsuite_k2.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
    System.out.println("-----------");
    System.out.println("k=3");
    file = new File("./upb_models/specials_testsuite_k3.txt");
    tsr = new TestSuiteReader(file, true);
    testsuite = tsr.getTestSuite();
    TestSet.printStats(testsuite);
    TestSet.printStatsInLine(testsuite);
  }
  
  @Test
  public void test04() throws Exception {
    File file = new File("./upb_models/specials_fsm-part1.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printStats();
  }
}
