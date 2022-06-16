package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FsmCoverage;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.CharacterizationSetConstructor;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar.CostTuple;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PCompleteTestGenerator {
  private static Logger logger = Logger.getAnonymousLogger();
  
  private FiniteStateMachine fsm;
  
  private ArrayList<String> initialTestSet;
  
  private ArrayList<String> finalTestSet;
  
  private int p;
  
  private ArrayList<String> W;
  
  private Ruler ruler;
  
  private ArrayList<String> CunionK;
  
  public static Logger getLogger() {
    return logger;
  }
  
  public Ruler getRuler() {
    return this.ruler;
  }
  
  public PCompleteTestGenerator(FiniteStateMachine fsm) {
    logger.setLevel(Level.OFF);
    this.fsm = fsm;
    this.initialTestSet = new ArrayList<String>();
    this.initialTestSet.add("EPSILON");
    this.p = fsm.getNumberOfStates();
    CharacterizationSetConstructor constructor = new CharacterizationSetConstructor(fsm);
    this.W = constructor.getWset();
  }
  
  public FiniteStateMachine getFsm() {
    return this.fsm;
  }
  
  public void setRuler(Ruler ruler) {
    this.ruler = ruler;
  }
  
  public void setInitialTestSet(ArrayList<String> initialTestSet) {
    this.initialTestSet = initialTestSet;
  }
  
  public void setP(int p) {
    this.p = p;
  }
  
  public ArrayList<String> getFinalTestSet() {
    return TestSequence.getNoPrefixes(this.finalTestSet);
  }
  
  public void generate() {
    logger.info("W: " + this.W);
    logger.info("p: " + this.p);
    int n = this.fsm.getStates().size();
    logger.info("Initial T: " + this.initialTestSet);
    ArrayList<String> T = new ArrayList<String>();
    for (String s : this.initialTestSet)
      T.add(s); 
    logger.info("------------------------------");
    logger.info("STEP 1");
    ArrayList<Pair> C = getIdentityRelation(T);
    ArrayList<Pair> temp_D = getTSeparatedTestPairs(T);
    ArrayList<Pair> D = getTSeparatedTestPairs(T);
    this.ruler = new Ruler(C, D, T);
    for (Pair p : temp_D)
      this.ruler.applyRules(p, Ruler.AddedTo.D); 
    logger.info("findK");
    ArrayList<String> K = findK(T, D);
    logger.info("T1: " + T);
    logger.info("C1: " + C);
    logger.info("D1: " + D);
    logger.info("K1: " + K);
    while (K.size() < Math.min(this.p + 1, n))
      step02(T, C, D, K); 
    if (K.size() >= this.p + 1) {
      this.finalTestSet = T;
    } else {
      this.CunionK = findCunionK(C, K);
      logger.info("C union K2: " + this.CunionK);
      FsmCoverage coverage = new FsmCoverage(this.fsm);
      while (!coverage.isInitializedTransitionCoverage(this.CunionK)) {
        Pair p_fichi = findFiChi(T, this.CunionK, K, D);
        if (p_fichi != null) {
          step03(C, D, p_fichi);
        } else if (!this.CunionK.contains("EPSILON")) {
          logger.info("C union K4: " + this.CunionK);
          Pair p_fichi2 = step04("EPSILON", K, D, C, T);
          step03(C, D, p_fichi2);
        } else {
          String fi = step05(this.CunionK, T, C, D);
          Pair p_fichi2 = step04(fi, K, D, C, T);
          step03(C, D, p_fichi2);
        } 
        this.CunionK = findCunionK(C, K);
      } 
      this.finalTestSet = T;
    } 
  }
  
  private String step05(ArrayList<String> CunionK, ArrayList<String> T, ArrayList<Pair> C, ArrayList<Pair> D) {
    logger.info("------------------------------");
    logger.info("STEP 5");
    FsmCoverage coverage = new FsmCoverage(this.fsm);
    logger.info("CunionK:  " + CunionK);
    logger.info(":  " + coverage.getNonCoveredtransitions(CunionK));
    Transition transition = selectTransition(CunionK, coverage, T);
    State s = transition.getIn();
    for (String alpha : CunionK) {
      if (this.fsm.nextStateWithSequence(this.fsm.getInitialState(), alpha) == s) {
        logger.info("selected transition: " + transition);
        logger.info("alpha: " + alpha);
        String alphax = TestSequence.concat(alpha, transition.getInput());
        TestSet.addAllPrefsOf(T, alphax);
        updateC(T, C);
        updateD(D, T, alphax);
        logger.info("T5: " + T);
        logger.info("C5: " + C);
        logger.info("D5: " + D);
        logger.info("alpha = " + alpha + "; x = " + transition.getInput());
        return alphax;
      } 
    } 
    return null;
  }
  
  private Transition selectTransition(ArrayList<String> CunionK, FsmCoverage coverage, ArrayList<String> T) {
    Transition transition = coverage.getNonCoveredtransitions(CunionK).get(0);
    return transition;
  }
  
  public void updateD(ArrayList<Pair> D, ArrayList<String> T, String alphax) {
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
                this.ruler.applyRules(p, Ruler.AddedTo.D); 
              break;
            } 
          } 
        } 
      } 
    } 
  }
  
  public Pair step04(String fi, ArrayList<String> K, ArrayList<Pair> D, ArrayList<Pair> C, ArrayList<String> T) {
    logger.info("------------------------------");
    logger.info("STEP 4");
    String chi = findChi(K, fi);
    logger.info("ST04 - fi: " + fi);
    logger.info("ST04 - chi: " + chi);
    for (String upsilon : TestSet.minus(K, chi)) {
      if (!Pair.in(new Pair(upsilon, fi), D)) {
        ArrayList<String> Cupsilon = Pair.getPartition(upsilon, C);
        ArrayList<String> Cfi = Pair.getPartition(fi, C);
        logger.info("Cupsilon: " + Cupsilon);
        logger.info("Cfi:     " + Cfi);
        String alpha = Cupsilon.get(0);
        String beta = Cfi.get(0);
        CostTuple tuple = selectGamma(alpha, beta, T, C);
        String gamma = tuple.getGamma();
        alpha = tuple.getAlpha();
        beta = tuple.getBeta();
        logger.info("alpha: " + alpha);
        logger.info("beta: " + beta);
        logger.info("gamma: " + gamma);
        if (gamma != null) {
          String seq1 = TestSequence.concat(alpha, gamma);
          String seq2 = TestSequence.concat(beta, gamma);
          TestSet.addAllPrefsOf(T, seq1);
          TestSet.addAllPrefsOf(T, seq2);
          updateC(T, C);
          Pair p_alphabeta = new Pair(alpha, beta);
          if (Pair.add(D, p_alphabeta))
            this.ruler.applyRules(p_alphabeta, Ruler.AddedTo.D); 
        } 
      } 
    } 
    logger.info("T4: " + T);
    logger.info("C4: " + C);
    logger.info("D4: " + D);
    logger.info("K4: " + K);
    logger.info("fi = " + fi + "; chi = " + chi);
    return new Pair(fi, chi);
  }
  
  public String findChi(ArrayList<String> K, String fi) {
    for (String chi : K) {
      if (isConvergent(fi, chi))
        return chi; 
    } 
    return null;
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
  
  public void step03(ArrayList<Pair> C, ArrayList<Pair> D, Pair pair) {
    logger.info("------------------------------");
    logger.info("STEP 3");
    logger.info("fi = " + pair.getLeft() + "; chi = " + pair.getRight());
    if (Pair.add(C, pair))
      this.ruler.applyRules(pair, Ruler.AddedTo.C); 
    logger.info("C3: " + C);
    logger.info("D3: " + D);
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
  
  public void step02(ArrayList<String> T, ArrayList<Pair> C, ArrayList<Pair> D, ArrayList<String> K) {
    logger.info("------------------------------");
    logger.info("STEP 2");
    String alpha = selectAlpha(K);
    logger.info("ST02 - alpha: " + alpha);
    for (String beta : K) {
      if (!Pair.in(new Pair(alpha, beta), D)) {
        CostTuple tuple = selectGamma(alpha, beta, T, C);
        String gamma = tuple.getGamma();
        alpha = tuple.getAlpha();
        beta = tuple.getBeta();
        logger.info("ST02 - gamma: " + gamma);
        String seq1 = TestSequence.concat(alpha, gamma);
        String seq2 = TestSequence.concat(beta, gamma);
        TestSet.addAllPrefsOf(T, seq1);
        TestSet.addAllPrefsOf(T, seq2);
        updateC(T, C);
        Pair p_alphabeta = new Pair(alpha, beta);
        if (Pair.add(D, p_alphabeta))
          this.ruler.applyRules(p_alphabeta, Ruler.AddedTo.D); 
      } 
    } 
    K.add(alpha);
    logger.info("T2: " + T);
    logger.info("C2: " + C);
    logger.info("D2: " + D);
    logger.info("K2:" + K);
  }
  
  private void updateC(ArrayList<String> T, ArrayList<Pair> C) {
    for (String test : T) {
      Pair p = new Pair(test, test);
      if (!Pair.in(p, C))
        C.add(p); 
    } 
  }
  
  private ArrayList<Pair> getIdentityRelation(ArrayList<String> T) {
    ArrayList<Pair> ret = new ArrayList<Pair>();
    for (String test : T)
      ret.add(new Pair(test, test)); 
    return ret;
  }
  
  public CostTuple selectGamma(String alpha, String beta, ArrayList<String> T, ArrayList<Pair> C) {
    CharacterizationSetConstructor csc = new CharacterizationSetConstructor();
    csc.setFsm(this.fsm);
    return csc.getDistinguishSequence(alpha, beta, T, C);
  }
  
  public String selectAlpha(ArrayList<String> K) {
    if (!K.contains(TestSequence.EPSILON)) {
      ArrayList<String> K_l = new ArrayList<String>(K);
      K_l.add(TestSequence.EPSILON);
      if (isM_Divergent(K_l))
        return TestSequence.EPSILON; 
    } 
    for (int i = 1; i <= 1000; i++) {
      for (String seq : getInputSeqWithLength(i)) {
        if (this.fsm.isDefinedSeq(seq, this.fsm.getInitialState()) && !K.contains(seq)) {
          ArrayList<String> K_l = new ArrayList<String>(K);
          K_l.add(seq);
          if (isM_Divergent(K_l))
            return seq; 
        } 
      } 
    } 
    return null;
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
  
  public boolean isConvergent(String seqi, String seqj) {
    State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), seqi);
    State sj = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), seqj);
    if (si == sj)
      return true; 
    return false;
  }
  
  public ArrayList<String> findK(ArrayList<String> T, ArrayList<Pair> D) {
    DivergenceGraph dg = new DivergenceGraph(T, D, this.fsm.getNumberOfStates());
    ArrayList<String> K = dg.getK();
    return K;
  }
  
  public ArrayList<Pair> getTSeparatedTestPairs(ArrayList<String> T) {
    ArrayList<Pair> ret = new ArrayList<Pair>();
    for (String alpha : T) {
      for (String beta : T) {
        if (!alpha.equals(beta)) {
          logger.info(String.valueOf(alpha) + " - " + beta);
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
  
  public ArrayList<String> getExtensionsFrom(ArrayList<String> T, String alpha) {
    ArrayList<String> ret = new ArrayList<String>();
    for (String seq : T) {
      if (TestSequence.isProperPrefixOf(alpha, seq))
        ret.add(seq); 
    } 
    return ret;
  }
  
  public ArrayList<String> getInputSeqWithLength(int k) {
    ArrayList<String> ret = new ArrayList<String>();
    HashSet<String> Li = this.fsm.getInputAlphabet();
    for (String in : Li)
      ret.add(in); 
    for (int i = 2; i <= k; i++) {
      ArrayList<String> temp = new ArrayList<String>();
      for (String seq : ret) {
        for (String in : Li)
          temp.add(String.valueOf(seq) + "," + in); 
      } 
      ret = temp;
    } 
    return ret;
  }
}
