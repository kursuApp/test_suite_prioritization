package br.usp.icmc.labes.jstatemodeltest.common;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class FsmModelReader {
  private File file;
  
  private boolean validFile;
  
  private FiniteStateMachine fsm;
  
  private HashMap<String, State> states;
  
  public File getFile() {
    return this.file;
  }
  
  public void setFile(File file) {
    this.file = file;
  }
  
  public FiniteStateMachine getFsm() throws Exception {
    if (this.fsm.getStates().size() == 0 || this.fsm.getTransitions().size() == 0)
      throw new Exception("Empty FSM"); 
    return this.fsm;
  }
  
  public FsmModelReader(File file) {
    this.file = file;
    this.fsm = new FiniteStateMachine();
    this.states = new HashMap<String, State>();
    read();
  }
  
  public FsmModelReader(File file, boolean adesFormat) {
    this.file = file;
    this.fsm = new FiniteStateMachine();
    this.states = new HashMap<String, State>();
    readAdes();
  }
  
  private void addTransition(String line) throws Exception {
    String[] token = line.split(" ");
    if (token.length != 4)
      throw new Exception("Non well formed transition"); 
    State s1 = this.states.get(token[0]);
    if (s1 == null) {
      s1 = new State(token[0]);
      if (this.fsm.getStates().size() == 0)
        this.fsm.setInitialState(s1); 
      this.fsm.addState(s1);
      this.states.put(token[0], s1);
    } 
    State s2 = this.states.get(token[3]);
    if (s2 == null) {
      s2 = new State(token[3]);
      this.fsm.addState(s2);
      this.states.put(token[3], s2);
    } 
    Transition t = new Transition(s1, s2, token[1], token[2]);
    this.fsm.addTransition(t);
  }
  
  private void read() {
    this.validFile = true;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(this.file));
      String line = "";
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.equals("") && !line.startsWith("#"))
          addTransition(line); 
      } 
    } catch (Exception e) {
      this.validFile = false;
      e.printStackTrace();
    } 
  }

  private void readAdes() {
    this.validFile = true;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(this.file));
      String line = "";
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.equals("") && !line.startsWith("#")) {
          line = line.replaceAll(" -- ", " ");
          line = line.replaceAll(" / ", " ");
          line = line.replaceAll(" -> ", " ");
          addTransition(line);
        } 
      } 
    } catch (Exception e) {
      this.validFile = false;
      e.printStackTrace();
    } 
  }
  
  public boolean isValidFile() {
    return this.validFile;
  }
}
