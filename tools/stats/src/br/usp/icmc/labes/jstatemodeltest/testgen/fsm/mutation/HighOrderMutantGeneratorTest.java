package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class HighOrderMutantGeneratorTest {
  @Test
  public void test01() {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printByStructure();
      HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
      LinkedHashSet<FiniteStateMachine> mutants = mgen.getMutants();
      System.out.println(mutants.size());
    } catch (Exception e) {
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    File file = new File("./mutants/fsm1.txt");
    File file2 = new File("./mutants/tsp3.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    TestSuiteReader ts_reader = new TestSuiteReader(file2, false);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
      mgen.execute(ts_reader.getTestSuite());
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    File file2 = new File("./test/Simao09CJ-fsm01-tsp.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    TestSuiteReader ts_reader = new TestSuiteReader(file2, false);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
      mgen.execute(ts_reader.getTestSuite());
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test04() {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    File file2 = new File("./test/Simao09CJ-fsm01-tsp.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    TestSuiteReader ts_reader = new TestSuiteReader(file2, false);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
      mgen.executeN(ts_reader.getTestSuite(), 2);
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test05() {
    File file = new File("./mutants/fsm1.txt");
    File file2 = new File("./mutants/tsp3.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    TestSuiteReader ts_reader = new TestSuiteReader(file2, false);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
      HighOrderMutantGenerator.getLogger().setLevel(Level.ALL);
      mgen.executeN(ts_reader.getTestSuite(), 2);
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test06_30States() {
    File file = new File("./mutants/biggest/fsm1.txt");
    File file2 = new File("./mutants/biggest/tsw.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    TestSuiteReader ts_reader = new TestSuiteReader(file2, false);
    try {
      FiniteStateMachine fsm = reader.getFsm();
      HighOrderMutantGenerator mgen = new HighOrderMutantGenerator(fsm);
      HighOrderMutantGenerator.getLogger().setLevel(Level.ALL);
      mgen.executeN(ts_reader.getTestSuite(), 2);
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    } 
  }
}
