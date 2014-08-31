package test.logic.algorithms;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import test.domain.INPUT_PROBLEM;
import test.domain.TownGraph;
import test.domain.TownGraphEdge;

import test.logic.VertexMapper;

/**
 * <p>Problem 4: find the shortest distance from START vertex to FINISH vertex with at least one stop.
 *
 * <p>The solution is based on Dijkstra's shortest path algorithm, with modification for the case when START == FINISH
 *
 * <p>The time complexity is O(V^2*E), where V - number of graph vertexes. E - number graph edges. This is naive
 * implementation of Dijkstra's algorithm, which may be further improved to reach O(E + V*LOG(V)) with Fibonacci Heap.
 *
 * <p>The space complexity is O(V), where V - number of graph vertexes.
 *
 * @return  number of routes
 */
public class P4FindShortestDistanceAlgorithm implements AlgorithmExecutor {

    @Override
    public String validateParametersAndExecute(final TownGraph townGraph, final VertexMapper mapper,
            final String[] parameters) {
        Preconditions.checkArgument(parameters != null && parameters.length == 3,
            "Expected input: P4 X Y - problem 4: find the shortest distance from START vertex to FINISH vertex with at least one stop. X - START vertex, Y - FINISH vertex");
        Preconditions.checkArgument(INPUT_PROBLEM.valueOf(parameters[0]) == INPUT_PROBLEM.P4,
            "Wrong parameters, expected P4, found [%s]", parameters[0]);

        char from = parameters[1].charAt(0);
        char to = parameters[2].charAt(0);

        Optional<Long> result = algorithm(townGraph, mapper.id(from), mapper.id(to));

        return result.isPresent() ? String.valueOf(result.get()) : NO_ROUTE_FOUND;
    }

    public Optional<Long> algorithm(final TownGraph townGraph, final int from, final int to) {

        long[] shortestDistance = new long[townGraph.getVertexes().size()];
        for (int i = 0; i < shortestDistance.length; i++) {
            shortestDistance[i] = Long.MAX_VALUE;
        }

        shortestDistance[from] = 0;

        final Set<Integer> visitedVertexes = Sets.newHashSet(from);
        final Set<Integer> remainingVertexes = Sets.newHashSet(townGraph.getVertexes());
        remainingVertexes.remove(from);
        while (!remainingVertexes.isEmpty()) {
            Integer remainingVertexWithMinimalDistance = null;
            long minimalDistance = Long.MAX_VALUE;
            for (Integer remainingVertex : remainingVertexes) {
                if (remainingVertexWithMinimalDistance == null) {
                    remainingVertexWithMinimalDistance = remainingVertex;
                }

                List<TownGraphEdge> edges = townGraph.getFromEdges(remainingVertex);
                for (TownGraphEdge edge : edges) {
                    if (visitedVertexes.contains(edge.getFrom())) {
                        long currentDistance = sumConsideringInf(shortestDistance[edge.getFrom()], edge.getDistance());
                        if (shortestDistance[edge.getTo()] > currentDistance) {
                            shortestDistance[edge.getTo()] = currentDistance;
                        }

                        if (shortestDistance[edge.getTo()] < minimalDistance) {
                            remainingVertexWithMinimalDistance = edge.getTo();
                            minimalDistance = shortestDistance[edge.getTo()];
                        }
                    }
                }
            }

            visitedVertexes.add(remainingVertexWithMinimalDistance);
            remainingVertexes.remove(remainingVertexWithMinimalDistance);
        }

        if (from == to) {
            List<TownGraphEdge> edges = townGraph.getFromEdges(to);
            shortestDistance[to] = Long.MAX_VALUE;
            for (TownGraphEdge edge : edges) {
                long currentDistance = sumConsideringInf(shortestDistance[edge.getFrom()], edge.getDistance());
                if (shortestDistance[edge.getTo()] > currentDistance) {
                    shortestDistance[edge.getTo()] = currentDistance;
                }
            }
        }

        return shortestDistance[to] == Long.MAX_VALUE ? Optional.<Long>absent() : Optional.of(shortestDistance[to]);
    }

    private static long sumConsideringInf(final long value, final int distance) {
        return value == Long.MAX_VALUE ? Long.MAX_VALUE : value + distance;
    }

}
