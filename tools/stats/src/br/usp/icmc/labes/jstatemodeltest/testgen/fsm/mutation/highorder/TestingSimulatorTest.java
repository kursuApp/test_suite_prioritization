package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.highorder;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.Operator;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class TestingSimulatorTest {
  @Test
  public void testgetNumberOfExecutedEvents() {
    Assert.assertEquals(1L, TestingSimulator.getNumberOfExecutedEvents("0::1::0::1", "1::0::1::1"));
    Assert.assertEquals(2L, TestingSimulator.getNumberOfExecutedEvents("0::1::0::1", "0::0::1::1"));
    Assert.assertEquals(3L, TestingSimulator.getNumberOfExecutedEvents("0::1::0::1", "0::1::1::1"));
    Assert.assertEquals(4L, TestingSimulator.getNumberOfExecutedEvents("0::1::0::1", "0::1::0::2"));
    Assert.assertEquals(4L, TestingSimulator.getNumberOfExecutedEvents("0::1::0::1", "0::1::0"));
    Assert.assertEquals(1L, TestingSimulator.getNumberOfExecutedEvents("0::1::0::1", "1"));
  }
  
  @Test
  public void test01() throws Exception {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    TestingSimulator simulator = new TestingSimulator(fsm);
    ArrayList<OperatorApplication> applications = new ArrayList<OperatorApplication>();
    OperatorApplication app1 = new OperatorApplication();
    app1.setOperator(Operator.CIS);
    app1.setState(fsm.getStateWithLabel("s2"));
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.MT);
    app1.setTransition(fsm.getTransitions().get(5));
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.CO);
    app1.setTransition(fsm.getTransitions().get(1));
    app1.setOutput("1");
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.CI);
    app1.setTransition(fsm.getTransitions().get(4));
    app1.setInput("b");
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.TSE);
    app1.setTransition(fsm.getTransitions().get(2));
    app1.setState(fsm.getStateWithLabel("s3"));
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.HSE);
    app1.setTransition(fsm.getTransitions().get(0));
    app1.setState(fsm.getStateWithLabel("s3"));
    applications.add(app1);
    simulator.generateHighOrderMutant(applications);
    file = new File("./test/Simao09CJ-fsm01-tsp.txt");
    TestSuiteReader treader = new TestSuiteReader(file, false);
    ArrayList<String> ts = treader.getTestSuite();
    simulator.performTestingSimulation(ts);
  }
  
  @Test
  public void test02() throws Exception {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    TestingSimulator simulator = new TestingSimulator(fsm);
    ArrayList<OperatorApplication> applications = new ArrayList<OperatorApplication>();
    OperatorApplication app1 = new OperatorApplication();
    app1.setOperator(Operator.CIS);
    app1.setState(fsm.getStateWithLabel("s2"));
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.MT);
    app1.setTransition(fsm.getTransitions().get(4));
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.CO);
    app1.setTransition(fsm.getTransitions().get(1));
    app1.setOutput("1");
    applications.add(app1);
    TestingSimulator.getLogger().setLevel(Level.OFF);
    simulator.generateHighOrderMutant(applications);
    file = new File("./test/Simao09CJ-fsm01-tsp.txt");
    TestSuiteReader treader = new TestSuiteReader(file, false);
    ArrayList<String> ts = treader.getTestSuite();
    simulator.performTestingSimulation(ts);
  }
  
  @Test
  public void test03() throws Exception {
    File file = new File("./test/Simao09CJ-fsm01.txt");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    TestingSimulator simulator = new TestingSimulator(fsm);
    ArrayList<OperatorApplication> applications = new ArrayList<OperatorApplication>();
    OperatorApplication app1 = new OperatorApplication();
    app1.setOperator(Operator.MT);
    app1.setTransition(fsm.getTransitions().get(4));
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.CO);
    app1.setTransition(fsm.getTransitions().get(1));
    app1.setOutput("1");
    applications.add(app1);
    app1 = new OperatorApplication();
    app1.setOperator(Operator.TSE);
    app1.setTransition(fsm.getTransitions().get(2));
    app1.setState(fsm.getStateWithLabel("s3"));
    applications.add(app1);
    simulator.generateHighOrderMutant(applications);
    file = new File("./test/Simao09CJ-fsm01-tsp.txt");
    TestSuiteReader treader = new TestSuiteReader(file, false);
    ArrayList<String> ts = treader.getTestSuite();
    simulator.performTestingSimulation(ts);
  }
}
