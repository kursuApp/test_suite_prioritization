package paderborn.project02.experiment;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.coversets.StateTransitionCoverageGenerator;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi.HeuHsiMethod;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class Step03 {
  @Test
  public void test01() {
    try {
      callTest("./project02-experiment/additionals/additionals-main-reduced-enabledEvents.fsm");
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  protected void callTest(String filename) throws Exception {
    File file = new File(filename);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    HeuHsiMethod method = new HeuHsiMethod(fsm);
    HeuHsiMethod.getLogger().setLevel(Level.OFF);
    method.setTraversalSize(0);
    method.generateN1();
    System.out.println("--------------------------------------------------------------");
    System.out.println("NO EnabledInputs");
    System.out.println("length: " + TestSet.size(method.getNoEnabledInputsequences()));
    System.out.println("# TCs: " + method.getNoEnabledInputsequences().size());
    System.out.println("length (no resets): " + (TestSet.size(method.getNoEnabledInputsequences()) - method.getNoEnabledInputsequences().size()));
    System.out.println("--------------------------------------------------------------");
    File outfile = new File("./project02-experiment/testsuite.txt");
    FileWriter fw = new FileWriter(outfile);
    for (String seq : method.getNoEnabledInputsequences())
      fw.write(String.valueOf(seq) + "\n"); 
    fw.close();
  }
  
  protected void callTestWeakerTestSuites(String filename) throws Exception {
    File file = new File(filename);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    StateTransitionCoverageGenerator method = new StateTransitionCoverageGenerator(fsm);
    method.generate();
    printSet(method.getStateCoverageTestSuite(), "TS-StateCoverage");
    printSet(method.getTransitionCoverageTestSuite(), "TS-TransitionCoverage");
  }
  
  private void printSet(ArrayList<String> testSuite, String method) throws IOException {
    System.out.println("--------------------------------------------------------------");
    System.out.println(method);
    System.out.println("length: " + TestSet.size(testSuite));
    System.out.println("# TCs: " + testSuite.size());
    System.out.println("length (no resets): " + (TestSet.size(testSuite) - testSuite.size()));
    System.out.println("--------------------------------------------------------------");
    File outfile = new File("./project02-experiment/" + method + ".txt");
    FileWriter fw = new FileWriter(outfile);
    for (String seq : testSuite)
      fw.write(String.valueOf(seq) + "\n"); 
    fw.close();
  }
}
