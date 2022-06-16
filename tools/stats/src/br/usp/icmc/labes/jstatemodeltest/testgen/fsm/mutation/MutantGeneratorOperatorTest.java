package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.LinkedHashSet;
import org.junit.Assert;
import org.junit.Test;

public class MutantGeneratorOperatorTest {
  @Test
  public void testoperatorCIS() {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      String origInitLabel = fsm.getInitialState().getLabel();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorCIS(fsm);
      Assert.assertEquals(2L, mutants.size());
      for (FiniteStateMachine mut : mutants) {
        Assert.assertNotSame(origInitLabel, mut.getInitialState().getLabel());
        mut.printByStructure();
      } 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorCO() {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorCO(fsm);
      Assert.assertEquals(6L, mutants.size());
      for (FiniteStateMachine mut : mutants)
        mut.printByStructure(); 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorTF() {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorTF(fsm);
      Assert.assertEquals(12L, mutants.size());
      for (FiniteStateMachine mut : mutants)
        mut.printByStructure(); 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorMT() {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorMT(fsm);
      Assert.assertEquals(6L, mutants.size());
      for (FiniteStateMachine mut : mutants) {
        Assert.assertEquals(5L, mut.getTransitions().size());
        mut.printByStructure();
      } 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorES() {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorES(fsm);
      Assert.assertEquals(6L, mutants.size());
      for (FiniteStateMachine mut : mutants) {
        mut.printByStructure();
        Assert.assertEquals(4L, mut.getStates().size());
        Assert.assertTrue(mut.isComplete());
        Assert.assertFalse(mut.isReduced());
      } 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorES2() {
    File file = new File("./test/dorofeeva.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorES(fsm);
      Assert.assertEquals(7L, mutants.size());
      for (FiniteStateMachine mut : mutants) {
        mut.printByStructure();
        Assert.assertEquals(5L, mut.getStates().size());
        Assert.assertTrue(mut.isComplete());
        Assert.assertFalse(mut.isReduced());
      } 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorIEAND() {
    File file = new File("./test/Partial01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorIEAND(fsm);
      Assert.assertEquals(10L, mutants.size());
      for (FiniteStateMachine mut : mutants)
        mut.printByStructure(); 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorOEAND() {
    File file = new File("./test/Partial01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorOEAND(fsm);
      Assert.assertEquals(8L, mutants.size());
      for (FiniteStateMachine mut : mutants)
        mut.printByStructure(); 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorTF02() {
    File file = new File("./test/Partial01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorTF(fsm);
      Assert.assertEquals(12L, mutants.size());
      for (FiniteStateMachine mut : mutants)
        mut.printByStructure(); 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void testoperatorET() {
    File file = new File("./test/Partial01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      MutantGenerator mgen = new MutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.operatorET(fsm);
      Assert.assertEquals(8L, mutants.size());
      for (FiniteStateMachine mut : mutants) {
        Assert.assertEquals(7L, mut.getTransitions().size());
        mut.printByStructure();
      } 
    } catch (Exception e) {
      Assert.fail();
    } 
  }
}
