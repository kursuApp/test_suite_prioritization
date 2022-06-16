package paderborn.project02.experiment;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.mutation.MutantEnvironment;
import java.io.File;
import org.junit.Test;

public class Step07 {
  @Test
  public void test00() throws Exception {
    detectEquivalents("./project02-experiment/specials/", "specials-reduced-noEnabled.fsm");
  }
  
  @Test
  public void test01() throws Exception {
    detectEquivalents("./project02-experiment/specials/", "specials.fsm");
  }
  
  @Test
  public void test02() throws Exception {
    detectEquivalents("./project02-experiment/additionals/", "additionals-main-reduced-NoEnabledEvents.fsm");
  }
  
  @Test
  public void test03() throws Exception {
    detectEquivalents("./project02-experiment/additionals/", "additionals-main.fsm");
  }
  
  @Test
  public void test04() throws Exception {
    detectEquivalents("./project02-experiment/prices/", "prices-reduced-noEnabled.fsm");
  }
  
  @Test
  public void test05() throws Exception {
    detectEquivalents("./project02-experiment/prices/", "prices.fsm");
  }
  
  private void detectEquivalents(String DIR, String modelName) throws Exception {
    String TESTMODEL = String.valueOf(DIR) + modelName;
    System.out.println("--------------------------");
    System.out.println("model: " + modelName);
    File file = new File(TESTMODEL);
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    MutantEnvironment env = new MutantEnvironment(fsm);
    env.detectEquivalents();
  }
}
