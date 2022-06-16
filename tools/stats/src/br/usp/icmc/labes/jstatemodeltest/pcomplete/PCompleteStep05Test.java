package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class PCompleteStep05Test {
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
  public void testUpdateD01() {
    Ruler ruler = new Ruler(this.C, this.D, this.T);
    this.generator.setRuler(ruler);
    this.T.add("y,y");
    this.T.add("x,x");
    this.T.add("x,x,x,x");
    this.T.add("y,y,x,x,x");
    this.generator.updateD(this.D, this.T, "x,x,x");
    System.out.println("D: " + this.D);
  }
}
