package test.logic;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import test.domain.TownGraph;
import test.domain.TownGraphEdgeBuilder;

@RunWith(Parameterized.class)
public class AlgorithmsFindShortestPathTest {

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
        final TownGraph twoVertexGraphWithCycle = new TownGraph(
                Lists.newArrayList(
                    new TownGraphEdgeBuilder().from(0).to(1).distance(Integer.MAX_VALUE).build(),
                    new TownGraphEdgeBuilder().from(1).to(0).distance(Integer.MAX_VALUE).build()
                )
        );

        final Object[][] data = new Object[][] {
                {
                    new Parameters()
                        .townGraph(nokiaTestTownGraph)
                        .from(0)
                        .to(2)
                        .expectedShortestDistance(Optional.of(9l))
                },{
                    new Parameters()
                        .townGraph(nokiaTestTownGraph)
                        .from(1)
                        .to(1)
                        .expectedShortestDistance(Optional.of(9l))
                },{
                    new Parameters()
                        .townGraph(nokiaTestTownGraph)
                        .from(3)
                        .to(0)
                        .expectedShortestDistance(Optional.<Long>absent())
                },{
                    new Parameters()
                        .townGraph(nokiaTestTownGraph)
                        .from(0)
                        .to(0)
                        .expectedShortestDistance(Optional.<Long>absent())
                },{
                    new Parameters()
                        .townGraph(nokiaTestTownGraph)
                        .from(2)
                        .to(2)
                        .expectedShortestDistance(Optional.of(9l))
                },{
                    new Parameters()
                        .townGraph(twoVertexGraphWithCycle)
                        .from(0)
                        .to(0)
                        .expectedShortestDistance(Optional.of((long)Integer.MAX_VALUE+(long)Integer.MAX_VALUE))
                }
        };
        //J+

        return Arrays.asList(data);
    }

    private static class Parameters {
        TownGraph townGraph;
        int from;
        int to;
        Optional<Long> expectedShortestDistance;

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

        public Parameters expectedShortestDistance(final Optional<Long> expectedShortestDistance) {
            this.expectedShortestDistance = expectedShortestDistance;
            return this;
        }
    }

    public AlgorithmsFindShortestPathTest(final Parameters parameters) {
        this.parameters = parameters;
    }

    @Test
    public void test() {
        //J-
        Optional<Long> shortestDistance = Algorithms.findShortestDistance(parameters.townGraph, parameters.from, parameters.to);
        MatcherAssert.assertThat("shortest distance", shortestDistance, Matchers.is(parameters.expectedShortestDistance));
        //J+
    }
}
