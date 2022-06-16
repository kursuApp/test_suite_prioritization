package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.highorder;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.util.ArrayList;
import java.util.logging.Level;

public class TestingSimulationEnvironment {
  private FiniteStateMachine fsm;
  
  public TestingSimulationEnvironment(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public ArrayList<Integer> performSimulation(ArrayList<String> testSuite, int numOfApplications, int seed) {
    OperatorSelector selector = new OperatorSelector(this.fsm);
    ArrayList<OperatorApplication> opApps = selector.getOperatorApplication(numOfApplications, seed);
    TestingSimulator simulator = new TestingSimulator(this.fsm);
    TestingSimulator.getLogger().setLevel(Level.OFF);
    simulator.generateHighOrderMutant(opApps);
    simulator.performTestingSimulation(testSuite);
    return simulator.getExecutedEvents();
  }
}
