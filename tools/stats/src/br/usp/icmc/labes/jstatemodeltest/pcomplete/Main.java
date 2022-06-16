package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("No FSM file specified.");
    } else {
      File file = new File(args[0]);
      FsmModelReader reader = new FsmModelReader(file, true);
      try {
        FiniteStateMachine fsm = reader.getFsm();
        PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
        if (args.length >= 2) {
          TestSuiteReader treader;
          File testsuitefile = new File(args[1]);
          if (args.length >= 3) {
            treader = new TestSuiteReader(testsuitefile, true);
          } else {
            treader = new TestSuiteReader(testsuitefile, false);
          } 
          ArrayList<String> initialSet = new ArrayList<String>();
          for (String testseq : treader.getTestSuite())
            TestSet.addAllPrefsOf(initialSet, testseq); 
          generator.setInitialTestSet(initialSet);
        } 
        generator.generate();
        for (String test : generator.getFinalTestSet()) {
          if (args.length >= 3) {
            System.out.println(test);
            continue;
          } 
          System.out.println(test.replaceAll(",", ""));
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
