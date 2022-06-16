package paderborn;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi.FsmMinimization;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.junit.Assert;
import org.junit.Test;

public class ReadDotFile {
  @Test
  public void test01() {
    File file = new File("./test/specials_deterministic_withoutputs_an.dot");
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = "";
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.startsWith("\"")) {
          String[] tokens = line.split(" ");
          String state1 = tokens[0].replace("\"", "");
          state1 = state1.replace(" ", "_");
          String state2 = tokens[2].replace("\"", "");
          state2 = state2.replace(" ", "_");
          tokens = line.split("= ");
          String transition = tokens[1].replace(" ];", "");
          tokens = transition.split(" / ");
          String input = tokens[0].replace("\"", "");
          input = input.replace(" ", "_");
          String output = tokens[1].replace("\"", "");
          output = output.replace(" ", "_");
          System.out.println(String.valueOf(state1) + " -- " + input + " / " + output + " -> " + state2);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./upb_models/specials_fsm-part1.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printInfo();
      fsm.addEnabledInputs();
      fsm.printFSM();
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    try {
      File file = new File("./upb_models/specials_fsm-part2.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printInfo();
      FsmMinimization min = new FsmMinimization(fsm);
      min.minimize();
      fsm.printInfo();
      fsm.printFSM();
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
