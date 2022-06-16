package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.common.TestSuiteReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.WMethod;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import org.junit.Assert;
import org.junit.Test;

public class PCompleteTest {
  @Test
  public void test01() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 01");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(3);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.generate();
      System.out.println(generator.getFinalTestSet());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test02() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 02");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(2);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add("EPSILON");
      initialTestSet.add("a");
      initialTestSet.add("a,a");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      Assert.assertEquals(3L, generator.getFinalTestSet().size());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test03() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 03");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(3);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      initialTestSet.add("a");
      initialTestSet.add("a,a");
      initialTestSet.add("a,a,a");
      initialTestSet.add("b");
      initialTestSet.add("b,a");
      initialTestSet.add("b,a,a");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      Assert.assertEquals(5L, generator.getFinalTestSet().size());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test04() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 04");
    try {
      File file = new File("./test/dorofeeva.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(4L, fsm.getStates().size());
      Assert.assertEquals(8L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(4);
      ArrayList<String> T = new ArrayList<String>();
      TestSet.addAllPrefsOf(T, "x,x,y");
      TestSet.addAllPrefsOf(T, "x,y,y,y");
      TestSet.addAllPrefsOf(T, "y,x,y");
      TestSet.addAllPrefsOf(T, "y,y,x,x");
      TestSet.addAllPrefsOf(T, "y,y,y,y,y,y");
      generator.setInitialTestSet(T);
      generator.generate();
      System.out.println("P: " + TestSet.size(generator.getFinalTestSet()));
      Assert.assertEquals(25L, TestSet.size(generator.getFinalTestSet()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test05() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 05");
    try {
      File file = new File("./test/chen.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(5L, fsm.getStates().size());
      Assert.assertEquals(10L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(5);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add("EPSILON");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println(wmethod.getSequencesWithoutPrefixes());
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test06() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 06");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(3);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add("EPSILON");
      initialTestSet.add("a");
      initialTestSet.add("a,a");
      initialTestSet.add("a,a,a");
      initialTestSet.add("b");
      initialTestSet.add("b,a");
      initialTestSet.add("b,a,a");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      System.out.println(generator.getFinalTestSet());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test07() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 07");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(3);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add("EPSILON");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println(wmethod.getSequencesWithoutPrefixes());
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test08() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 08");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(fsm.getNumberOfStates());
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add("EPSILON");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
      file = new File("./test/dorofeeva.txt");
      reader = new FsmModelReader(file);
      fsm = reader.getFsm();
      generator = new PCompleteTestGenerator(fsm);
      generator.setP(fsm.getNumberOfStates());
      initialTestSet = new ArrayList<String>();
      initialTestSet.add("EPSILON");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
      file = new File("./test/chen.txt");
      reader = new FsmModelReader(file);
      fsm = reader.getFsm();
      generator = new PCompleteTestGenerator(fsm);
      generator.setP(fsm.getNumberOfStates());
      initialTestSet = new ArrayList<String>();
      initialTestSet.add("EPSILON");
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test09() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 09");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(3);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      initialTestSet.add("a");
      initialTestSet.add("a,a");
      initialTestSet.add("a,a,a");
      initialTestSet.add("a,b");
      initialTestSet.add("a,b,b");
      initialTestSet.add("b");
      initialTestSet.add("b,a");
      initialTestSet.add("b,a,b");
      initialTestSet.add("b,a,b,a");
      initialTestSet.add("b,b");
      initialTestSet.add("b,b,a");
      initialTestSet.add("b,b,a,b");
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.setInitialTestSet(initialTestSet);
      generator.generate();
      Assert.assertEquals(4L, generator.getFinalTestSet().size());
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test10() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 10");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      initialTestSet.add("b");
      initialTestSet.add("b,b");
      initialTestSet.add("b,b,a");
      initialTestSet.add("b,b,a,b");
      initialTestSet.add("b,b,a,b,a");
      initialTestSet.add("b,b,a,b,a,a");
      generator.setInitialTestSet(initialTestSet);
      generator.setP(3);
      generator.generate();
      System.out.println(generator.getFinalTestSet());
      System.out.println("initial: 7");
      System.out.println("P: " + TestSet.size(generator.getFinalTestSet()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test11() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 11");
    try {
      File file = new File("./test/ipate.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(5L, fsm.getStates().size());
      Assert.assertEquals(10L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      generator.setInitialTestSet(initialTestSet);
      generator.setP(5);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test12() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 12");
    try {
      File file = new File("./test/dk27.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(7L, fsm.getStates().size());
      Assert.assertEquals(14L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(3L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      generator.setInitialTestSet(initialTestSet);
      generator.setP(7);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test13() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 13");
    try {
      File file = new File("./test/dk15.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(4L, fsm.getStates().size());
      Assert.assertEquals(32L, fsm.getTransitions().size());
      Assert.assertEquals(8L, fsm.getInputAlphabet().size());
      Assert.assertEquals(11L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      generator.setInitialTestSet(initialTestSet);
      generator.setP(4);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      Assert.assertEquals(236L, TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test14() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 14");
    try {
      File file = new File("./test/bbtas.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(6L, fsm.getStates().size());
      Assert.assertEquals(24L, fsm.getTransitions().size());
      Assert.assertEquals(4L, fsm.getInputAlphabet().size());
      Assert.assertEquals(4L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      generator.setInitialTestSet(initialTestSet);
      generator.setP(6);
      long init = System.currentTimeMillis();
      generator.generate();
      init = System.currentTimeMillis() - init;
      System.out.println("time: " + init);
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test15() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 15");
    try {
      File file = new File("./test/pio.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(4L, fsm.getStates().size());
      Assert.assertEquals(8L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      generator.setInitialTestSet(initialTestSet);
      generator.setP(4);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test16() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 16");
    try {
      File file = new File("./test/pio.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(4L, fsm.getStates().size());
      Assert.assertEquals(8L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      TestSet.addAllPrefsOf(initialTestSet, "a,a,b,a,b,a");
      TestSet.addAllPrefsOf(initialTestSet, "a,b,a,b");
      TestSet.addAllPrefsOf(initialTestSet, "b,a,a,b,b,b,a,b");
      generator.setInitialTestSet(initialTestSet);
      generator.setP(4);
      generator.generate();
      System.out.println("P:" + TestSet.size(generator.getFinalTestSet()));
      WMethod wmethod = new WMethod(fsm);
      System.out.println("W:" + TestSet.size(wmethod.getSequencesWithoutPrefixes()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test17() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 17");
    try {
      File file = new File("./test/test.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      file = new File("./test/testsuite.txt");
      TestSuiteReader treader = new TestSuiteReader(file, false);
      ArrayList<String> initialSet = new ArrayList<String>();
      for (String testseq : treader.getTestSuite())
        TestSet.addAllPrefsOf(initialSet, testseq); 
      generator.setInitialTestSet(initialSet);
      generator.generate();
      System.out.println("P set: ");
      System.out.println(generator.getFinalTestSet());
      System.out.println("Wrong set: ");
      System.out.println("[0,0, 0,1,0, 0,2, 1,0,1, 1,1, 2,0, 2,2,1, 0,1,1,1, 0,1,2,1]");
      Assert.assertTrue(generator.getFinalTestSet().contains("2,1"));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test18() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 18");
    try {
      File file = new File("./test/Simao09CJ-fsm01.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(3L, fsm.getStates().size());
      Assert.assertEquals(6L, fsm.getTransitions().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(3);
      ArrayList<String> initialTestSet = new ArrayList<String>();
      initialTestSet.add(TestSequence.EPSILON);
      generator.setInitialTestSet(initialTestSet);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.generate();
      System.out.println(generator.getFinalTestSet());
      System.out.println(TestSet.size(generator.getFinalTestSet()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test19() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 19");
    try {
      File file = new File("./test/mef_bap.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      file = new File("./test/tc_brickles.txt");
      TestSuiteReader treader = new TestSuiteReader(file, true);
      ArrayList<String> initialSet = new ArrayList<String>();
      for (String testseq : treader.getTestSuite())
        TestSet.addAllPrefsOf(initialSet, testseq); 
      generator.setInitialTestSet(initialSet);
      generator.setInitialTestSet(initialSet);
      generator.generate();
      System.out.println(generator.getFinalTestSet());
      System.out.println(TestSet.size(treader.getTestSuite()));
      System.out.println(TestSet.size(generator.getFinalTestSet()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test20() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 20");
    try {
      File file = new File("./test/mefH.txt");
      FsmModelReader reader = new FsmModelReader(file);
      FiniteStateMachine fsm = reader.getFsm();
      Assert.assertEquals(4L, fsm.getStates().size());
      Assert.assertEquals(2L, fsm.getInputAlphabet().size());
      Assert.assertEquals(2L, fsm.getOutputAlphabet().size());
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      generator.setP(4);
      generator.generate();
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
      System.out.println(TestSet.size(generator.getFinalTestSet()));
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test21() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 21");
    try {
      File file = new File("./test/hierons.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.setInitialTestSet(TestSequence.getAllPrefixesFrom("a,a,b,a,a,b,a,b,a,b,a,a,b,b,b,a,b,a,b,a,a,b,a,a,a,b,b,a,b,a,a,b,a,a,a,b,a,b,a,b,a,a,b,b,a,b"));
      generator.generate();
      System.out.println(TestSet.size(generator.getFinalTestSet()));
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
  
  @Test
  public void test22() {
    System.out.println("-----------------------------------------");
    System.out.println("TEST 22");
    try {
      File file = new File("./test/dorofeeva.txt");
      FsmModelReader reader = new FsmModelReader(file, true);
      FiniteStateMachine fsm = reader.getFsm();
      PCompleteTestGenerator generator = new PCompleteTestGenerator(fsm);
      PCompleteTestGenerator.getLogger().setLevel(Level.ALL);
      generator.setInitialTestSet(TestSequence.getAllPrefixesFrom("y,y,y,y,y,y,y,y,x,y,y,y,y,y,y,x,x,y,y,y,y,x,x,x,y,x,y,x,y,y,y"));
      generator.generate();
      System.out.println(TestSet.size(generator.getFinalTestSet()));
      for (String test : generator.getFinalTestSet())
        System.out.println(test); 
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail();
    } 
  }
}
