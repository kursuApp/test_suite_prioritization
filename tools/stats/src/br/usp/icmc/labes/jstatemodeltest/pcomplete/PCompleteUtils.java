package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import java.util.ArrayList;
import java.util.HashMap;

public class PCompleteUtils {
  FiniteStateMachine fsm;
  
  public PCompleteUtils(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public ArrayList<Pair> getIdentityRelation(ArrayList<String> T) {
    ArrayList<Pair> ret = new ArrayList<Pair>();
    for (String test : T)
      ret.add(new Pair(test, test)); 
    return ret;
  }
  
  public ArrayList<Pair> getTSeparatedTestPairs(ArrayList<String> T) {
    ArrayList<Pair> ret = new ArrayList<Pair>();
    for (String alpha : T) {
      for (String beta : T) {
        if (!alpha.equals(beta)) {
          ArrayList<String> alpha_ext = getExtensionsFrom(T, alpha);
          ArrayList<String> beta_ext = getExtensionsFrom(T, beta);
          for (String gamma : getCommonSufix(alpha, beta, alpha_ext, beta_ext)) {
            State sa = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), alpha);
            State sb = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), beta);
            if (this.fsm.separe(gamma, sa, sb)) {
              Pair.add(ret, new Pair(alpha, beta));
              break;
            } 
          } 
        } 
      } 
    } 
    return ret;
  }
  
  public ArrayList<String> getExtensionsFrom(ArrayList<String> T, String alpha) {
    ArrayList<String> ret = new ArrayList<String>();
    for (String seq : T) {
      if (TestSequence.isProperPrefixOf(alpha, seq))
        ret.add(seq); 
    } 
    return ret;
  }
  
  public ArrayList<String> getCommonSufix(String alpha, String beta, ArrayList<String> alphaExt, ArrayList<String> betaExt) {
    ArrayList<String> ret = new ArrayList<String>();
    for (String seq1 : alphaExt) {
      String gamma = TestSequence.getSuffixFrom(seq1, alpha);
      for (String seq2 : betaExt) {
        String gamma2 = TestSequence.getSuffixFrom(seq2, beta);
        if (gamma.equals(gamma2))
          ret.add(gamma); 
      } 
    } 
    return ret;
  }
  
  public boolean isM_Divergent(ArrayList<String> K_l) {
    for (int i = 0; i < K_l.size() - 1; i++) {
      for (int j = i + 1; j < K_l.size(); j++) {
        String seqi = K_l.get(i);
        String seqj = K_l.get(j);
        if (i != j)
          if (!isDivergent(seqi, seqj))
            return false;  
      } 
    } 
    return true;
  }
  
  public boolean isDivergent(String seqi, String seqj) {
    State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), seqi);
    State sj = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), seqj);
    if (si != sj)
      return true; 
    return false;
  }
  
  public void updateC(ArrayList<String> T, ArrayList<Pair> C) {
    for (String test : T) {
      Pair p = new Pair(test, test);
      if (!Pair.in(p, C))
        C.add(p); 
    } 
  }
  
  public void updateD(ArrayList<Pair> D, ArrayList<String> T, String lastAlpha, String alphax, Ruler ruler) {
    String xs = TestSequence.getSuffixFrom(alphax, lastAlpha);
    for (String x : TestSequence.getAllPrefixesFrom(xs)) {
      String alpha = TestSequence.concat(lastAlpha, x);
      for (String test : T) {
        if (!alpha.equals(test)) {
          ArrayList<String> alpha_ext = getExtensionsFrom(T, alpha);
          ArrayList<String> beta_ext = getExtensionsFrom(T, test);
          for (String gamma : getCommonSufix(alpha, test, alpha_ext, beta_ext)) {
            State sa = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), alpha);
            State sb = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), test);
            if (this.fsm.separe(gamma, sa, sb)) {
              Pair p = new Pair(alpha, test);
              if (Pair.add(D, p))
                ruler.applyRules(p, Ruler.AddedTo.D); 
              break;
            } 
          } 
        } 
      } 
    } 
  }
  
  public void updateD_old(ArrayList<Pair> D, ArrayList<String> T, String alphax, Ruler ruler) {
    for (String alpha : TestSequence.getAllPrefixesFrom(alphax)) {
      for (String test : T) {
        if (!alpha.equals(test)) {
          ArrayList<String> alpha_ext = getExtensionsFrom(T, alpha);
          ArrayList<String> beta_ext = getExtensionsFrom(T, test);
          for (String gamma : getCommonSufix(alpha, test, alpha_ext, beta_ext)) {
            State sa = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), alpha);
            State sb = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), test);
            if (this.fsm.separe(gamma, sa, sb)) {
              Pair p = new Pair(alpha, test);
              if (Pair.add(D, p))
                ruler.applyRules(p, Ruler.AddedTo.D); 
              break;
            } 
          } 
        } 
      } 
    } 
  }
  
  public String findPathBetween(State origin, State target) {
    HashMap<Transition, String> visited = new HashMap<Transition, String>();
    return reachState(origin, target, TestSequence.EPSILON, visited);
  }
  
  private String reachState(State origin, State target, String sequence, HashMap<Transition, String> visited) {
    if (origin == target)
      return sequence; 
    for (Transition t : origin.getOut()) {
      if (visited.get(t) == null) {
        visited.put(t, "ok");
        String seq = reachState(t.getOut(), target, TestSequence.concat(sequence, t.getInput()), visited);
        if (seq != null)
          return seq; 
      } 
    } 
    return null;
  }
  
  public ArrayList<String> findCunionK(ArrayList<Pair> C, ArrayList<String> K) {
    ArrayList<String> ret = new ArrayList<String>();
    for (Pair pair : C) {
      String alpha = pair.getLeft();
      String beta = pair.getRight();
      if (K.contains(alpha) && !ret.contains(beta))
        ret.add(beta); 
      alpha = pair.getRight();
      beta = pair.getLeft();
      if (K.contains(alpha) && !ret.contains(beta))
        ret.add(beta); 
    } 
    return ret;
  }
  
  public Pair findFiChi(ArrayList<String> T, ArrayList<String> CunionK, ArrayList<String> K, ArrayList<Pair> D) {
    for (String fi : TestSet.minus(T, CunionK)) {
      for (String chi : K) {
        boolean in = true;
        for (String upsilon : TestSet.minus(K, chi)) {
          if (!Pair.in(new Pair(fi, upsilon), D)) {
            in = false;
            break;
          } 
        } 
        if (in && isConvergent(fi, chi))
          return new Pair(fi, chi); 
      } 
    } 
    return null;
  }
  
  public boolean isConvergent(String seqi, String seqj) {
    State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), seqi);
    State sj = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), seqj);
    if (si == sj)
      return true; 
    return false;
  }
  
  public ArrayList<State> getInStates(ArrayList<Transition> transitions) {
    ArrayList<State> ret = new ArrayList<State>();
    for (Transition t : transitions) {
      if (!ret.contains(t.getIn()))
        ret.add(t.getIn()); 
    } 
    return ret;
  }
  
  public String findPathBetweenUsingTransitions(State origin, State target, ArrayList<Transition> transitions, ArrayList<String> T) {
    if (origin == target)
      return TestSequence.EPSILON; 
    ArrayList<String> queue = new ArrayList<String>();
    queue.add(TestSequence.EPSILON);
    while (queue.size() > 0) {
      String sequence = queue.remove(0);
      if (TestSequence.lenght(sequence) > this.fsm.getNumberOfStates())
        return null; 
      State reached = this.fsm.nextStateWithSequence(origin, sequence, transitions);
      if (reached != null && reached == target)
        return sequence; 
      for (String x : this.fsm.getInputAlphabet())
        queue.add(TestSequence.concat(sequence, x)); 
    } 
    return null;
  }
  
  public String findPathBetweenUsingTransitions2(State origin, State target, ArrayList<Transition> transitions) {
    HashMap<Transition, String> visited = new HashMap<Transition, String>();
    return reachStateWithTransitions(origin, target, TestSequence.EPSILON, visited, transitions);
  }
  
  private String reachStateWithTransitions(State origin, State target, String sequence, HashMap<Transition, String> visited, ArrayList<Transition> transitions) {
    if (origin == target)
      return sequence; 
    for (Transition t : origin.getOut()) {
      if (transitions.contains(t) && visited.get(t) == null) {
        visited.put(t, "ok");
        String seq = reachState(t.getOut(), target, TestSequence.concat(sequence, t.getInput()), visited);
        if (seq != null)
          return seq; 
      } 
    } 
    return null;
  }
  
  public String findChi(ArrayList<String> K, String fi) {
    for (String chi : K) {
      if (isConvergent(fi, chi))
        return chi; 
    } 
    return null;
  }
  
  public HashMap<State, String> getPrefDS(String DS) {
    HashMap<State, String> map = new HashMap<State, String>();
    for (State si : this.fsm.getStates()) {
      for (String seq : TestSequence.getAllPrefixesFrom(DS)) {
        if (this.fsm.isUIO(si, seq)) {
          map.put(si, seq);
          break;
        } 
      } 
    } 
    return map;
  }
  
  public String findShortestPathBetween(State si, State sj) {
    ArrayList<String> queue = new ArrayList<String>();
    queue.add(TestSequence.EPSILON);
    while (queue.size() > 0) {
      String curr = queue.remove(0);
      if (this.fsm.isDefinedSeq(curr, si) && this.fsm.isDefinedSeq(curr, sj) && this.fsm.nextStateWithSequence(si, curr) == sj)
        return curr; 
      for (String x : this.fsm.getInputAlphabet())
        queue.add(TestSequence.concat(curr, x)); 
    } 
    return null;
  }
}
