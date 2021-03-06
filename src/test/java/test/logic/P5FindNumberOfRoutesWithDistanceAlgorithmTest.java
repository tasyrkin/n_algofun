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

import test.logic.algorithms.P5FindNumberOfRoutesWithDistanceAlgorithm;

public class P5FindNumberOfRoutesWithDistanceAlgorithmTest {

    @Test
    public void testFromNokia() {

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

        TownGraph townGraph = new TownGraph(edges, 5);

        Optional<Integer> numOfRoutes = new P5FindNumberOfRoutesWithDistanceAlgorithm().algorithm(townGraph, 2, 2, 30);

        MatcherAssert.assertThat("result is present", numOfRoutes.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat("number of routes", numOfRoutes.get(), Matchers.is(7));

    }

    @Test
    public void testThreeVertices() {
        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(2).distance(1).build(),
                new TownGraphEdgeBuilder().from(1).to(0).distance(2).build(),
                new TownGraphEdgeBuilder().from(1).to(2).distance(3).build()
        );
        //J+

        TownGraph townGraph = new TownGraph(edges, 3);

        Optional<Integer> numOfRoutes = new P5FindNumberOfRoutesWithDistanceAlgorithm().algorithm(townGraph, 1, 2, 4);

        MatcherAssert.assertThat("result is present", numOfRoutes.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat("number of routes", numOfRoutes.get(), Matchers.is(2));

    }

    @Test
    public void testTwoVertexesWithCycle() {
        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(1).distance(1).build(),
                new TownGraphEdgeBuilder().from(1).to(0).distance(1).build()
        );
        //J+

        TownGraph townGraph = new TownGraph(edges, 2);

        Optional<Integer> numOfRoutes = new P5FindNumberOfRoutesWithDistanceAlgorithm().algorithm(townGraph, 0, 1, 4);

        MatcherAssert.assertThat("result is present", numOfRoutes.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat("number of routes", numOfRoutes.get(), Matchers.is(2));
    }

    @Test
    public void testNoRoute() {
        //J-
        List<TownGraphEdge> edges = Lists.newArrayList(
                new TownGraphEdgeBuilder().from(0).to(1).distance(1).build()
        );
        //J+

        TownGraph townGraph = new TownGraph(edges, 2);

        Optional<Integer> numOfRoutes = new P5FindNumberOfRoutesWithDistanceAlgorithm().algorithm(townGraph, 1, 0, 2);

        MatcherAssert.assertThat("result is absent", numOfRoutes.isPresent(), Matchers.is(false));
    }
}
