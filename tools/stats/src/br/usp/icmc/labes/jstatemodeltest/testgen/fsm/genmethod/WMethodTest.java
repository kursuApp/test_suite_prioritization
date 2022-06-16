package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;

public class WMethodTest {
  @Test
  public void test01() {
    try {
      File file = new File("./test/dorofeeva.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      WMethod method = new WMethod(fsm);
      System.out.println(method.getSequences().size());
      Assert.assertTrue((method.getSequences().size() >= 10));
      method = new WMethod(fsm, 0);
      System.out.println(method.getSequences().size());
      Assert.assertTrue((method.getSequences().size() >= 10));
      method = new WMethod(fsm, 1);
      System.out.println(method.getSequences().size());
      Assert.assertTrue((method.getSequences().size() > 10));
      method = new WMethod(fsm, 2);
      System.out.println(method.getSequences().size());
      Assert.assertTrue((method.getSequences().size() > 20));
      method = new WMethod(fsm, 3);
      System.out.println(method.getSequences().size());
      Assert.assertTrue((method.getSequences().size() > 20));
      method = new WMethod(fsm, 4);
      System.out.println(method.getSequences().size());
      Assert.assertTrue((method.getSequences().size() > 20));
      method = new WMethod(fsm, 5);
      System.out.println(method.getSequences().size());
      Assert.assertTrue((method.getSequences().size() > 20));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void testSymbolOperator() {
    HashSet<String> set = new HashSet<String>();
    set.add("x");
    set.add("y");
    Assert.assertTrue((SymbolOperator.power(set, 0).size() == 1));
    Assert.assertTrue((SymbolOperator.power(set, 1).size() == 2));
    Assert.assertTrue((SymbolOperator.power(set, 2).size() == 4));
    System.out.println(SymbolOperator.power(set, 2));
    System.out.println(SymbolOperator.power(set, 3));
    System.out.println(SymbolOperator.power(set, 4));
    set.add("z");
    Assert.assertTrue((SymbolOperator.power(set, 0).size() == 1));
    Assert.assertTrue((SymbolOperator.power(set, 1).size() == 3));
    Assert.assertTrue((SymbolOperator.power(set, 2).size() == 9));
    System.out.println(SymbolOperator.power(set, 2));
    System.out.println(SymbolOperator.power(set, 3));
  }
}
