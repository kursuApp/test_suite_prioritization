package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FiniteStateMachine {
  private HashSet<String> inputAlphabet;
  
  private HashSet<String> outputAlphabet;
  
  private ArrayList<State> states;
  
  private ArrayList<Transition> transitions;
  
  private State initialState = null;
  
  public FiniteStateMachine() {
    this.states = new ArrayList<State>();
    this.transitions = new ArrayList<Transition>();
    this.inputAlphabet = new HashSet<String>();
    this.outputAlphabet = new HashSet<String>();
  }
  
  public State getInitialState() {
    return this.initialState;
  }
  
  public void setInitialState(State initialState) {
    this.initialState = initialState;
  }
  
  public int getNumberOfStates() {
    return this.states.size();
  }
  
  public int getNumberOfTransitions() {
    return this.transitions.size();
  }
  
  public void addState(State ns) {
    this.states.add(ns);
  }
  
  public void addTransition(Transition nt) {
    this.transitions.add(nt);
    this.inputAlphabet.add(nt.getInput());
    this.outputAlphabet.add(nt.getOutput());
  }
  
  public ArrayList<State> getStates() {
    return this.states;
  }
  
  public ArrayList<Transition> getTransitions() {
    return this.transitions;
  }
  
  public HashSet<String> getOutputAlphabet() {
    return this.outputAlphabet;
  }
  
  public HashSet<String> getInputAlphabet() {
    return this.inputAlphabet;
  }
  
  public State nextState(State current, String inputsymbol) {
    if (inputsymbol.equals("EPSILON"))
      return current; 
    for (Transition ot : current.getOut()) {
      if (ot.getInput().equals(inputsymbol))
        return ot.getOut(); 
    } 
    return null;
  }
  
  public String nextStateOut(State current, String inputsymbol) {
    if (inputsymbol.equals("EPSILON"))
      return ""; 
    for (Transition ot : current.getOut()) {
      if (ot.getInput().equals(inputsymbol))
        return ot.getOutput(); 
    } 
    return null;
  }
  
  public State nextStateWithSequence(State current, String sequence) {
    String[] symbols = sequence.split(",");
    if (sequence.equals("") || sequence.equals("EPSILON"))
      return current; 
    State curr = current;
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = symbols).length, b = 0; b < i; ) {
      String symb = arrayOfString1[b];
      if (!symb.equals("EPSILON"))
        curr = nextState(curr, symb); 
      b++;
    } 
    return curr;
  }
  
  public ArrayList<State> nextStateWithSequence(State current, ArrayList<String> sequences) {
    ArrayList<State> ret = new ArrayList<State>();
    for (String seq : sequences) {
      State reachedSt = nextStateWithSequence(current, seq);
      if (!ret.contains(reachedSt))
        ret.add(reachedSt); 
    } 
    return ret;
  }
  
  public ArrayList<Transition> reachedTransitionsWithSequence(State current, String sequence) {
    ArrayList<Transition> ret = new ArrayList<Transition>();
    String[] symbols = sequence.split(",");
    if (sequence.equals("") || sequence.equals("EPSILON"))
      return ret; 
    State curr = current;
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = symbols).length, b = 0; b < i; ) {
      String symb = arrayOfString1[b];
      if (!symb.equals("EPSILON"))
        for (Transition ot : curr.getOut()) {
          if (ot.getInput().equals(symb)) {
            curr = ot.getOut();
            ret.add(ot);
            break;
          } 
        }  
      b++;
    } 
    return ret;
  }
  
  public String nextOutput(State current, String sequence) {
    String[] symbols = sequence.split(",");
    if (sequence.equals("") || sequence.equals("EPSILON"))
      return ""; 
    String out = "";
    State curr = current;
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = symbols).length, b = 0; b < i; ) {
      String symb = arrayOfString1[b];
      if (out.equals("")) {
        out = nextStateOut(curr, symb);
      } else {
        out = String.valueOf(out) + "," + nextStateOut(curr, symb);
      } 
      curr = nextState(curr, symb);
      b++;
    } 
    return out;
  }
  
  public String output(String sequence) {
    String[] symbols = sequence.split(",");
    if (sequence.equals("") || sequence.equals("EPSILON"))
      return ""; 
    String out = "";
    State curr = this.initialState;
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = symbols).length, b = 0; b < i; ) {
      String symb = arrayOfString1[b];
      String nextOutput = nextStateOut(curr, symb);
      if (nextOutput == null)
        break; 
      if (out.equals("")) {
        out = nextOutput;
      } else {
        out = String.valueOf(out) + "::" + nextOutput;
      } 
      curr = nextState(curr, symb);
      b++;
    } 
    return out;
  }
  
  public boolean isDefinedSeq(String x, State state) {
    if (x.equals("EPSILON"))
      return true; 
    String[] ins = x.split(",");
    State current = state;
    for (int i = 0; i < ins.length; i++) {
      State next = nextState(current, ins[i]);
      if (next == null)
        return false; 
      current = next;
    } 
    return true;
  }
  
  public boolean separe(String x, State si, State sj) {
    if (x.equals("EPSILON"))
      return false; 
    if (!isDefinedSeq(x, si) || !isDefinedSeq(x, sj))
      return false; 
    String[] in = x.split(",");
    State current_si = si;
    State current_sj = sj;
    for (int i = 0; i < in.length; i++) {
      if (!nextOutput(current_si, in[i]).equals(nextOutput(current_sj, in[i])))
        return true; 
      current_si = nextState(current_si, in[i]);
      current_sj = nextState(current_sj, in[i]);
    } 
    return false;
  }
  
  public String getIOSequence(String s) {
    String[] in = s.split(",");
    String ret = "";
    State current = getInitialState();
    for (int i = 0; i < in.length; i++) {
      if ("".equals(ret)) {
        ret = String.valueOf(ret) + in[i] + ",";
      } else {
        ret = String.valueOf(ret) + "," + in[i] + ",";
      } 
      if (nextStateOut(current, in[i]) == null) {
        ret = String.valueOf(ret) + "UNDEFINED";
      } else {
        ret = String.valueOf(ret) + nextStateOut(current, in[i]);
      } 
      current = nextState(current, in[i]);
    } 
    return ret;
  }
  
  public boolean isUIO(State si, String seq) {
    if (seq.equals(TestSequence.EPSILON))
      return false; 
    for (State sj : this.states) {
      if (si != sj)
        if (!separe(seq, si, sj))
          return false;  
    } 
    return true;
  }
  
  public State nextStateWithSequence(State current, String sequence, ArrayList<Transition> transitions) {
    String[] symbols = sequence.split(",");
    if (sequence.equals("") || sequence.equals(TestSequence.EPSILON))
      return current; 
    State curr = current;
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = symbols).length, b = 0; b < i; ) {
      String symb = arrayOfString1[b];
      if (!symb.equals(TestSequence.EPSILON)) {
        boolean ok = false;
        for (Transition ot : curr.getOut()) {
          if (ot.getInput().equals(symb) && transitions.contains(ot)) {
            curr = ot.getOut();
            ok = true;
            break;
          } 
        } 
        if (!ok)
          return null; 
      } 
      b++;
    } 
    return curr;
  }
  
  public void checkMinimality() {
    int dist = 0, noDist = 0;
    for (int i = 0; i < this.states.size(); i++) {
      State si = this.states.get(i);
      for (int j = i + 1; j < this.states.size(); j++) {
        State sj = this.states.get(j);
        String distseq = distiguishSeqN(si, sj);
        if (distseq == null) {
          System.out.println("(" + si + "," + sj + ") -> " + distseq);
          noDist++;
        } else {
          dist++;
        } 
      } 
    } 
    System.out.println("distinguisable pairs: " + dist);
    System.out.println("non-distinguisable pairs: " + noDist);
    System.out.println("Distinguisability ration: " + (dist / (dist + noDist)));
  }
  
  public boolean isReduced() {
    for (int i = 0; i < this.states.size(); i++) {
      State si = this.states.get(i);
      for (int j = i + 1; j < this.states.size(); j++) {
        State sj = this.states.get(j);
        String distseq = distiguishSeqN(si, sj);
        if (distseq == null)
          return false; 
      } 
    } 
    return true;
  }
  
  public void checkMinimality2() {
    int count = 0;
    for (int i = 0; i < this.states.size(); i++) {
      State si = this.states.get(i);
      for (int j = i + 1; j < this.states.size(); j++) {
        State sj = this.states.get(j);
        String distseq = distiguishSeqN(si, sj);
        if (distseq == null)
          if (!isDistinguishedByDefinedInputs(si, sj))
            System.out.println(String.valueOf(count) + ": " + si + " , " + sj);  
      } 
    } 
  }
  
  private boolean isDistinguishedByDefinedInputs(State si, State sj) {
    HashSet<String> seti = new HashSet<String>();
    for (Transition t : si.getOut())
      seti.add(t.getInput()); 
    HashSet<String> setj = new HashSet<String>();
    for (Transition t : sj.getOut())
      setj.add(t.getInput()); 
    return !seti.equals(setj);
  }
  
  public String distiguishSeqN(State si, State sj) {
    int n = getNumberOfStates();
    ArrayList<String> queue = new ArrayList<String>();
    queue.addAll(this.inputAlphabet);
    while (!queue.isEmpty()) {
      String seq = queue.remove(0);
      if (separe(seq, si, sj))
        return seq; 
      State reachedFromSi = nextStateWithSequence(si, seq);
      State reachedFromSj = nextStateWithSequence(sj, seq);
      if (reachedFromSi != reachedFromSj && TestSequence.lenght(seq) <= 6)
        for (String x : getInputAlphabet()) {
          String newseq = TestSequence.concat(seq, x);
          if (isDefinedSeq(newseq, si) && isDefinedSeq(newseq, sj))
            queue.add(newseq); 
        }  
    } 
    return null;
  }
  
  public void printNumbersKiss() {
    ArrayList<String> t_inputs = new ArrayList<String>(this.inputAlphabet);
    ArrayList<String> t_outputs = new ArrayList<String>(this.outputAlphabet);
    System.out.println(".i " + t_inputs.size());
    System.out.println(".o " + t_outputs.size());
    System.out.println(".p " + this.transitions.size());
    System.out.println(".s " + this.states.size());
    System.out.println(".r 0");
    for (Transition t : this.transitions)
      System.out.println(String.valueOf(t_inputs.indexOf(t.getInput())) + " s" + this.states.indexOf(t.getIn()) + " s" + this.states.indexOf(t.getOut()) + " " + t_outputs.indexOf(t.getOutput())); 
  }
  
  public void printNumbersFSM() {
    ArrayList<String> t_inputs = new ArrayList<String>(this.inputAlphabet);
    ArrayList<String> t_outputs = new ArrayList<String>(this.outputAlphabet);
    for (Transition t : this.transitions)
      System.out.println(String.valueOf(this.states.indexOf(t.getIn())) + " -- " + t_inputs.indexOf(t.getInput()) + " / " + t_outputs.indexOf(t.getOutput()) + " -> " + this.states.indexOf(t.getOut())); 
    System.out.println("IN");
    int i;
    for (i = 0; i < t_inputs.size(); i++)
      System.out.println(String.valueOf(i) + " : " + (String)t_inputs.get(i)); 
    System.out.println("OUT");
    for (i = 0; i < t_outputs.size(); i++)
      System.out.println(String.valueOf(i) + " : " + (String)t_outputs.get(i)); 
  }
  
  public void addEnabledInputs() {
    HashMap<HashSet<String>, String> map = new HashMap<HashSet<String>, String>();
    for (State si : this.states) {
      HashSet<String> defInputs = new HashSet<String>();
      for (Transition defT : si.getOut())
        defInputs.add(defT.getInput()); 
      String output = map.get(defInputs);
      if (output == null) {
        output = defInputs.toString();
        output = output.replace(" ", "");
        map.put(defInputs, output);
      } 
      Transition selfLoop = new Transition(si, si, "enabledInputs", output);
      addTransition(selfLoop);
    } 
  }
  
  public void printFSM() {
    for (State si : this.states) {
      for (Transition t : si.getOut())
        System.out.println(t); 
    } 
  }
  
  public void printFSMnoEnabled() {
    for (State si : this.states) {
      for (Transition t : si.getOut()) {
        if (!t.getInput().equals("enabledInputs"))
          System.out.println(t); 
      } 
    } 
  }
  
  public void printInfo() {
    System.out.println("------------------------------------------------------------------");
    System.out.println("# states: " + getNumberOfStates());
    System.out.println("# transitions: " + getNumberOfTransitions());
    System.out.println("# input symbols: " + getInputAlphabet().size());
    System.out.println("# output symbols: " + getOutputAlphabet().size());
    System.out.println("------------------------------------------------------------------");
  }
  
  public boolean checkDeterminism() {
    for (State si : getStates()) {
      ArrayList<String> tempDef = new ArrayList<String>();
      for (Transition t : si.getOut()) {
        if (tempDef.contains(t.getInput()))
          return false; 
        tempDef.add(t.getInput());
      } 
    } 
    return true;
  }
  
  public boolean isComplete() {
    for (State si : getStates()) {
      ArrayList<String> tempDef = new ArrayList<String>();
      for (Transition t : si.getOut()) {
        if (!tempDef.contains(t.getInput()))
          tempDef.add(t.getInput()); 
      } 
      if (tempDef.size() != this.inputAlphabet.size())
        return false; 
    } 
    return true;
  }
  
  public State getStateWithLabel(String label) {
    for (State si : getStates()) {
      if (si.getLabel().equals(label))
        return si; 
    } 
    return null;
  }
  
  public void printByStructure() {
    HashMap<State, String> map = new HashMap<State, String>();
    ArrayList<State> queue = new ArrayList<State>();
    System.out.println("----------");
    queue.add(this.initialState);
    while (queue.size() > 0) {
      State si = queue.remove(0);
      if (map.get(si) == null) {
        map.put(si, "ok");
        for (Transition t : si.getOut()) {
          System.out.println(t);
          queue.add(t.getOut());
        } 
      } 
    } 
    System.out.println("----------");
  }
  
  public void printStats() {
    System.out.println("Number of states: " + this.states.size());
    System.out.println("Number of inputs: " + this.inputAlphabet.size());
    System.out.println("Number of outputs: " + this.outputAlphabet.size());
    System.out.println("Number of transitions: " + this.transitions.size());
  }
  
  public Transition getTransitionWithLabel(String label, String input) {
    for (Transition t : getTransitions()) {
      if (t.getIn().getLabel().equals(label) && t.getInput().equals(input))
        return t; 
    } 
    return null;
  }
  
  public void printLettersFSM() {
    ArrayList<String> t_inputs = new ArrayList<String>(this.inputAlphabet);
    ArrayList<String> t_outputs = new ArrayList<String>(this.outputAlphabet);
    for (Transition t : this.transitions)
      System.out.println(String.valueOf(this.states.indexOf(t.getIn())) + " -- " + getLetter(t_inputs.indexOf(t.getInput())) + " / " + getLetter(t_outputs.indexOf(t.getOutput())) + " -> " + this.states.indexOf(t.getOut())); 
    System.out.println("STATE");
    int i;
    for (i = 0; i < this.states.size(); i++)
      System.out.println(String.valueOf(i) + " : " + this.states.get(i)); 
    System.out.println("IN");
    for (i = 0; i < t_inputs.size(); i++)
      System.out.println(String.valueOf(getLetter(i)) + " : " + (String)t_inputs.get(i)); 
    System.out.println("OUT");
    for (i = 0; i < t_outputs.size(); i++)
      System.out.println(String.valueOf(getLetter(i)) + " : " + (String)t_outputs.get(i)); 
  }
  
  private String getLetter(int index) {
    char a = (char)(index + 97);
    return String.valueOf(a);
  }
  
  public boolean separe(ArrayList<String> wi, State si, State sj) {
    for (String seq : wi) {
      if (separe(seq, si, sj))
        return true; 
    } 
    return false;
  }
  
  public boolean isWi(State si, ArrayList<String> set1) {
    for (State sj : this.states) {
      if (si != sj)
        if (!separe(set1, si, sj))
          return false;  
    } 
    return true;
  }
}
