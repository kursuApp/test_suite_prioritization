package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class M0126Test {
  @Test
  public void test01() {
    try {
      File file = new File("./test/m0126_mef2.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      TestSet.addAllPrefsOf(initialTestSet, "ID,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,ID,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,ID,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,PG,CS,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,PG,EX,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,PG,ID,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,PG,LG,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,PG,NG,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,PG,RG,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,PG,SS,ID");
      TestSet.addAllPrefsOf(initialTestSet, "SB,PL,PG,SVG,ID");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      TestSet.addAllPrefsOf(initialTestSet, "b,b,a,a,a,b,b,b,b,b,b,a,b,b,a,a,b");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
