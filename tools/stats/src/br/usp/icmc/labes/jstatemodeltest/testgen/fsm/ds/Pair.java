package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds;

public class Pair<L,R> {

    private final L left;
    private final R right;

    public Pair(L left, R right) {
        assert left != null;
        assert right != null;

        this.left = left;
        this.right = right;
    }

    public L getLeft() { return left; }
    public R getRight() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pair = (Pair) o;
        return this.left.equals(pair.getLeft()) &&
                this.right.equals(pair.getRight());
    }

}
