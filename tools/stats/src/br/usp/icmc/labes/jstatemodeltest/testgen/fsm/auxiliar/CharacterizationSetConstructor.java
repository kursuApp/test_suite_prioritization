package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar;

import br.usp.icmc.labes.jstatemodeltest.pcomplete.Pair;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSet;
import java.util.ArrayList;
import java.util.HashSet;

public class CharacterizationSetConstructor {
  FiniteStateMachine fsm;
  
  ArrayList<String> wset;
  
  public void setFsm(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public CharacterizationSetConstructor() {}
  
  public CharacterizationSetConstructor(FiniteStateMachine fsm) {
    this.fsm = fsm;
    build();
  }
  
  public void build() {
    this.wset = new ArrayList<String>();
    for (State si : this.fsm.getStates()) {
      for (State sj : this.fsm.getStates()) {
        if (si != sj) {
          String dseq = distiguishSeqN(si, sj);
          if (!this.wset.contains(dseq))
            this.wset.add(dseq); 
        } 
      } 
    } 
  }
  
  private String distiguishSeq(State si, State sj) {
    int k = 1;
    int n = this.fsm.getNumberOfStates();
    while (k <= n * (n - 1) / 2) {
      for (String x : getInputSeqWithLength(k)) {
        if (this.fsm.separe(x, si, sj))
          return x; 
      } 
      k++;
    } 
    if (k > n * (n - 1) / 2)
      System.out.println("ERROR: no minimal machine: " + si + "," + sj); 
    return null;
  }
  
  private String distiguishSeqN(State si, State sj) {
    int n = this.fsm.getNumberOfStates();
    ArrayList<String> queue = new ArrayList<String>();
    queue.add(TestSequence.EPSILON);
    while (!queue.isEmpty()) {
      String seq = queue.remove(0);
      if (this.fsm.separe(seq, si, sj))
        return seq; 
      if (TestSequence.lenght(seq) <= n * (n - 1) / 2) {
        for (String x : this.fsm.getInputAlphabet()) {
          String newseq = TestSequence.concat(seq, x);
          if (this.fsm.isDefinedSeq(newseq, si) && this.fsm.isDefinedSeq(newseq, sj))
            queue.add(newseq); 
        } 
        continue;
      } 
      System.out.println("ERROR: no minimal machine: " + si + "," + sj);
      break;
    } 
    return null;
  }
  
  public String getDistinguishSequence(State si, State sj, ArrayList<String> T) {
    return distiguishSeqN(si, sj);
  }
  
  public void initInputs(int k) {}
  
  public boolean hasNextInput() {
    return true;
  }
  
  public String nextInput() {
    return null;
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
  
  public ArrayList<String> getWset() {
    return this.wset;
  }
  
  public CostTuple getDistinguishSequence(String alpha, String beta, ArrayList<String> T, ArrayList<Pair> C) {
    State si = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), alpha);
    State sj = this.fsm.nextStateWithSequence(this.fsm.getInitialState(), beta);
    int n = this.fsm.getNumberOfStates();
    ArrayList<CostTuple> queue = new ArrayList<CostTuple>();
    for (String alp : Pair.getPartition(alpha, C)) {
      for (String bet : Pair.getPartition(beta, C)) {
        CostTuple costTuple = new CostTuple();
        costTuple.setGamma(TestSequence.EPSILON);
        costTuple.setAlphacost(0);
        costTuple.setBetacost(0);
        costTuple.setAlpha(alp);
        costTuple.setBeta(bet);
        queue.add(costTuple);
      } 
    } 
    CostTuple tuple = new CostTuple();
    tuple.setGamma(TestSequence.EPSILON);
    tuple.setAlphacost(0);
    tuple.setBetacost(0);
    tuple.setAlpha(alpha);
    tuple.setBeta(beta);
    queue.add(tuple);
    while (!queue.isEmpty()) {
      tuple = queue.remove(0);
      if (this.fsm.separe(tuple.getGamma(), si, sj))
        return tuple; 
      if (this.fsm.nextStateWithSequence(si, tuple.getGamma()) != this.fsm.nextStateWithSequence(sj, tuple.getGamma()))
        for (String x : this.fsm.getInputAlphabet()) {
          String newseq = TestSequence.concat(tuple.getGamma(), x);
          if (this.fsm.isDefinedSeq(newseq, si) && this.fsm.isDefinedSeq(newseq, sj)) {
            String alphagamma = TestSequence.concat(tuple.getAlpha(), tuple.getGamma());
            String betagamma = TestSequence.concat(tuple.getBeta(), tuple.getGamma());
            int alphax_cost = TestSet.calcCost(alphagamma, x, T, tuple.getAlphacost());
            int betax_cost = TestSet.calcCost(betagamma, x, T, tuple.getBetacost());
            CostTuple tuple1 = new CostTuple();
            tuple1.setGamma(newseq);
            tuple1.setAlpha(tuple.getAlpha());
            tuple1.setBeta(tuple.getBeta());
            tuple1.setAlphacost(alphax_cost);
            tuple1.setBetacost(betax_cost);
            int addcost = alphax_cost + betax_cost;
            int i = 0;
            for (; i < queue.size(); i++) {
              CostTuple ti = queue.get(i);
              if (addcost <= ti.getAlphacost() + ti.getBetacost())
                break; 
            } 
            queue.add(i, tuple1);
          } 
        }  
    } 
    return null;
  }
}
