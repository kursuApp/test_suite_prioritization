package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.cs;

import br.usp.icmc.labes.jstatemodeltest.pcomplete.PCompleteUtils;
import br.usp.icmc.labes.jstatemodeltest.pcomplete.Pair;
import br.usp.icmc.labes.jstatemodeltest.pcomplete.Ruler;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FsmCoverage;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PCheckingSequenceGeneratorAdaptative {
  private static Logger logger = Logger.getAnonymousLogger();
  
  FiniteStateMachine fsm;
  
  String checkingSequence;
  
  private Ruler ruler;
  
  PCompleteUtils utils;
  
  String userSequence = TestSequence.EPSILON;
  
  private String DS = null;
  
  public void setDS(String dS) {
    this.DS = dS;
  }
  
  public String getDS() {
    return this.DS;
  }
  
  public static Logger getLogger() {
    return logger;
  }
  
  public Ruler getRuler() {
    return this.ruler;
  }
  
  public String getUserSequence() {
    return this.userSequence;
  }
  
  public void setUserSequence(String userSequence) {
    this.userSequence = userSequence;
  }
  
  public String getCheckingSequence() {
    return this.checkingSequence;
  }
  
  public PCheckingSequenceGeneratorAdaptative(FiniteStateMachine fsm) {
    logger.setLevel(Level.OFF);
    this.fsm = fsm;
  }
  
  public void generate2() {
    this.utils = new PCompleteUtils(this.fsm);
    AdaptativeDistinguishingSequenceConstructor adsc = new AdaptativeDistinguishingSequenceConstructor(this.fsm);
    HashMap<State, String> DSi = adsc.getAdaptativeDS();
    State s0 = this.fsm.getInitialState();
    logger.info("DSi: " + DSi);
    ArrayList<String> T = new ArrayList<String>();
    TestSet.addAllPrefsOf(T, this.userSequence);
    ArrayList<Pair> C = this.utils.getIdentityRelation(T);
    ArrayList<Pair> D = this.utils.getTSeparatedTestPairs(T);
    ArrayList<Pair> temp_D = new ArrayList<Pair>(D);
    this.ruler = new Ruler(C, D, T);
    for (Pair p : temp_D)
      this.ruler.applyRules(p, Ruler.AddedTo.D); 
    ArrayList<String> K = new ArrayList<String>();
    for (String alpha : T) {
      State si = this.fsm.nextStateWithSequence(s0, alpha);
      String dsi = DSi.get(si);
      if (T.contains(TestSequence.concat(alpha, dsi)) && !this.fsm.nextStateWithSequence(s0, K).contains(si))
        K.add(alpha); 
    } 
    String CS = this.userSequence;
    while (K.size() < this.fsm.getNumberOfStates()) {
      String alpha = selectAlpha(CS, K, DSi, T);
      logger.info("CS: " + CS);
      logger.info("alpha: " + alpha);
      if (alpha != null) {
        String DSitemp = DSi.get(this.fsm.nextStateWithSequence(this.fsm.getInitialState(), alpha));
        if (TestSequence.isProperPrefixOf(CS, TestSequence.concat(alpha, DSitemp))) {
          State si = this.fsm.nextStateWithSequence(s0, alpha);
          CS = TestSequence.concat(alpha, DSi.get(si));
        } 
      } else {
        String kappa = selectKappa(CS, K);
        logger.info("kappa: " + kappa);
        alpha = TestSequence.concat(CS, kappa);
        State si = this.fsm.nextStateWithSequence(s0, alpha);
        CS = TestSequence.concat(alpha, DSi.get(si));
      } 
      TestSet.addAllPrefsOf(T, CS);
      this.utils.updateC(T, C);
      for (String beta : K) {
        Pair p_alphabeta = new Pair(alpha, beta);
        if (Pair.add(D, p_alphabeta))
          this.ruler.applyRules(p_alphabeta, Ruler.AddedTo.D); 
      } 
      K.add(alpha);
    } 
    logger.info("T1.1: " + T);
    logger.info("C1.1: " + C);
    logger.info("D1.1: " + D);
    logger.info("K1.1: " + K);
    logger.info("CS1.1: " + CS);
    logger.info("CS1.1: " + TestSet.printLength(CS));
    ArrayList<String> CunionK = this.utils.findCunionK(C, K);
    logger.info("C union K1.1: " + CunionK);
    logger.info("C union K1.1: " + TestSet.printLength(CunionK));
    CunionK = applyConditionFour(T, C, D, K, CS, CunionK);
    logger.info("C union K1.1: " + CunionK);
    logger.info("C union K1.1: " + TestSet.printLength(CunionK));
    if (!CunionK.contains(CS)) {
      String phi = CS;
      State current = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), CS);
      CS = TestSequence.concat(CS, DSi.get(current));
      TestSet.addAllPrefsOf(T, CS);
      this.fsm.nextStateWithSequence(s0, K);
      String chi = findChi(phi, K);
      for (String alpha : TestSet.minus(K, chi)) {
        Pair pair = new Pair(alpha, phi);
        if (Pair.add(D, pair))
          this.ruler.applyRules(pair, Ruler.AddedTo.D); 
      } 
      this.utils.updateC(T, C);
      this.utils.updateD_old(D, T, CS, this.ruler);
      logger.info(CS);
      CunionK = this.utils.findCunionK(C, K);
      logger.info("CS-B: " + TestSet.printLength(CS));
      logger.info("CS-B: " + CS);
      logger.info("C union K-B: " + TestSet.printLength(CunionK));
      CunionK = applyConditionFour(T, C, D, K, CS, CunionK);
      logger.info("C union K1.1: " + CunionK);
      logger.info("C union K1.1: " + TestSet.printLength(CunionK));
      if (!CunionK.contains(CS))
        CS = yeahyeah(T, C, D, CS, CunionK); 
    } 
    CunionK = applyConditionFour(T, C, D, K, CS, CunionK);
    CunionK = this.utils.findCunionK(C, K);
    logger.info("CS: " + CS);
    logger.info("CS: " + TestSet.printLength(CS));
    logger.info("C union K1.1: " + CunionK);
    logger.info("C union K1.1: " + TestSet.printLength(CunionK));
    FsmCoverage coverage = new FsmCoverage(this.fsm);
    while (!coverage.isTransitionCoverage(CunionK)) {
      Pair p_fichi = this.utils.findFiChi(T, CunionK, K, D);
      if (p_fichi != null) {
        logger.info("Step 03");
        if (Pair.add(C, p_fichi)) {
          logger.info("fi, chi: " + p_fichi);
          this.ruler.applyRules(p_fichi, Ruler.AddedTo.C);
        } 
        logger.info("T3: " + TestSet.printLength(T));
        logger.info("C3: " + C);
        logger.info("D3: " + D);
        logger.info("K3: " + TestSet.printLength(K));
        logger.info("CS3: " + TestSet.printLength(CS));
      } else if (!CunionK.contains(CS)) {
        CS = yeahyeah(T, C, D, CS, CunionK);
      } else {
        logger.info("confirm transitions");
        ArrayList<Transition> UT = coverage.getNonCoveredtransitions(CunionK);
        logger.info("UT: " + UT);
        ReachingData temp_rd = selectTransition(coverage, CunionK, this.fsm.nextStateWithSequence(s0, CS), T);
        String kappa = temp_rd.getSequence();
        String x = temp_rd.getTransition().getInput();
        logger.info("CS in state: " + this.fsm.nextStateWithSequence(s0, CS));
        String talpha = CS;
        CS = TestSequence.concat(CS, kappa);
        TestSet.addAllPrefsOf(T, CS);
        if (!kappa.equals(TestSequence.EPSILON)) {
          logger.info("kappa: " + kappa);
          String[] kappas = kappa.split(",");
          byte b;
          int i;
          String[] arrayOfString1;
          for (i = (arrayOfString1 = kappas).length, b = 0; b < i; ) {
            String k = arrayOfString1[b];
            ArrayList<String> convergentSet = Pair.getPartition(talpha, C);
            convergentSet.remove(talpha);
            for (String gline : convergentSet) {
              String glineKappa = TestSequence.concat(gline, k);
              if (T.contains(glineKappa)) {
                Pair pair = new Pair(glineKappa, TestSequence.concat(talpha, k));
                if (Pair.add(C, pair))
                  this.ruler.applyRules(pair, Ruler.AddedTo.C); 
              } 
            } 
            talpha = TestSequence.concat(talpha, k);
            b++;
          } 
        } 
        logger.info("CS-B: " + TestSet.printLength(CS));
        logger.info("CS-B: " + CS);
        logger.info("C union K-B: " + TestSet.printLength(CunionK));
        CS = TestSequence.concat(CS, x);
        TestSet.addAllPrefsOf(T, CS);
        this.utils.updateC(T, C);
        this.utils.updateD_old(D, T, CS, this.ruler);
        String phi = CS;
        GammaAlphas ga = findGammaAndAlphas(C, D, K, phi, T, DSi);
        String gamma = ga.getGamma();
        logger.info("CS-B: " + TestSet.printLength(CS));
        logger.info("CS-B: " + CS);
        logger.info("C union K-B: " + TestSet.printLength(CunionK));
        logger.info("transition: " + temp_rd.getTransition());
        logger.info("kappa: " + kappa);
        logger.info("x: " + x);
        logger.info("gamma: " + gamma);
        CS = TestSequence.concat(phi, gamma);
        TestSet.addAllPrefsOf(T, CS);
        this.utils.updateC(T, C);
        this.utils.updateD_old(D, T, CS, this.ruler);
        for (String alpha : ga.getAlphas()) {
          Pair p_alphabeta = new Pair(alpha, phi);
          if (Pair.add(D, p_alphabeta))
            this.ruler.applyRules(p_alphabeta, Ruler.AddedTo.D); 
        } 
      } 
      CunionK = this.utils.findCunionK(C, K);
      logger.info("C: " + C);
      logger.info("CS-B: " + TestSet.printLength(CS));
      logger.info("CS-B: " + CS);
      logger.info("C union K-B: " + TestSet.printLength(CunionK));
    } 
    this.checkingSequence = CS;
  }
  
  private String yeahyeah(ArrayList<String> T, ArrayList<Pair> C, ArrayList<Pair> D, String CS, ArrayList<String> CunionK) {
    ArrayList<String> convergentSet = Pair.getPartition(CS, C);
    convergentSet.remove(CS);
    String gamma = null, adsi = null;
    for (String convseq : convergentSet) {
      adsi = convseq;
      logger.info(adsi);
      int l = Integer.MAX_VALUE;
      gamma = TestSequence.EPSILON;
      for (String str : CunionK) {
        if (TestSequence.isProperPrefixOf(convseq, str) && TestSequence.lenght(str) < l) {
          gamma = str;
          l = TestSequence.lenght(gamma);
        } 
      } 
      if (!gamma.equals(TestSequence.EPSILON))
        break; 
    } 
    String gline = TestSequence.getSuffixFrom(gamma, adsi);
    CS = TestSequence.concat(CS, gline);
    TestSet.addAllPrefsOf(T, CS);
    Pair pair = new Pair(gamma, CS);
    if (Pair.add(C, pair))
      this.ruler.applyRules(pair, Ruler.AddedTo.C); 
    this.utils.updateC(T, C);
    this.utils.updateD_old(D, T, CS, this.ruler);
    return CS;
  }
  
  private ArrayList<String> applyConditionFour(ArrayList<String> T, ArrayList<Pair> C, ArrayList<Pair> D, ArrayList<String> K, String CS, ArrayList<String> CunionK) {
    while (true) {
      Pair p_fichi = this.utils.findFiChi(T, CunionK, K, D);
      if (p_fichi != null) {
        logger.info("Step 03");
        if (Pair.add(C, p_fichi)) {
          logger.info("fi, chi: " + p_fichi);
          this.ruler.applyRules(p_fichi, Ruler.AddedTo.C);
        } 
        logger.info("T3: " + T);
        logger.info("C3: " + C);
        logger.info("D3: " + D);
        logger.info("K3: " + K);
        logger.info("CS3: " + CS);
        CunionK = this.utils.findCunionK(C, K);
        continue;
      } 
      break;
    } 
    return CunionK;
  }
  
  private GammaAlphas findGammaAndAlphas(ArrayList<Pair> C, ArrayList<Pair> D, ArrayList<String> K, String phi, ArrayList<String> T, HashMap<State, String> DSi) {
    String chi = findChi(phi, K);
    logger.info("chi: " + chi);
    State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), phi);
    return new GammaAlphas(DSi.get(si), TestSet.minus(K, chi));
  }
  
  private String findGamma(String phi, ArrayList<String> N, ArrayList<Pair> C, ArrayList<String> T) {
    ArrayList<State> statesN = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), N);
    State statePhi = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), phi);
    ArrayList<String> queue = new ArrayList<String>();
    queue.add(TestSequence.EPSILON);
    while (queue.size() != 0) {
      String gamma = queue.remove(0);
      boolean sep = true;
      for (State sj : statesN) {
        if (!this.fsm.separe(gamma, statePhi, sj)) {
          sep = false;
          break;
        } 
      } 
      if (sep && isGammaApplicable(gamma, N, T, C))
        return gamma; 
      for (String x : this.fsm.getInputAlphabet()) {
        String newseq = TestSequence.concat(gamma, x);
        queue.add(newseq);
      } 
    } 
    return null;
  }
  
  private boolean isGammaApplicable(String gamma, ArrayList<String> N, ArrayList<String> T, ArrayList<Pair> C) {
    for (String upsilon : N) {
      State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), upsilon);
      String pref_gamma = gamma;
      for (String tmp_gamma : TestSequence.getAllPrefixesFrom(gamma)) {
        if (this.fsm.isUIO(si, tmp_gamma)) {
          pref_gamma = tmp_gamma;
          break;
        } 
      } 
      boolean ok = false;
      for (String alpha : Pair.getPartition(upsilon, C)) {
        String alphagamma = TestSequence.concat(alpha, pref_gamma);
        if (T.contains(alphagamma)) {
          ok = true;
          break;
        } 
      } 
      if (!ok)
        return false; 
    } 
    return true;
  }
  
  private String findChi(String phi, ArrayList<String> K) {
    for (String chi : K) {
      if (this.fsm.nextStateWithSequence(this.fsm.getInitialState(), chi) == this.fsm.nextStateWithSequence(this.fsm.getInitialState(), phi))
        return chi; 
    } 
    return null;
  }
  
  private ReachingData selectTransition(FsmCoverage coverage, ArrayList<String> CunionK, State currState, ArrayList<String> T) {
    ArrayList<Transition> uncovered = coverage.getNonCoveredtransitions(CunionK);
    ArrayList<Transition> covered = Transition.diff(this.fsm.getTransitions(), uncovered);
    logger.info("uncovered:  " + uncovered);
    logger.info("covered:  " + covered);
    logger.info("current State:  " + currState);
    ArrayList<State> unStates = this.utils.getInStates(uncovered);
    int size = Integer.MAX_VALUE;
    String transferSeq = TestSequence.EPSILON;
    State inState = unStates.get(0);
    for (State state : unStates) {
      String temp_transfer = this.utils.findPathBetweenUsingTransitions(currState, state, covered, T);
      if (temp_transfer != null && TestSequence.lenght(temp_transfer) < size) {
        size = TestSequence.lenght(temp_transfer);
        transferSeq = temp_transfer;
        inState = state;
      } 
    } 
    Transition transition = null;
    for (Transition t : uncovered) {
      if (t.getIn() == inState) {
        transition = t;
        break;
      } 
    } 
    logger.info(transition + " " + transferSeq);
    return new ReachingData(transition, transferSeq);
  }
  
  private String selectKappa(String CS, ArrayList<String> K) {
    State finalstate = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), CS);
    logger.info("s: " + finalstate);
    ArrayList<State> reachedStates = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), K);
    int size = Integer.MAX_VALUE;
    String kappa = TestSequence.EPSILON;
    for (State si : this.fsm.getStates()) {
      if (!reachedStates.contains(si)) {
        logger.info("final " + finalstate + ", si: " + si);
        String transferSeq = this.utils.findShortestPathBetween(finalstate, si);
        if (TestSequence.lenght(transferSeq) < size) {
          kappa = transferSeq;
          size = TestSequence.lenght(transferSeq);
        } 
      } 
    } 
    logger.info(finalstate.toString());
    return kappa;
  }
  
  private String selectAlpha(String CS, ArrayList<String> K, HashMap<State, String> DSi, ArrayList<String> T) {
    for (String alpha : T) {
      ArrayList<String> temp_K = new ArrayList<String>(K);
      temp_K.add(alpha);
      String DSitemp = DSi.get(this.fsm.nextStateWithSequence(this.fsm.getInitialState(), alpha));
      if (this.utils.isM_Divergent(temp_K) && (TestSequence.isPrefixOf(TestSequence.concat(alpha, DSitemp), CS) || 
        TestSequence.isProperPrefixOf(CS, TestSequence.concat(alpha, DSitemp))))
        return alpha; 
    } 
    return null;
  }
}
