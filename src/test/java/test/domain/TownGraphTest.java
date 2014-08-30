package test.domain;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.Test;

import com.google.common.collect.Lists;

public class TownGraphTest {
    @Test
    public void testGetToEdges() throws Exception {

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
    public void testGetFromEdges() throws Exception {
        List<TownGraphEdge> edges = Lists.newArrayList(new TownGraphEdgeBuilder().from(0).to(1).distance(1).build());

        TownGraph townGraph = new TownGraph(edges);

        MatcherAssert.assertThat("Vertexes", townGraph.getVertexes(), Matchers.containsInAnyOrder(0, 1));
        MatcherAssert.assertThat("From edges to 1 vertex", townGraph.getFromEdges(0), Matchers.is(Matchers.empty()));
        MatcherAssert.assertThat("From edges to 0 vertex", townGraph.getFromEdges(1),
            Matchers.containsInAnyOrder(new TownGraphEdgeBuilder().from(0).to(1).distance(1).build()));
    }

}
