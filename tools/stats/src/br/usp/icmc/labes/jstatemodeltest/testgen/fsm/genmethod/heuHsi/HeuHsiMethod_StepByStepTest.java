package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.io.File;
import org.junit.Test;

public class HeuHsiMethod_StepByStepTest {
  @Test
  public void step01_AddEnabledEvents() throws Exception {
    File file = new File("./examples/example01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printInfo();
    fsm.addEnabledInputs();
    fsm.printFSM();
  }
  
  @Test
  public void step02_Minimization() throws Exception {
    File file = new File("./examples/example01-enabledInputs.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printInfo();
    FsmMinimization min = new FsmMinimization(fsm);
    min.minimize();
    fsm.printInfo();
    fsm.printFSM();
  }
  
  @Test
  public void step03_testCaseGeneration() throws Exception {
    File file = new File("./examples/example01-enabledInputs-reduced.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    HeuHsiMethod method = new HeuHsiMethod(fsm);
    method.generate();
    System.out.println("--------------------------------------------------------------");
    System.out.println("NO EnabledInputs");
    System.out.println("length: " + TestSet.size(method.getNoEnabledInputsequences()));
    System.out.println("# TCs: " + method.getNoEnabledInputsequences().size());
    System.out.println("length (no resets): " + (TestSet.size(method.getNoEnabledInputsequences()) - method.getNoEnabledInputsequences().size()));
    System.out.println("--------------------------------------------------------------");
    for (String seq : method.getNoEnabledInputsequences())
      System.out.println(seq); 
  }
}
