import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.pcomplete.PCompleteTestGenerator;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import java.io.File;
import java.util.logging.Level;
import org.junit.Test;

public class BenchmarkPropertiesTest {
  @Test
  public void determinism() throws Exception {
    File dir = new File("./test/fsm_number");
    byte b;
    int i;
    File[] arrayOfFile;
    for (i = (arrayOfFile = dir.listFiles()).length, b = 0; b < i; ) {
      File file = arrayOfFile[b];
      if (file.getName().endsWith(".fsm"))
        executeDeter(file); 
      b++;
    } 
  }
  
  private void executeDeter(File file) throws Exception {
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    System.out.println(String.valueOf(file.getName()) + " : " + fsm.checkDeterminism());
  }
  
  @Test
  public void reduced() throws Exception {
    File dir = new File("./test/fsm_number_deter_red");
    byte b;
    int i;
    File[] arrayOfFile;
    for (i = (arrayOfFile = dir.listFiles()).length, b = 0; b < i; ) {
      File file = arrayOfFile[b];
      if (file.getName().endsWith(".fsm"))
        executeReduced(file); 
      b++;
    } 
  }
  
  private void executeReduced(File file) throws Exception {
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    System.out.println(file.getName());
    fsm.printStats();
  }
  
  @Test
  public void kissFormat() throws Exception {
    File file = new File("./test/fsm_number_deter/ex1.kiss2.fsm");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printNumbersKiss();
  }
  
  @Test
  public void complete() throws Exception {
    File dir = new File("./test/fsm_number_deter");
    byte b;
    int i;
    File[] arrayOfFile;
    for (i = (arrayOfFile = dir.listFiles()).length, b = 0; b < i; ) {
      File file = arrayOfFile[b];
      if (file.getName().endsWith(".fsm"))
        executeComp(file); 
      b++;
    } 
  }
  
  private void executeComp(File file) throws Exception {
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    System.out.println(String.valueOf(file.getName()) + " : " + fsm.isComplete());
  }
  
  @Test
  public void testGen() throws Exception {
    File file = new File("./test/fsm_number_deter_red/dk512.kiss2.fsm");
    FsmModelReader reader = new FsmModelReader(file, true);
    FiniteStateMachine fsm = reader.getFsm();
    fsm.printStats();
    PCompleteTestGenerator gen = new PCompleteTestGenerator(fsm);
    PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
    gen.generate();
    for (String s : gen.getFinalTestSet())
      System.out.println(s); 
  }
  
  @Test
  public void testFSMNumber() throws Exception {
    File file = new File("./test/fsm_number_deter_red/dk16.kiss2.fsm");
    FsmModelReader reader = new FsmModelReader(file, false);
    FiniteStateMachine fsm = reader.getFsm();
    System.out.println(fsm.isComplete());
    System.out.println(fsm.checkDeterminism());
    fsm.printStats();
  }
}
