package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PCompleteStep04Test {
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
  public void testIsConvergent01() {
    Assert.assertTrue(this.generator.isConvergent(TestSequence.EPSILON, "x,y,x,y"));
    Assert.assertTrue(this.generator.isConvergent("y,x", "x,x,x,x"));
    Assert.assertFalse(this.generator.isConvergent(TestSequence.EPSILON, "x,y,x"));
    Assert.assertFalse(this.generator.isConvergent("x,x,y", "y,y,x"));
  }
  
  @Test
  public void testFindChi01() {
    this.K.add(TestSequence.EPSILON);
    this.K.add("x");
    this.K.add("y");
    this.K.add("y,y");
    Assert.assertTrue(this.generator.findChi(this.K, "x,y").equals(TestSequence.EPSILON));
    Assert.assertTrue(this.generator.findChi(this.K, "y,y,x").equals("x"));
  }
}
