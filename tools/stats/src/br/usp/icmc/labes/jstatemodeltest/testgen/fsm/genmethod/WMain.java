package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.CharacterizationSetConstructor;
import java.io.File;

public class WMain {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("No FSM file specified.");
    } else {
      File file = new File(args[0]);
      FsmModelReader reader = new FsmModelReader(file, true);
      try {
        FiniteStateMachine fsm = reader.getFsm();
        if (args.length == 1) {
          WMethod method = new WMethod(fsm);
          for (String test : method.getSequencesWithoutPrefixes())
            System.out.println(test.replaceAll(",", "")); 
        } else if (args.length == 2) {
          CharacterizationSetConstructor csconst = new CharacterizationSetConstructor(fsm);
          for (String test : csconst.getWset())
            System.out.println(test.replaceAll(",", "")); 
          String s = TestSet.getLongestSequence(csconst.getWset());
          System.out.println("SIZE: " + TestSequence.lenght(s));
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
