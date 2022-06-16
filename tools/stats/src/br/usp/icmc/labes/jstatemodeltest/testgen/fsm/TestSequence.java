package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class TestSequence {
  public static String EPSILON = "EPSILON";
  
  public static int lenght(String sequence) {
    if (sequence.equals(EPSILON))
      return 0; 
    StringTokenizer st = new StringTokenizer(sequence, ",");
    return st.countTokens();
  }
  
  public static boolean isPrefixOf(String pref, String sequence) {
    if (pref.equals(EPSILON))
      return true; 
    if (sequence.equals(EPSILON))
      return false; 
    String[] prefs = pref.split(",");
    String[] sequences = sequence.split(",");
    if (prefs.length > sequences.length)
      return false; 
    return sequence.startsWith(pref);
  }
  
  public static boolean isProperPrefixOf(String pref, String sequence) {
    if (!pref.equals(sequence) && isPrefixOf(pref, sequence))
      return true; 
    return false;
  }
  
  public static String getSuffixFrom(String seq, String pref) {
    if (pref.equals(EPSILON))
      return seq; 
    if (seq.equals(pref))
      return EPSILON; 
    String[] prefs = pref.split(",");
    String[] sequences = seq.split(",");
    String ret = EPSILON;
    for (int i = prefs.length; i < sequences.length; i++)
      ret = concat(ret, sequences[i]); 
    return ret;
  }
  
  public static String getPrefixFrom(String seq, String sufix) {
    if (sufix.equals(EPSILON))
      return seq; 
    if (seq.equals(sufix))
      return EPSILON; 
    String[] sufixes = sufix.split(",");
    String[] sequences = seq.split(",");
    String ret = EPSILON;
    for (int i = 0; i < sequences.length - sufixes.length; i++)
      ret = concat(ret, sequences[i]); 
    return ret;
  }
  
  public static String concat(String a, String b) {
    if (a.equals(EPSILON))
      return b; 
    if (b.equals(EPSILON))
      return a; 
    return String.valueOf(a) + "," + b;
  }
  
  public static boolean isSufixOf(String sufix, String sequence) {
    if (sufix.equals(EPSILON))
      return true; 
    if (sequence.equals(EPSILON))
      return false; 
    String[] sufixes = sufix.split(",");
    String[] sequences = sequence.split(",");
    if (sufixes.length > sequences.length)
      return false; 
    for (int i = sequences.length - 1, j = sufixes.length - 1; j >= 0; i--, j--) {
      if (!sequences[i].equals(sufixes[j]))
        return false; 
    } 
    return true;
  }
  
  public static boolean isProperSufixOf(String sufix, String sequence) {
    if (isSufixOf(sufix, sequence) && !sufix.equals(sequence))
      return true; 
    return false;
  }
  
  public static ArrayList<String> getAllPrefixesFrom(String seq) {
    ArrayList<String> ret = new ArrayList<String>();
    if (seq.equals(EPSILON)) {
      ret.add(EPSILON);
    } else {
      ret.add(EPSILON);
      String[] symbols = seq.split(",");
      String pref = symbols[0];
      ret.add(pref);
      for (int i = 1; i < symbols.length; i++) {
        pref = String.valueOf(pref) + "," + symbols[i];
        ret.add(pref);
      } 
    } 
    return ret;
  }
  
  public static ArrayList<String> getAllSuffixesFrom(String seq) {
    ArrayList<String> ret = new ArrayList<String>();
    if (seq.equals("EPSILON")) {
      ret.add("EPSILON");
    } else {
      ret.add("EPSILON");
      String[] symbols = seq.split(",");
      String pref = symbols[symbols.length - 1];
      ret.add(pref);
      for (int i = symbols.length - 2; i >= 0; i--) {
        pref = String.valueOf(symbols[i]) + "," + pref;
        ret.add(pref);
      } 
    } 
    return ret;
  }
  
  public static ArrayList<String> getCommonSuffixesFrom(String left, String right) {
    ArrayList<String> ret = new ArrayList<String>();
    if (left.equals("EPSILON") || right.equals("EPSILON"))
      return ret; 
    for (String seq1 : getAllSuffixesFrom(left)) {
      for (String seq2 : getAllSuffixesFrom(right)) {
        if (seq1.equals(seq2))
          ret.add(seq1); 
      } 
    } 
    return ret;
  }
  
  public static ArrayList<String> getNoPrefixes(ArrayList<String> finalTestSet) {
    ArrayList<String> noPrefs = new ArrayList<String>();
    for (String test : finalTestSet) {
      boolean isPref = false;
      for (String s : finalTestSet) {
        if (isProperPrefixOf(test, s)) {
          isPref = true;
          break;
        } 
      } 
      if (!isPref)
        noPrefs.add(test); 
    } 
    return noPrefs;
  }
  
  public static ArrayList<String> getNoPrefixesV2(ArrayList<String> testSet) {
    boolean remove = false;
    for (String test : testSet) {
      for (String s : testSet) {
        if (isProperPrefixOf(test, s)) {
          testSet.remove(test);
          remove = true;
          break;
        } 
      } 
      if (remove)
        break; 
    } 
    if (remove)
      return getNoPrefixesV2(testSet); 
    return testSet;
  }
  
  public static String getLongestSequence(ArrayList<String> testSet) {
    int size = lenght(testSet.get(0));
    String ret = testSet.get(0);
    for (String curr : testSet) {
      if (lenght(curr) > size) {
        size = lenght(curr);
        ret = curr;
      } 
    } 
    return ret;
  }
  
  public static String getPartialTestCase(String testCase, int exec) {
    String ret = EPSILON;
    String[] t = testCase.split(",");
    for (int i = 0; i < exec; i++)
      ret = concat(ret, t[i]); 
    return ret;
  }
}
