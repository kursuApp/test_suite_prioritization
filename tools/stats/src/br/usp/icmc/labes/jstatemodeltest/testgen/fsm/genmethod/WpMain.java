package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;

public class WpMain {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("No FSM file specified.");
    } else {
      File file = new File(args[0]);
      FsmModelReader reader = new FsmModelReader(file, true);
      try {
        FiniteStateMachine fsm = reader.getFsm();
        if (args.length == 1) {
          WpMethod method = new WpMethod(fsm);
          for (String test : method.getSequences())
            System.out.println(test.replaceAll(",", "")); 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
