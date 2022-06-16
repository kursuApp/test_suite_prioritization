package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;

public class DistinguishSequenceMain {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("No FSM file specified.");
    } else {
      File file = new File(args[0]);
      FsmModelReader reader = new FsmModelReader(file, true);
      try {
        FiniteStateMachine fsm = reader.getFsm();
        DistinguishingSequenceConstructor c = new DistinguishingSequenceConstructor(fsm);
        String ds = c.getDs();
        System.out.println(ds.replaceAll(",", " "));
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
