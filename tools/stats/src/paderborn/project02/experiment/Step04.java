package paderborn.project02.experiment;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import org.junit.Test;

public class Step04 {
  @Test
  public void test01() {
    File file = new File("./project02-experiment/prices/prices-deterministic_rrg-1seq-tb_3seqproductionrules_sentences_transformed_dupfiltered.txt");
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = "";
      ArrayList<String> testsuite = new ArrayList<String>();
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        String[] parts = line.split(" ");
        String printSeq = "";
        byte b;
        int i;
        String[] arrayOfString1;
        for (i = (arrayOfString1 = parts).length, b = 0; b < i; ) {
          String p = arrayOfString1[b];
          String[] temp = p.split("_");
          printSeq = String.valueOf(printSeq) + temp[0] + ",";
          b++;
        } 
        printSeq = printSeq.substring(0, printSeq.length() - 1);
        if (!testsuite.contains(printSeq))
          testsuite.add(printSeq); 
      } 
      testsuite = TestSequence.getNoPrefixes(testsuite);
      File outfile = new File("./project02-experiment/testsuite.txt");
      FileWriter fw = new FileWriter(outfile);
      for (String t : testsuite) {
        System.out.println(t);
        fw.write(String.valueOf(t) + "\n");
      } 
      fw.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test02() {
    try {
      File file = new File("./project02-experiment/prices/prices-reduced-enabledEvents.fsm");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      fsm.printFSMnoEnabled();
    } catch (Exception exception) {}
  }
}
