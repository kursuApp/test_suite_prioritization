package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import mathCollection.HashMathSet;
import mathCollection.SetOfSets;

public class Graph {
  private ArrayList<String> nodes = new ArrayList<String>();
  
  private HashMap<String, ArrayList<String>> arcs = new HashMap<String, ArrayList<String>>();
  
  public void addNode(String test) {
    this.nodes.add(test);
    this.arcs.put(test, new ArrayList<String>());
  }
  
  public void addArc(String left, String right) {
    ((ArrayList<String>)this.arcs.get(left)).add(right);
    ((ArrayList<String>)this.arcs.get(right)).add(left);
  }
  
  public ArrayList<String> getMaxClique(int n) {
    n++;
    ArrayList<String> clique = null;
    while (clique == null) {
      n--;
      clique = getCliqueV2(n);
    } 
    return clique;
  }
  
  public ArrayList<String> getMaxCliqueV2(int n) {
    if (n == 1) {
      ArrayList<String> ret = new ArrayList<String>();
      ret.add(this.nodes.get(0));
      return ret;
    } 
    ArrayList<ArrayList<String>> cliques = new ArrayList<ArrayList<String>>();
    for (String node : this.nodes) {
      ArrayList<String> c = new ArrayList<String>();
      c.add(node);
      cliques.add(c);
    } 
    for (int k = 2; k <= n; k++) {
      ArrayList<ArrayList<String>> ncliques = new ArrayList<ArrayList<String>>();
      for (ArrayList<String> c : cliques) {
        for (String node : this.nodes) {
          if (!c.contains(node)) {
            ArrayList<String> nc = new ArrayList<String>(c);
            nc.add(node);
            if (isClique(nc, k))
              ncliques.add(nc); 
          } 
        } 
      } 
      if (ncliques.size() == 0)
        return cliques.get(0); 
      if (k == n)
        return ncliques.get(0); 
      cliques = ncliques;
    } 
    return cliques.get(0);
  }
  
  public ArrayList<String> getCliqueV2(int k) {
    ArrayList<String> ret = new ArrayList<String>();
    if (k == 1) {
      ret.add(this.nodes.get(0));
      return ret;
    } 
    for (String node : this.nodes) {
      ArrayList<String> connectedNodes = new ArrayList<String>(this.arcs.get(node));
      connectedNodes.add(node);
      if (connectedNodes.size() >= k)
        for (ArrayList<String> permut : getPermutationsV2(k, connectedNodes)) {
          if (isClique(permut, k))
            return permut; 
        }  
    } 
    return null;
  }
  
  public ArrayList<String> getClique(int k) {
    ArrayList<String> ret = new ArrayList<String>();
    if (k == 1) {
      ret.add(this.nodes.get(0));
      return ret;
    } 
    ArrayList<String> candidates = new ArrayList<String>();
    for (String node : this.nodes) {
      if (((ArrayList)this.arcs.get(node)).size() >= k - 1)
        candidates.add(node); 
    } 
    if (candidates.size() < k)
      return null; 
    for (ArrayList<String> permut : getPermutations(k, candidates)) {
      if (isClique(permut, k))
        return permut; 
    } 
    System.out.println("---------------");
    return null;
  }
  
  private boolean isClique(ArrayList<String> permut, int k) {
    for (String node : permut) {
      ArrayList<String> connections = this.arcs.get(node);
      for (String node2 : permut) {
        if (node != node2)
          if (!connections.contains(node2))
            return false;  
      } 
    } 
    return true;
  }
  
  public ArrayList<ArrayList<String>> getPermutationsV2(int k, ArrayList<String> set) {
    ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
    HashMathSet mset = new HashMathSet(set);
    SetOfSets setofset = mset.fixedSizeSubsets(k);
    for (Iterator<Set<String>> it = setofset.iterator(); it.hasNext(); ) {
      ArrayList<String> newone = new ArrayList<String>(it.next());
      ret.add(newone);
    } 
    return ret;
  }
  
  public ArrayList<ArrayList<String>> getPermutations(int k, ArrayList<String> set) {
    ArrayList<String> current = new ArrayList<String>();
    for (int i = 0; i < k; i++)
      current.add("_"); 
    ArrayList<ArrayList<String>> finalset = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> seqs = new ArrayList<ArrayList<String>>();
    seqs.add(current);
    while (seqs.size() > 0) {
      ArrayList<String> actual = seqs.remove(0);
      boolean is = true;
      for (int j = 0; j < k; j++) {
        if (((String)actual.get(j)).equals("_")) {
          is = false;
          for (String s : set) {
            if (!actual.contains(s)) {
              ArrayList<String> newest = copy(actual);
              newest.set(j, s);
              seqs.add(newest);
            } 
          } 
          break;
        } 
      } 
      if (is)
        add(finalset, actual); 
    } 
    return finalset;
  }
  
  public void add(ArrayList<ArrayList<String>> seqs, ArrayList<String> seq) {
    boolean is = true;
    for (ArrayList<String> s1 : seqs) {
      if (isSameSet(s1, seq)) {
        is = false;
        break;
      } 
    } 
    if (is)
      seqs.add(seq); 
  }
  
  private boolean isSameSet(ArrayList<String> s1, ArrayList<String> s2) {
    if (s1.size() != s2.size())
      return false; 
    for (String s : s1) {
      if (!s2.contains(s))
        return false; 
    } 
    return true;
  }
  
  public ArrayList<String> copy(ArrayList<String> seq) {
    ArrayList<String> ret = new ArrayList<String>();
    for (String s : seq)
      ret.add(s); 
    return ret;
  }
}
