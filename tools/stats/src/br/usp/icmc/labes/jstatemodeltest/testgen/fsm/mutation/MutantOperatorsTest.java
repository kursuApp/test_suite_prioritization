package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MutantOperatorsTest {
  FiniteStateMachine mutant;
  
  FiniteStateMachine fsm;
  
  MutantOperators op;
  
  @Before
  public void before() throws Exception {
    File file = new File("./test/endo11.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    this.fsm = reader.getFsm();
    this.op = new MutantOperators(this.fsm);
  }
  
  @Test
  public void test01() throws Exception {
    this.mutant = this.op.operatorCIS(this.fsm.getStates().get(1));
    Assert.assertNotNull(this.mutant);
    Assert.assertEquals("S4", this.mutant.getInitialState().getLabel());
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
  
  @Test
  public void test02() throws Exception {
    this.mutant = this.op.operatorCO(this.fsm.getTransitions().get(0), "1");
    Assert.assertNotNull(this.mutant);
    Assert.assertEquals("1", ((Transition)this.mutant.getTransitions().get(0)).getOutput());
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
  
  @Test
  public void test03() throws Exception {
    this.mutant = this.op.operatorTSE(this.fsm.getTransitions().get(0), this.fsm.getInitialState());
    Assert.assertNotNull(this.mutant);
    Assert.assertEquals(this.mutant.getInitialState(), ((Transition)this.mutant.getTransitions().get(0)).getOut());
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
  
  @Test
  public void test04() throws Exception {
    File file = new File("./test/Partial01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    this.fsm = reader.getFsm();
    this.op = new MutantOperators(this.fsm);
    System.out.println((new StringBuilder()).append(this.fsm.getTransitions().get(0)).append(", ").append(this.fsm.getStates().get(1)).toString());
    this.mutant = this.op.operatorHSE(this.fsm.getTransitions().get(0), this.fsm.getStates().get(1));
    Assert.assertNotNull(this.mutant);
    Assert.assertEquals(2L, this.mutant.getStateWithLabel("s1").getOut().size());
    Assert.assertEquals(2L, this.mutant.getStateWithLabel("s2").getOut().size());
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
  
  @Test
  public void test05() throws Exception {
    File file = new File("./test/Partial01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    this.fsm = reader.getFsm();
    this.op = new MutantOperators(this.fsm);
    System.out.println((new StringBuilder()).append(this.fsm.getTransitions().get(0)).append(", ").toString());
    this.mutant = this.op.operatorCI(this.fsm.getTransitions().get(0), "d");
    Assert.assertNotNull(this.mutant);
    this.fsm.printByStructure();
    this.mutant.printByStructure();
    this.mutant = this.op.operatorCI(this.fsm.getTransitions().get(0), "a");
    Assert.assertNull(this.mutant);
    this.mutant = this.op.operatorCI(this.fsm.getTransitions().get(0), "c");
    Assert.assertNull(this.mutant);
  }
  
  @Test
  public void test06() throws Exception {
    this.mutant = this.op.operatorMT(this.fsm.getTransitions().get(0));
    Assert.assertNotNull(this.mutant);
    Assert.assertEquals(9L, this.mutant.getTransitions().size());
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
  
  @Test
  public void test07() throws Exception {
    this.mutant = this.op.operatorES(this.fsm.getTransitions().get(0), this.fsm.getStateWithLabel("S4"));
    Assert.assertNotNull(this.mutant);
    Assert.assertEquals(6L, this.mutant.getStates().size());
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
  
  @Test
  public void test08() throws Exception {
    this.mutant = this.op.operatorES(this.fsm.getTransitions().get(5), this.fsm.getStateWithLabel("S4"));
    Assert.assertNotNull(this.mutant);
    Assert.assertEquals(6L, this.mutant.getStates().size());
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
  
  @Test
  public void test09() throws Exception {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    this.fsm = reader.getFsm();
    this.op = new MutantOperators(this.fsm);
    this.mutant = this.op.operatorES(this.fsm.getTransitions().get(0), this.fsm.getStateWithLabel("s2"));
    Assert.assertNotNull(this.mutant);
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
  
  @Test
  public void test10() throws Exception {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    this.fsm = reader.getFsm();
    this.op = new MutantOperators(this.fsm);
    System.out.println(this.fsm.getTransitions().get(5));
    this.mutant = this.op.operatorES(this.fsm.getTransitions().get(5), this.fsm.getStateWithLabel("s3"));
    Assert.assertNotNull(this.mutant);
    this.fsm.printByStructure();
    this.mutant.printByStructure();
  }
}
