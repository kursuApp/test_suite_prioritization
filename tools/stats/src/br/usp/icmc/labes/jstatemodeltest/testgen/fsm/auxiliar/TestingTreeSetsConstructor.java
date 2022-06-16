package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.State;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.Transition;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Node;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Ntree;
import java.util.ArrayList;
import java.util.HashMap;

public class TestingTreeSetsConstructor extends AbstractSetsConstructor {
  protected Ntree tree;
  
  protected HashMap<State, String> visited;
  
  public TestingTreeSetsConstructor(FiniteStateMachine fsm) {
    super(fsm);
  }
  
  public void construct() {
    createTestingTreeBreadth(this.fsm);
    generateCoverSets(this.tree);
  }
  
  public Ntree getTree() {
    return this.tree;
  }
  
  private void createTestingTreeBreadth(FiniteStateMachine fsm) {
    this.visited = new HashMap<State, String>();
    this.tree = new Ntree();
    ArrayList<Node> queue = new ArrayList<Node>();
    Node root = new Node(fsm.getInitialState());
    this.tree.setRoot(root);
    queue.add(root);
    while (!queue.isEmpty()) {
      Node current = queue.remove(0);
      if (this.visited.get(current.getState()) == null) {
        this.visited.put(current.getState(), "ok");
        for (Transition t : current.getState().getOut()) {
          Node newnode = new Node(t.getOut());
          this.tree.addNode(current, t.getInput(), newnode);
          queue.add(newnode);
        } 
      } 
    } 
  }
  
  protected void generateCoverSets(Ntree tree) {
    this.stateCover = new ArrayList<String>();
    this.transitionCover = new ArrayList<String>();
    this.visited = new HashMap<State, String>();
    ArrayList<Node> queue = new ArrayList<Node>();
    ArrayList<String> queue_seqs = new ArrayList<String>();
    queue.add(tree.getRoot());
    queue_seqs.add("EPSILON");
    while (!queue.isEmpty()) {
      Node current = queue.remove(0);
      String curr_seq = queue_seqs.remove(0);
      if (this.visited.get(current.getState()) == null) {
        this.visited.put(current.getState(), "ok");
        this.stateCover.add(curr_seq);
      } 
      this.transitionCover.add(curr_seq);
      for (int i = 0; i < current.getChildren().size(); i++) {
        queue.add(current.getChildren().get(i));
        queue_seqs.add(TestSequence.concat(curr_seq, current.getLabels().get(i)));
      } 
    } 
  }
}
