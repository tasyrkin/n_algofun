package test.logic.algorithms;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import test.domain.INPUT_PROBLEM;
import test.domain.TownGraph;

import test.logic.VertexMapper;

/**
 * <p>Problem 1: find the distance along the given route.
 *
 * <p>The solution is linear traversal through the adjacency matrix and summing up the met values. In case if the
 * distance is 0, then the route does not exist.
 *
 * <p>The time complexity: O(E+K) where E - number of graph edges (for building adjacency matrix), K - number of
 * vertexes in the route.
 *
 * <p>The space complexity: O(V^2) where V - number of graph vertexes.
 */
public class P1CalculateDistanceForRouteAlgorithm implements AlgorithmExecutor {

    @Override
    public String validateParametersAndExecute(final TownGraph townGraph, final VertexMapper mapper,
            final String[] parameters) {

        Preconditions.checkArgument(parameters != null && parameters.length >= 1,
            "Expected input: P1 X1 X2 X3 - find the distance along the given route. X1 - first vertex, X2 - second vertex, ...");
        Preconditions.checkArgument(INPUT_PROBLEM.valueOf(parameters[0]) == INPUT_PROBLEM.P1,
            "Wrong parameters, expected P1, found [%s]", parameters[0]);

        List<Integer> route = Lists.newArrayList();
        for (int i = 1; i < parameters.length; i++) {
            Preconditions.checkArgument(parameters[i] != null && parameters[i].length() == 1,
                "Expected single character as vertex");

            char vertex = parameters[i].charAt(0);
            route.add(mapper.id(vertex));
        }

        Optional<Integer> result = algorithm(townGraph, route);

        return result.isPresent() ? String.valueOf(result.get()) : NO_ROUTE_FOUND;
    }

    public Optional<Integer> algorithm(final TownGraph townGraph, final List<Integer> route) {
        if (townGraph == null || route == null || route.isEmpty()) {
            return Optional.absent();
        }

        final int[][] adjacencyMatrix = townGraph.getAdjacencyMatrix();

        int distance = 0;
        Integer prevVertex = null;
        for (Integer currentVertex : route) {
            if (prevVertex != null) {
                if (adjacencyMatrix[prevVertex][currentVertex] == 0) {
                    return Optional.absent();
                }

                distance += adjacencyMatrix[prevVertex][currentVertex];
            }

            prevVertex = currentVertex;
        }

        return optionalOrAbsentIfZero(distance);
    }

    private static Optional<Integer> optionalOrAbsentIfZero(final int result) {
        return result != 0 ? Optional.of(result) : Optional.<Integer>absent();
    }

}
