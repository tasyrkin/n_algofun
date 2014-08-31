package test.logic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import test.domain.TownGraph;
import test.domain.TownGraphEdge;
import test.domain.TownGraphEdgeBuilder;

import test.logic.algorithms.P1CalculateDistanceForRouteAlgorithm;

@RunWith(Parameterized.class)
public class P1CalculateDistanceForRouteAlgorithmTest {

    private TownGraph townGraph;
    private Optional<Integer> expectedDistance;
    private List<Integer> route;

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
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

        TownGraph nokiaTestTownGraph = new TownGraph(edges, 5);

        //J-
        final Object[][] data = new Object[][] {
                {nokiaTestTownGraph, Optional.of(9), Lists.newArrayList(0, 1, 2)},
                {nokiaTestTownGraph, Optional.of(5), Lists.newArrayList(0, 3)},
                {nokiaTestTownGraph, Optional.of(13), Lists.newArrayList(0, 3, 2)},
                {nokiaTestTownGraph, Optional.of(22), Lists.newArrayList(0, 4, 1, 2, 3)},
                {nokiaTestTownGraph, Optional.absent(), Lists.newArrayList(0, 4, 3)},
        };
        //J+

        return Arrays.asList(data);
    }

    public P1CalculateDistanceForRouteAlgorithmTest(final TownGraph townGraph, final Optional<Integer> expectedDistance,
            final List<Integer> route) {
        this.townGraph = townGraph;
        this.expectedDistance = expectedDistance;
        this.route = route;
    }

    @Test
    public void test() {
        Optional<Integer> distance = new P1CalculateDistanceForRouteAlgorithm().algorithm(townGraph, route);
        MatcherAssert.assertThat("distance", distance, Matchers.is(expectedDistance));
    }
}
