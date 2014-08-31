package test.domain;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.Test;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class TownGraphTest {

    private static class Pair {
        long first;
        long second;

        public Pair(final long first, final long second) {
            this.first = first;
            this.second = second;
        }

        public static Pair of(final long first, final long second) {
            return new Pair(first, second);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Pair pair = (Pair) o;

            if (first != pair.first) {
                return false;
            }

            if (second != pair.second) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = (int) (first ^ (first >>> 32));
            result = 31 * result + (int) (second ^ (second >>> 32));
            return result;
        }

        public String toString() {
            //J-
            return Objects.toStringHelper(this)
                    .add("first", first)
                    .add("second", second)
                    .toString();
            //J+
        }
    }

    @Test
    public void testGetToEdges() {

        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(2).distance(1).build(),
                new TownGraphEdgeBuilder().from(1).to(0).distance(2).build(),
                new TownGraphEdgeBuilder().from(1).to(2).distance(3).build()
        );

        TownGraph townGraph = new TownGraph(edges);

        MatcherAssert.assertThat("Vertexes", townGraph.getVertexes(), Matchers.containsInAnyOrder(0, 1, 2));
        MatcherAssert.assertThat("To edges from 0 vertex", townGraph.getToEdges(0),
                Matchers.containsInAnyOrder(
                        new TownGraphEdgeBuilder().from(0).to(2).distance(1).build()
                ));
        MatcherAssert.assertThat("To edges from 1 vertex", townGraph.getToEdges(1),
                Matchers.containsInAnyOrder(
                        new TownGraphEdgeBuilder().from(1).to(0).distance(2).build(),
                        new TownGraphEdgeBuilder().from(1).to(2).distance(3).build()
                ));
        MatcherAssert.assertThat("To edges from 2 vertex", townGraph.getToEdges(2),
                Matchers.is(Matchers.empty()));
        //J+
    }

    @Test
    public void testGetFromEdges() {
        List<TownGraphEdge> edges = Lists.newArrayList(new TownGraphEdgeBuilder().from(0).to(1).distance(1).build());

        TownGraph townGraph = new TownGraph(edges);

        MatcherAssert.assertThat("Vertexes", townGraph.getVertexes(), Matchers.containsInAnyOrder(0, 1));
        MatcherAssert.assertThat("From edges to 1 vertex", townGraph.getFromEdges(0), Matchers.is(Matchers.empty()));
        MatcherAssert.assertThat("From edges to 0 vertex", townGraph.getFromEdges(1),
            Matchers.containsInAnyOrder(new TownGraphEdgeBuilder().from(0).to(1).distance(1).build()));
    }

    @Test
    public void testGetAdjacencyMatrix() {
        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(2).distance(1).build(),
                new TownGraphEdgeBuilder().from(1).to(0).distance(2).build(),
                new TownGraphEdgeBuilder().from(1).to(2).distance(3).build()
        );

        TownGraph townGraph = new TownGraph(edges);
        //J+

        long[][] connectivityMatrix = townGraph.getConnectivityMatrix();

        MatcherAssert.assertThat("Matrix present", connectivityMatrix, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat("Matrix rows", connectivityMatrix.length, Matchers.is(3));
        MatcherAssert.assertThat("Matrix cols", connectivityMatrix[0].length, Matchers.is(3));
        MatcherAssert.assertThat("Cells where value is 1", getCells(connectivityMatrix, 1),
            Matchers.containsInAnyOrder(Pair.of(0, 2), Pair.of(1, 0), Pair.of(1, 2)));
        //J-
        MatcherAssert.assertThat("Cells where value is 0", getCells(connectivityMatrix, 0),
            Matchers.containsInAnyOrder(
                    Pair.of(0, 0), Pair.of(0, 1),
                                   Pair.of(1, 1),
                    Pair.of(2, 0), Pair.of(2, 1), Pair.of(2, 2)
            )
        );
        //J+

    }

    private List<Pair> getCells(final long[][] matrix, final int value) {
        final List<Pair> result = Lists.newArrayList();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == value) {
                    result.add(Pair.of(i, j));
                }
            }
        }

        return result;
    }

}
