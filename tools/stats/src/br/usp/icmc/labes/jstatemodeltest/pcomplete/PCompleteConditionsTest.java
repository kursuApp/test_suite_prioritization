package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PCompleteConditionsTest {
  ArrayList<Pair> C;
  
  ArrayList<Pair> D;
  
  ArrayList<String> T;
  
  ArrayList<String> K;
  
  ArrayList<String> CunionK;
  
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
    this.CunionK = new ArrayList<String>();
  }
  
  @Test
  public void testUpdateD01() {
    String fi = "y";
    String chi = "x,x";
    String upsilon1 = "y,y";
    String upsilon2 = "y,x";
    this.T.add(fi);
    this.K.add(chi);
    this.K.add(upsilon1);
    this.K.add(upsilon2);
    this.D.add(new Pair(fi, upsilon1));
    this.D.add(new Pair(fi, upsilon2));
    System.out.println(this.generator.findFiChi(this.T, this.CunionK, this.K, this.D));
    Assert.assertTrue(this.generator.findFiChi(this.T, this.CunionK, this.K, this.D).getlonger().equals("x,x"));
    Assert.assertTrue(this.generator.findFiChi(this.T, this.CunionK, this.K, this.D).getShorter().equals("y"));
  }
}
