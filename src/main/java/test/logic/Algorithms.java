package test.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import test.domain.TownGraph;
import test.domain.TownGraphEdge;

public class Algorithms {

    /**
     * <p>Problem 1: find the distance along the given route.
     *
     * <p>The solution is linear traversal through the adjacency matrix and summing up the met values. In case if the
     * distance is 0, then the route does not exist.
     *
     * <p>The time complexity: O(E+K) where M - number of graph edges (for building adjacency matrix), K - number of
     * vertexes in the route.
     *
     * <p>The space complexity: O(V^2) where V - number of graph vertexes.
     */
    public static Optional<Integer> calculateDistanceForRoute(final TownGraph townGraph, final List<Integer> route) {
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

    /**
     * <p>Problem 2: find the number of routes from START vertex to FINISH vertex with a less or equal number of STOPS.
     *
     * <p>The solution is based on raising graph's connectivity matrix to the power of STOPS.
     *
     * <p>The result is found by summing up the intermediate result of matrix exponentiation for every STOP value:
     * resultPowK[START][FINISH].
     *
     * <p>The time complexity is O(V^3*STOPS), where V - number of graph vertexes.
     *
     * <p>The space complexity is O(V^2), where N - number of graph vertexes.
     *
     * @return  number of routes
     */

    public static long findNumberOfRoutesWithLessThanAndExactlyStops(final TownGraph townGraph, final int from,
            final int to, final int maxStopsInclusive) {
        Preconditions.checkArgument(maxStopsInclusive >= 0, "Number of stops must be non negative, was [%s]",
            maxStopsInclusive);

        long[][] connectivityMatrix = townGraph.getConnectivityMatrix();

        long[][] result = identityMatrix(connectivityMatrix.length, connectivityMatrix[0].length);

        long numberOfRoutes = 0;
        for (int i = 0; i < maxStopsInclusive; i++) {
            result = multiplyMatrices(result, connectivityMatrix);
            numberOfRoutes += result[from][to];
        }

        return numberOfRoutes;
    }

    /**
     * <p>Problem 3: find the number of routes from START vertex to FINISH vertex with a exactly number of STOPS.
     *
     * <p>The solution is based on raising graph's connectivity matrix to the power of STOPS.
     *
     * <p>The result is found by taking result[START][FINISH].
     *
     * <p>The time complexity is O(V^3*LOG(STOPS)), where V - number of graph vertexes. LOG(STOPS) is achieved by
     * exponentiating the matrix using efficient binary power algorithm.
     *
     * <p>The space complexity is O(V^2), where V - number of graph vertexes.
     *
     * @return  number of routes
     */
    public static long findNumberOfRoutesWithExactlyStops(final TownGraph townGraph, final int from, final int to,
            final int stops) {

        Preconditions.checkArgument(stops >= 0, "Number of stops must be non negative, was [%s]", stops);

        int multiplyTimes = stops;

        long[][] connectivityMatrix = townGraph.getConnectivityMatrix();

        long[][] result = identityMatrix(connectivityMatrix.length, connectivityMatrix[0].length);

        while (multiplyTimes > 0) {
            if (multiplyTimes % 2 == 1) {
                result = multiplyMatrices(result, connectivityMatrix);
                multiplyTimes--;
            } else {
                connectivityMatrix = multiplyMatrices(connectivityMatrix, connectivityMatrix);
                multiplyTimes >>= 1;
            }
        }

        return result[from][to];
    }

    @VisibleForTesting
    static long[][] multiplyMatrices(final long[][] matrixOne, final long[][] matrixTwo) {

        //J-
        Preconditions.checkArgument(
                matrixOne[0].length == matrixTwo.length,
                "Dimensions of the multiplied matrices are wrong, matrixOne [%sx%s], matrixTwo [%sx%s]",
                matrixOne.length, matrixOne[0].length,
                matrixTwo.length, matrixTwo[0].length
        );
        //J+

        long[][] result = new long[matrixOne.length][matrixTwo[0].length];
        for (int i = 0; i < matrixOne.length; i++) {
            for (int j = 0; j < matrixTwo[0].length; j++) {
                int value = 0;
                for (int k = 0; k < matrixOne[0].length; k++) {
                    value += matrixOne[i][k] * matrixTwo[k][j];
                }

                result[i][j] = value;
            }
        }

        return result;
    }

    private static long[][] identityMatrix(final int rows, final int cols) {
        long[][] result = new long[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                result[i][j] = i == j ? 1 : 0;
            }

        }

        return result;
    }

    /**
     * <p>Problem 4: find the shortest distance from START vertex to FINISH vertex with at least one stop.
     *
     * <p>The solution is based on Dijkstra's shortest path algorithm, with modification for the case when START ==
     * FINISH
     *
     * <p>The time complexity is O(V^2*E), where V - number of graph vertexes. E - number graph edges. This is naive
     * implementation of Dijkstra's algorithm, which may be further improved to reach O(E + V*LOG(V)) with Fibonacci
     * Heap.
     *
     * <p>The space complexity is O(V), where V - number of graph vertexes.
     *
     * @return  number of routes
     */
    public static Optional<Long> findShortestDistance(final TownGraph townGraph, final int from, final int to) {

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

    private static class NodeInQueue {
        private int vertex;
        private int totalDistanceToFinal;

        private NodeInQueue(final int vertex, final int totalDistanceToFinal) {
            this.vertex = vertex;
            this.totalDistanceToFinal = totalDistanceToFinal;
        }

        public int getVertex() {
            return vertex;
        }

        public int getTotalDistanceToFinal() {
            return totalDistanceToFinal;
        }
    }

    /**
     * <p>Problem 5: find the number of different routes from START vertex to FINISH vertex with a distance of less than
     * MAX_DISTANCE.
     *
     * <p>This problem is NP-hard since it is possible to make a reduction from longest path problem to it in polynomial
     * time.
     *
     * <p>The solution is based on dynamic programming where the state is dp[current_vertex][w] - number of paths to
     * FINISH vertex from current_vertex with path weight less than or equal to w.
     *
     * <p>Algorithm starts from FINISH vertex and traverses all paths with breadth-first search until the path weight is
     * less or equal than MAX_DISTANCE.
     *
     * <p>The result is found by summing up dp[START][w], where w < MAX_DISTANCE.
     *
     * <p>The worst case time complexity is V^MAX_DISTANCE, where V - number of graph vertexes, the graph is complete
     * and the weight of each edge is 1.
     *
     * @return  number of routes, absent in case if no route is found
     */
    public static Optional<Integer> findNumberOfRoutesWithDistance(final TownGraph townGraph, final int start,
            final int finish, final int maxDistanceExclusive) {

        Preconditions.checkArgument(maxDistanceExclusive >= 0, "Max distance must be non negative, was [%s]",
            maxDistanceExclusive);

        int numberOfVertexes = townGraph.getVertexes().size();

        final Queue<NodeInQueue> queue = new LinkedList<>();

        queue.add(new NodeInQueue(finish, 0));

        final int[][] dp = new int[numberOfVertexes][maxDistanceExclusive + 1];

        while (!queue.isEmpty()) {
            final NodeInQueue node = queue.poll();
            final List<TownGraphEdge> fromNeighbours = townGraph.getFromEdges(node.getVertex());

            //J-
            for (TownGraphEdge fromEdge : fromNeighbours) {
                NodeInQueue newNode = new NodeInQueue(fromEdge.getFrom(), node.getTotalDistanceToFinal() + fromEdge.getDistance());
                if (newNode.getTotalDistanceToFinal() <= maxDistanceExclusive) {
                    dp[newNode.getVertex()][newNode.getTotalDistanceToFinal()] +=
                            dp[node.getVertex()][node.getTotalDistanceToFinal()] == 0
                                    ? 1 : dp[node.getVertex()][node.getTotalDistanceToFinal()];
                    queue.offer(newNode);
                }
            }
            //J+
        }

        int result = 0;

        for (int distance = 0; distance < maxDistanceExclusive; distance++) {
            result += dp[start][distance];
        }

        return optionalOrAbsentIfZero(result);
    }

    private static Optional<Integer> optionalOrAbsentIfZero(final int result) {
        return result != 0 ? Optional.of(result) : Optional.<Integer>absent();
    }

}
