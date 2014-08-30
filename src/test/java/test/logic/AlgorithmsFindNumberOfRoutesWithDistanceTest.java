package test.logic;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import test.domain.TownGraph;
import test.domain.TownGraphEdge;
import test.domain.TownGraphEdgeBuilder;

public class AlgorithmsFindNumberOfRoutesWithDistanceTest {

    @Test
    public void testFromNokia() throws Exception {

        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(1).distance(5).build(),
                new TownGraphEdgeBuilder().from(0).to(3).distance(5).build(),
                new TownGraphEdgeBuilder().from(0).to(4).distance(7).build(),
                new TownGraphEdgeBuilder().from(1).to(2).distance(4).build(),
                new TownGraphEdgeBuilder().from(2).to(3).distance(8).build(),
                new TownGraphEdgeBuilder().from(2).to(4).distance(2).build(),
                new TownGraphEdgeBuilder().from(3).to(2).distance(8).build(),
                new TownGraphEdgeBuilder().from(3).to(4).distance(6).build(),
                new TownGraphEdgeBuilder().from(4).to(1).distance(3).build()
        );
        //J+

        TownGraph townGraph = new TownGraph(edges);

        Optional<Integer> numOfRoutes = Algorithms.findNumberOfRoutesWithDistance(townGraph, 2, 2, 30);

        MatcherAssert.assertThat("result is present", numOfRoutes.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat("number of routes", numOfRoutes.get(), Matchers.is(7));

    }

    @Test
    public void testThreeVertices() throws Exception {
        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(2).distance(1).build(),
                new TownGraphEdgeBuilder().from(1).to(0).distance(2).build(),
                new TownGraphEdgeBuilder().from(1).to(2).distance(3).build()
        );
        //J+

        TownGraph townGraph = new TownGraph(edges);

        Optional<Integer> numOfRoutes = Algorithms.findNumberOfRoutesWithDistance(townGraph, 1, 2, 4);

        MatcherAssert.assertThat("result is present", numOfRoutes.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat("number of routes", numOfRoutes.get(), Matchers.is(2));

    }

    @Test
    public void testTwoVertexesWithCycle() throws Exception {
        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(1).distance(1).build(),
                new TownGraphEdgeBuilder().from(1).to(0).distance(1).build()
        );
        //J+

        TownGraph townGraph = new TownGraph(edges);

        Optional<Integer> numOfRoutes = Algorithms.findNumberOfRoutesWithDistance(townGraph, 0, 1, 4);

        MatcherAssert.assertThat("result is present", numOfRoutes.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat("number of routes", numOfRoutes.get(), Matchers.is(2));
    }

    @Test
    public void testNoRoute() throws Exception {
        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(1).distance(1).build()
        );
        //J+

        TownGraph townGraph = new TownGraph(edges);

        Optional<Integer> numOfRoutes = Algorithms.findNumberOfRoutesWithDistance(townGraph, 1, 0, 2);

        MatcherAssert.assertThat("result is absent", numOfRoutes.isPresent(), Matchers.is(false));
    }
}
