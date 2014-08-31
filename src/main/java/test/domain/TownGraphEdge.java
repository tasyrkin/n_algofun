package test.domain;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Represents the edge of town graph. The distance between vertices must be positive.
 */
public class TownGraphEdge {
    private int from;
    private int to;
    private int distance;

    public TownGraphEdge(final int from, final int to, final int distance) {
        Preconditions.checkArgument(distance > 0, "Distance must be positive, was [%s]", distance);
        Preconditions.checkArgument(from >= 0, "From vertex must be non negative, was [%s]", from);
        Preconditions.checkArgument(to >= 0, "From vertex must be non negative, was [%s]", to);

        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public TownGraphEdge(final TownGraphEdge townGraphEdge) {
        this(townGraphEdge.getFrom(), townGraphEdge.getTo(), townGraphEdge.getDistance());
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

    public TownGraphEdge revert() {
        return new TownGraphEdgeBuilder().from(to).to(from).distance(distance).build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TownGraphEdge that = (TownGraphEdge) o;

        if (distance != that.distance) {
            return false;
        }

        if (from != that.from) {
            return false;
        }

        if (to != that.to) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = from;
        result = 31 * result + to;
        result = 31 * result + distance;
        return result;
    }

    public String toString() {
        //J-
        return Objects.toStringHelper(this)
                .add("from", from)
                .add("to", to)
                .add("distance", distance)
                .toString();
        //J+
    }
}
