package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PCompleteStep02Test {
  ArrayList<Pair> C;
  
  ArrayList<Pair> D;
  
  ArrayList<String> T;
  
  ArrayList<String> K;
  
  PCompleteTestGenerator generator;
  
  @Before
  public void setup() {
    File file = new File("./test/dorofeeva.txt");
    FsmModelReader reader = new FsmModelReader(file);
    FiniteStateMachine fsm = null;
    try {
      fsm = reader.getFsm();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    this.generator = new PCompleteTestGenerator(fsm);
    this.C = new ArrayList<Pair>();
    this.D = new ArrayList<Pair>();
    this.T = new ArrayList<String>();
    this.K = new ArrayList<String>();
  }
  
  @Test
  public void testIsMDivergent() {
    ArrayList<String> testset = new ArrayList<String>();
    testset.add(TestSequence.EPSILON);
    testset.add("x,y");
    Assert.assertFalse(this.generator.isM_Divergent(testset));
    testset = new ArrayList<String>();
    testset.add(TestSequence.EPSILON);
    testset.add("y");
    testset.add("y,y");
    testset.add("x,y,y,y,x");
    Assert.assertTrue(this.generator.isM_Divergent(testset));
  }
  
  @Test
  public void testSelectAlpha01() {
    this.K.add(TestSequence.EPSILON);
    Assert.assertTrue(!(!this.generator.selectAlpha(this.K).equals("x") && !this.generator.selectAlpha(this.K).equals("y")));
  }
  
  @Test
  public void testSelectAlpha02() {
    this.K.add("y");
    this.K.add("y,y");
    this.K.add("x");
    Assert.assertTrue(this.generator.selectAlpha(this.K).equals(TestSequence.EPSILON));
  }
  
  @Test
  public void testSelectAlpha03() {
    this.K.add(TestSequence.EPSILON);
    this.K.add("y");
    this.K.add("x");
    Assert.assertTrue((this.generator.selectAlpha(this.K).length() == 3));
  }
  
  @Test
  public void testSelectGamma01() {}
}
