package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds;

import java.util.*;

public class MultiDiGraph<E> {
    HashMap<E, Vertex<E>> vertices;
    HashMap<E, Arc<E>> arcs;
    HashMap<E, LinkedList<E>> vertexArcs;

    public MultiDiGraph(){
        this.vertices = new HashMap<>();
        this.arcs = new HashMap<>();
    }


    public Arc<E> addArc(E label, E sourceNodeLabel, E destNodeLabel, String input, String output, double weight){
        if(arcs.containsKey(label)) return arcs.get(label);
        Arc<E> arc = new Arc<E>(label, sourceNodeLabel, destNodeLabel, input, output, weight);
        arcs.put(label,arc);
        vertexArcs.get(sourceNodeLabel).add(arc.getLabel());
        return arc;
    }
    public void addArc(Arc<E> arc){
        if(arcs.containsKey(arc.getLabel())) return;
        arcs.put(arc.getLabel(), arc);
        vertexArcs.get(arc.getSourceNodeLabel()).add(arc.getLabel());
    }

    public Collection<Vertex<E>> getVertices() {
        return vertices.values();
    }
    public LinkedList<E> getArcs(E vertexLabel){
        return vertexArcs.get(vertexLabel);
    }
    public Vertex<E> getVertex(E label) {
        return vertices.get(label);
    }
    public Vertex<E> addVertex(E label){
        if (vertices.containsKey(label)){
            return vertices.get(label);
        }
        Vertex<E> vertex = new Vertex<>(label);
        vertices.put(label,vertex);
        vertexArcs.put(label,new LinkedList<>());
        return vertex;
    }
    public Arc<E> getArc(E arcLabel){
        return arcs.get(arcLabel);
    }

    public Set<E> getArcs() {
        return arcs.keySet();
    }

    public void printGraph(){
        for (Vertex<E> vertex: vertices.values()) {
            for (Arc<E> arc: arcs.values()){
                System.out.println(vertex.getLabel() + " -- " + arc.getInput() +" / " + arc.getOutput() + " -> " +
                        arc.getDestNodeLabel() +" ( "+arc.getWeight() + " ) ");
            }

        }
    }
}
