package test.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.Test;

import com.google.common.collect.Lists;

import test.domain.EndEdge;
import test.domain.Graph;

public class AlgorithmsFindNumberOfRoutesWithDistanceTest {

    @Test
    public void testFromNokia() throws Exception {

        Map<Integer, List<EndEdge>> graphData = new HashMap<>();

        graphData.put(0, Lists.newArrayList(new EndEdge(1, 5), new EndEdge(3, 5), new EndEdge(4, 7)));
        graphData.put(1, Lists.newArrayList(new EndEdge(2, 4)));
        graphData.put(2, Lists.newArrayList(new EndEdge(3, 8), new EndEdge(4, 2)));
        graphData.put(3, Lists.newArrayList(new EndEdge(2, 8), new EndEdge(4, 6)));
        graphData.put(4, Lists.newArrayList(new EndEdge(1, 3)));

        Graph graph = new Graph(graphData);

        int result = Algorithms.findNumberOfRoutesWithDistance(graph, 2, 2, 30);

        MatcherAssert.assertThat("number of ", result, Matchers.is(7));

    }

    @Test
    public void testThreeVertices() throws Exception {
        Map<Integer, List<EndEdge>> graphData = new HashMap<>();

        graphData.put(0, Lists.newArrayList(new EndEdge(2, 1)));
        graphData.put(1, Lists.newArrayList(new EndEdge(0, 2), new EndEdge(2, 3)));

        Graph graph = new Graph(graphData);

        int result = Algorithms.findNumberOfRoutesWithDistance(graph, 1, 2, 4);

        MatcherAssert.assertThat("number of ", result, Matchers.is(2));

    }

    @Test
    public void testTwoVertexesWithCycle() throws Exception {
        Map<Integer, List<EndEdge>> graphData = new HashMap<>();

        graphData.put(0, Lists.newArrayList(new EndEdge(1, 1)));
        graphData.put(1, Lists.newArrayList(new EndEdge(0, 1)));

        Graph graph = new Graph(graphData);

        int result = Algorithms.findNumberOfRoutesWithDistance(graph, 0, 1, 4);

        MatcherAssert.assertThat("number of ", result, Matchers.is(2));

    }
}
