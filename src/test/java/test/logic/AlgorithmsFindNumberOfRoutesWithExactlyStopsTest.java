package test.logic;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;

import com.google.common.collect.Lists;

import test.domain.TownGraph;
import test.domain.TownGraphEdgeBuilder;

@RunWith(Parameterized.class)
public class AlgorithmsFindNumberOfRoutesWithExactlyStopsTest {

    private Parameters parameters;

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        //J-
        final TownGraph nokiaTestTownGraph = new TownGraph(
                Lists.newArrayList(
                    new TownGraphEdgeBuilder().from(0).to(1).distance(5).build(),
                    new TownGraphEdgeBuilder().from(0).to(3).distance(5).build(),
                    new TownGraphEdgeBuilder().from(0).to(4).distance(7).build(),
                    new TownGraphEdgeBuilder().from(1).to(2).distance(4).build(),
                    new TownGraphEdgeBuilder().from(2).to(3).distance(8).build(),
                    new TownGraphEdgeBuilder().from(2).to(4).distance(2).build(),
                    new TownGraphEdgeBuilder().from(3).to(2).distance(8).build(),
                    new TownGraphEdgeBuilder().from(3).to(4).distance(6).build(),
                    new TownGraphEdgeBuilder().from(4).to(1).distance(3).build()
                )
        );

        final TownGraph threeVertexCompleteGraph = new TownGraph(
                Lists.newArrayList(
                        new TownGraphEdgeBuilder().from(0).to(1).distance(1).build(),
                        new TownGraphEdgeBuilder().from(1).to(0).distance(1).build(),
                        new TownGraphEdgeBuilder().from(0).to(2).distance(1).build(),
                        new TownGraphEdgeBuilder().from(2).to(0).distance(1).build(),
                        new TownGraphEdgeBuilder().from(1).to(2).distance(1).build(),
                        new TownGraphEdgeBuilder().from(2).to(1).distance(1).build()
                )
        );

        final Object[][] data = new Object[][] {
                {
                    new Parameters()
                        .townGraph(nokiaTestTownGraph)
                        .from(0)
                        .to(2)
                        .stops(4)
                        .expectedNumberOfRoutes(3)
                },{
                    new Parameters()
                        .townGraph(nokiaTestTownGraph)
                        .from(0)
                        .to(0)
                        .stops(100)
                        .expectedNumberOfRoutes(0)
                },{
                    new Parameters()
                        .townGraph(threeVertexCompleteGraph)
                        .from(0)
                        .to(0)
                        .stops(1)
                        .expectedNumberOfRoutes(0)
                },{
                    new Parameters()
                        .townGraph(threeVertexCompleteGraph)
                        .from(0)
                        .to(0)
                        .stops(2)
                        .expectedNumberOfRoutes(2)
                },{
                    new Parameters()
                        .townGraph(threeVertexCompleteGraph)
                        .from(0)
                        .to(0)
                        .stops(3)
                        .expectedNumberOfRoutes(2)
                },{
                    new Parameters()
                        .townGraph(threeVertexCompleteGraph)
                        .from(0)
                        .to(0)
                        .stops(4)
                        .expectedNumberOfRoutes(6)
                }
        };
        //J+

        return Arrays.asList(data);
    }

    public AlgorithmsFindNumberOfRoutesWithExactlyStopsTest(final Parameters parameters) {
        this.parameters = parameters;
    }

    private static class Parameters {
        TownGraph townGraph;
        int from;
        int to;
        int stops;
        long expectedNumberOfRoutes;

        public Parameters townGraph(final TownGraph townGraph) {
            this.townGraph = townGraph;
            return this;
        }

        public Parameters from(final int from) {
            this.from = from;
            return this;
        }

        public Parameters to(final int to) {
            this.to = to;
            return this;
        }

        public Parameters stops(final int stops) {
            this.stops = stops;
            return this;
        }

        public Parameters expectedNumberOfRoutes(final long expectedNumberOfRoutes) {
            this.expectedNumberOfRoutes = expectedNumberOfRoutes;
            return this;
        }
    }

    @Test
    public void test() {
        long numberOfRoutes = Algorithms.findNumberOfRoutesWithExactlyStops(parameters.townGraph, parameters.from,
                parameters.to, parameters.stops);

        MatcherAssert.assertThat("Number of routes", numberOfRoutes, Matchers.is(parameters.expectedNumberOfRoutes));
    }
}
