package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds;

public class Arc<E> {
    private final E label;
    private final E sourceNodeLabel;
    private final E destNodeLabel;
    private double weight;
    private final String input;
    private final String output;
    Arc(E label, E sourceNodeLabel, E destNodeLabel, String input, String output, double weight){
        this.destNodeLabel = destNodeLabel;
        this.sourceNodeLabel = sourceNodeLabel;
        this.weight = weight;
        this.input = input;
        this.output = output;
        this.label = label;
    }

    public E getLabel() {
        return label;
    }

    public E getDestNodeLabel() {
        return destNodeLabel;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public E getSourceNodeLabel() {
        return sourceNodeLabel;
    }
}