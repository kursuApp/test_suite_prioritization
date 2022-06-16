package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SymbolOperator {
  public static Set<String> power(Set<String> symbols, int pot) {
    HashSet<String> ret = new HashSet<String>();
    if (pot == 0) {
      ret.add(TestSequence.EPSILON);
      return ret;
    } 
    if (pot == 1) {
      ret = new HashSet<String>(symbols);
      return ret;
    } 
    ArrayList<String> queue = new ArrayList<String>();
    queue.add(TestSequence.EPSILON);
    while (queue.size() > 0) {
      String current = queue.remove(0);
      if (TestSequence.lenght(current) == pot) {
        ret.add(current);
        continue;
      } 
      for (String x : symbols)
        queue.add(TestSequence.concat(current, x)); 
    } 
    return ret;
  }
  
  public static Collection<String> concatenate(Collection<String> set1, Collection<String> set2) {
    ArrayList<String> ret = new ArrayList<String>();
    for (String s1 : set1) {
      for (String s2 : set2) {
        String newseq = TestSequence.concat(s1, s2);
        if (!ret.contains(newseq))
          ret.add(newseq); 
      } 
    } 
    return ret;
  }
  
  public static ArrayList<String> union(Collection<String> set1, Collection<String> set2) {
    ArrayList<String> ret = new ArrayList<String>(set1);
    for (String s2 : set2) {
      if (!ret.contains(s2))
        ret.add(s2); 
    } 
    return ret;
  }
}
