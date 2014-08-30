package test.domain;

public class TownGraphEdgeBuilder {
    private Integer from;
    private Integer to;
    private Integer distance;

    public TownGraphEdgeBuilder from(final int from) {
        this.from = from;
        return this;
    }

    public TownGraphEdgeBuilder to(final int to) {
        this.to = to;
        return this;
    }

    public TownGraphEdgeBuilder distance(final int distance) {
        this.distance = distance;
        return this;
    }

    public TownGraphEdge build() {
        if (from == null || to == null || distance == null) {
            throw new IllegalStateException(String.format(
                    "Can't build town graph edge: value(s) missing from=[%s], to=[%s], distance=[%s]", from, to,
                    distance));
        }

        return new TownGraphEdge(from, to, distance);
    }
}
