package br.usp.icmc.labes.jstatemodeltest.common;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import java.util.ArrayList;

public class TestSuiteStatsMain {
  public static void main(String[] args) {
    if (args.length >= 1) {
      File file = new File(args[0]);
      TestSuiteReader treader = new TestSuiteReader(file, false);
      ArrayList<String> set1 = treader.getTestSuite();
      TestSet.printStatsInLine(set1);
      System.exit(0);
    } 
    System.exit(-1);
  }
}
