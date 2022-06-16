package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import java.util.ArrayList;
import java.util.HashMap;

public class HarmonizedIdentifiersConstructor {
  private ArrayList<String> W;
  
  private FiniteStateMachine fsm;
  
  private HashMap<State, ArrayList<String>> Hs;
  
  public void setFsm(FiniteStateMachine fsm) {
    this.fsm = fsm;
  }
  
  public void setW(ArrayList<String> w) {
    this.W = w;
  }
  
  public void construct() {
    this.Hs = new HashMap<State, ArrayList<String>>();
    for (State state : this.fsm.getStates()) {
      ArrayList<String> hseqs = new ArrayList<String>();
      for (String wseq : this.W)
        hseqs.add(wseq); 
      this.Hs.put(state, hseqs);
    } 
    reduce();
  }
  
  public ArrayList<String> getHi(State state) {
    return this.Hs.get(state);
  }
  
  private void reduce() {
    int bigger_size = -1;
    State bigger_state = null;
    int bigger_index = -1;
    for (State state : this.fsm.getStates()) {
      ArrayList<String> Hi = getHi(state);
      for (int i = 0; i < Hi.size(); i++) {
        String seq = Hi.remove(i);
        if (isSeparatingFamily() && seq.length() > bigger_size) {
          bigger_size = seq.length();
          bigger_state = state;
          bigger_index = i;
        } 
        Hi.add(i, seq);
      } 
    } 
    if (bigger_size != -1) {
      ArrayList<String> Hi = getHi(bigger_state);
      Hi.remove(bigger_index);
      reduce();
    } 
  }
  
  public boolean isSeparatingFamily() {
    ArrayList<State> states = this.fsm.getStates();
    for (int i = 0; i < states.size(); i++) {
      for (int j = i + 1; j < states.size(); j++) {
        State si = states.get(i);
        State sj = states.get(j);
        ArrayList<String> Hi = getHi(si);
        ArrayList<String> Hj = getHi(sj);
        boolean hasAlfa = false;
        for (String beta : Hi) {
          for (String gama : Hj) {
            for (String alfa : getCommonPrefixes(beta, gama)) {
              if (!this.fsm.nextOutput(si, alfa).equals(this.fsm.nextOutput(sj, alfa))) {
                hasAlfa = true;
                break;
              } 
            } 
            if (hasAlfa)
              break; 
          } 
          if (hasAlfa)
            break; 
        } 
        if (!hasAlfa)
          return false; 
      } 
    } 
    return true;
  }
  
  private ArrayList<String> getCommonPrefixes(String a, String b) {
    ArrayList<String> ret = new ArrayList<String>();
    String[] as = a.split(",");
    String[] bs = b.split(",");
    String curr = "";
    for (int i = 0; i < as.length && i < bs.length && 
      as[i].equals(bs[i]); i++) {
      if (i == 0) {
        curr = as[i];
      } else {
        curr = String.valueOf(curr) + "," + as[i];
      } 
      ret.add(curr);
    } 
    return ret;
  }
}
