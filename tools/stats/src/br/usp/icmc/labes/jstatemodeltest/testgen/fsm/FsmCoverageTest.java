package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class FsmCoverageTest {
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
      ArrayList<String> testset = new ArrayList<String>();
      testset.add("EPSILON");
      testset.add("a");
      testset.add("b,b");
      FsmCoverage cover = new FsmCoverage(fsm);
      System.out.println(cover.getNonCoveredtransitions(testset));
      Assert.assertEquals(null, 0.1666D, cover.transitionCoverage(testset), 0.001D);
      testset.add("b,a");
      testset.add("a,a");
      testset.add("a,b");
      testset.add("b");
      System.out.println(cover.getNonCoveredtransitions(testset));
      Assert.assertEquals(null, 1.0D, cover.transitionCoverage(testset), 0.001D);
      Assert.assertTrue(cover.isInitializedTransitionCoverage(testset));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./test/dorofeeva.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      FsmCoverage cover = new FsmCoverage(fsm);
      ArrayList<String> testset = new ArrayList<String>();
      testset.add(TestSequence.EPSILON);
      System.out.println(cover.getNonCoveredtransitions(testset));
      Assert.assertEquals(8L, cover.getNonCoveredtransitions(testset).size());
      testset.add("x");
      testset.add("x,y");
      System.out.println(cover.getNonCoveredtransitions(testset));
      Assert.assertEquals(6L, cover.getNonCoveredtransitions(testset).size());
      testset.add("y");
      System.out.println(cover.getNonCoveredtransitions(testset));
      Assert.assertEquals(5L, cover.getNonCoveredtransitions(testset).size());
      testset.add("y,x");
      System.out.println(cover.getNonCoveredtransitions(testset));
      Assert.assertEquals(4L, cover.getNonCoveredtransitions(testset).size());
      testset.add("y,y");
      System.out.println(cover.getNonCoveredtransitions(testset));
      Assert.assertEquals(3L, cover.getNonCoveredtransitions(testset).size());
      testset.add("x,y,x");
      testset.add("x,y,x,x");
      testset.add("y,y,y");
      testset.add("y,y,x");
      System.out.println(cover.getNonCoveredtransitions(testset));
      Assert.assertEquals(0L, cover.getNonCoveredtransitions(testset).size());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
