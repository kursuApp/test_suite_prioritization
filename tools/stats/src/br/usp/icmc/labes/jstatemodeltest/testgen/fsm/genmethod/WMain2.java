package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.CharacterizationSetConstructor;

import java.io.File;

public class WMain2 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("No FSM file specified.");
        } else {
            File file = new File(args[0]);
            FsmModelReader reader = new FsmModelReader(file, true);
            try {
                FiniteStateMachine fsm = reader.getFsm();
                CharacterizationSetConstructor csconst = new CharacterizationSetConstructor(fsm);
                for (String test : csconst.getWset()){
                    System.out.println(test);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
