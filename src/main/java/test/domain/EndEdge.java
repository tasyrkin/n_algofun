package test.domain;

public class EndEdge {
    private int vertex;
    private int cost;

    public EndEdge(final int vertex, final int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }

    public int getVertex() {
        return vertex;
    }

    public int getCost() {
        return cost;
    }
}
