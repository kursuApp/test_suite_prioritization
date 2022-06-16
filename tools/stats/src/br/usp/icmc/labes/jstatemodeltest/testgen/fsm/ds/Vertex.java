package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds;

public class Vertex<E> {
    private final E label;

    public Vertex(E label){
        this.label = label;
    }

    public E getLabel() {
        return label;
    }
}