package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class PCompleteTestGeneratorTest {
  @Test
  public void test01() {
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> T = new ArrayList<String>();
      T.add("EPSILON");
      T.add("a");
      T.add("a,a");
      T.add("a,a,a");
      T.add("b");
      T.add("b,a");
      T.add("b,a,a");
      ArrayList<Pair> pairs = generator.getTSeparatedTestPairs(T);
      System.out.println(pairs);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./test/maycon.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(4L, fsm.getStates().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> T = new ArrayList<String>();
      T.add("EPSILON");
      T.add("ID");
      T.add("ID,ID");
      T.add("BK");
      T.add("BK,ID");
      T.add("BK,ID,ID");
      T.add("BK,PS");
      T.add("BK,PS,ID");
      T.add("BK,PS,ID,ID");
      T.add("BK,PS,ID,ID");
      T.add("BK,PS,RB");
      T.add("BK,PS,RB,ID");
      T.add("BK,PS,RB,ID,ID");
      T.add("BK,PS,NB");
      T.add("BK,PS,NB,ID");
      T.add("BK,PS,NB,ID,ID");
      T.add("BK,PS,LB");
      T.add("BK,PS,LB,ID");
      T.add("BK,PS,LB,ID,ID");
      T.add("BK,PS,SG");
      T.add("BK,PS,SG,ID");
      T.add("BK,PS,SG,ID,ID");
      T.add("BK,PS,EX");
      T.add("BK,PS,EX,ID");
      T.add("BK,PS,EX,ID,ID");
      ArrayList<Pair> pairs = generator.getTSeparatedTestPairs(T);
      System.out.println(pairs);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
