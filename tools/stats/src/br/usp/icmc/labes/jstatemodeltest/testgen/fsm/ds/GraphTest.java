package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds;

import org.junit.Test;

public class GraphTest {
  @Test
  public void test01() {
    Graph graph = new Graph();
    graph.addNode("a");
    graph.addNode("b");
    graph.addNode("c");
    System.out.println(": " + graph.getClique(1));
    graph.addArc("a", "b");
    graph.addArc("a", "c");
    graph.addArc("b", "c");
    System.out.println(": " + graph.getClique(2));
    graph.addNode("d");
    graph.addArc("b", "d");
    graph.addArc("c", "d");
    System.out.println(": " + graph.getClique(2));
    System.out.println(": " + graph.getClique(3));
    System.out.println("max: " + graph.getMaxClique(4));
    System.out.println("max: " + graph.getMaxCliqueV2(4));
  }
  
  @Test
  public void test02() {
    Graph graph = new Graph();
    graph.addNode("a");
    graph.addNode("b");
    graph.addNode("c");
    graph.addNode("d");
    graph.addNode("e");
    graph.addNode("f");
    graph.addNode("g");
    graph.addNode("h");
    graph.addNode("i");
    graph.addNode("j");
    graph.addNode("k");
    graph.addArc("a", "b");
    graph.addArc("a", "c");
    graph.addArc("b", "c");
    graph.addArc("b", "d");
    graph.addArc("c", "d");
    graph.addArc("e", "f");
    graph.addArc("j", "k");
    graph.addArc("a", "e");
    graph.addArc("b", "e");
    graph.addArc("c", "e");
    System.out.println("max: " + graph.getMaxClique(11));
    System.out.println("max: " + graph.getMaxCliqueV2(11));
    System.out.println("max: " + graph.getCliqueV2(4));
  }
  
  @Test
  public void test03() {
    Graph graph = new Graph();
    graph.addNode("a");
    graph.addNode("b");
    graph.addNode("c");
    System.out.println(": " + graph.getCliqueV2(1));
    graph.addArc("a", "b");
    graph.addArc("a", "c");
    graph.addArc("b", "c");
    System.out.println(": " + graph.getCliqueV2(2));
    graph.addNode("d");
    graph.addArc("b", "d");
    graph.addArc("c", "d");
    System.out.println(": " + graph.getCliqueV2(2));
    System.out.println(": " + graph.getCliqueV2(3));
    System.out.println("max: " + graph.getMaxClique(4));
    System.out.println("max: " + graph.getMaxCliqueV2(4));
  }
}
