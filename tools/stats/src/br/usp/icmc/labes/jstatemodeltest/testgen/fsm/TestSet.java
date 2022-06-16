package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import java.util.ArrayList;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public class TestSet {
  public static void addNewTest(ArrayList<String> T, String newTest) {
    if (!T.contains(newTest))
      T.add(newTest); 
  }
  
  public static void addAllPrefsOf(ArrayList<String> T, String newTest) {
    for (String test : TestSequence.getAllPrefixesFrom(newTest))
      addNewTest(T, test); 
  }
  
  public static ArrayList<String> minus(ArrayList<String> set1, ArrayList<String> set2) {
    ArrayList<String> ret = new ArrayList<String>();
    for (String s1 : set1) {
      if (!set2.contains(s1))
        ret.add(s1); 
    } 
    return ret;
  }
  
  public static ArrayList<String> minus(ArrayList<String> set1, String seq) {
    ArrayList<String> ret = new ArrayList<String>();
    for (String s1 : set1) {
      if (!seq.equals(s1))
        ret.add(s1); 
    } 
    return ret;
  }
  
  public static int size(ArrayList<String> set1) {
    int tam = set1.size();
    for (String test : set1)
      tam += TestSequence.lenght(test); 
    return tam;
  }
  
  public static String getLongestSequence(ArrayList<String> T) {
    String longest = T.get(0);
    int size = TestSequence.lenght(longest);
    for (String s : T) {
      if (TestSequence.lenght(s) > size) {
        longest = s;
        size = TestSequence.lenght(s);
      } 
    } 
    return longest;
  }
  
  public static String getShortestSequence(ArrayList<String> T) {
    String shortest = T.get(0);
    int size = TestSequence.lenght(shortest);
    for (String s : T) {
      if (TestSequence.lenght(s) < size) {
        shortest = s;
        size = TestSequence.lenght(s);
      } 
    } 
    return shortest;
  }
  
  public static int calcCost(String alpha, String x, ArrayList<String> T, int sumalpha) {
    String alphax = TestSequence.concat(alpha, x);
    if (!T.contains(alphax)) {
      if (T.contains(alpha)) {
        if (isMaximal(T, alpha))
          return 1; 
        return TestSequence.lenght(alpha) + 1 + 1;
      } 
      return sumalpha + 1;
    } 
    return 0;
  }
  
  private static boolean isMaximal(ArrayList<String> T, String alpha) {
    for (String test : T) {
      if (TestSequence.isProperPrefixOf(alpha, test))
        return false; 
    } 
    return true;
  }
  
  public static String printLength(ArrayList<String> seqs) {
    String print = "";
    for (String seq : seqs)
      print = String.valueOf(print) + TestSequence.lenght(seq) + ","; 
    return print;
  }
  
  public static String printLength(String seq) {
    return String.valueOf(TestSequence.lenght(seq));
  }
  
  public static void printStats(ArrayList<String> testsuite) {
    DescriptiveStatistics stats = new DescriptiveStatistics();
    for (String testcase : testsuite)
      stats.addValue(TestSequence.lenght(testcase)); 
    System.out.println("STATS");
    System.out.println("Number of test cases: " + testsuite.size());
    System.out.println("Test suite length (with resets): " + size(testsuite));
    System.out.println("Test suite length (no resets): " + (size(testsuite) - testsuite.size()));
    System.out.println("Shortest test case: " + stats.getMin());
    System.out.println("Longest test case: " + stats.getMax());
    System.out.println("Average test case length: " + stats.getMean() + " (" + stats.getStandardDeviation() + ", " + stats.getVariance() + ")");
  }
  
  public static void printStatsInLine(ArrayList<String> testsuite) {
    DescriptiveStatistics stats = new DescriptiveStatistics();
    for (String testcase : testsuite)
      stats.addValue(TestSequence.lenght(testcase)); 
    StringBuilder out = new StringBuilder();
    out.append(testsuite.size());
    out.append(",");
    out.append(size(testsuite));
    out.append(",");
    out.append(size(testsuite) - testsuite.size());
    out.append(",");
    out.append(stats.getMin());
    out.append(",");
    out.append(stats.getMax());
    out.append(",");
    out.append(stats.getMean());
    out.append(",");
    out.append(stats.getStandardDeviation());
    out.append(",");
    out.append(stats.getVariance());
    System.out.println(out);
  }
  
  public static boolean isPrefix(String newsequence, ArrayList<String> T) {
    for (String test : T) {
      if (TestSequence.isPrefixOf(newsequence, test))
        return true; 
    } 
    return false;
  }
}
