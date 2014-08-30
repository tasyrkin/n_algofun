package test.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.base.Optional;

import test.domain.TownGraph;
import test.domain.TownGraphEdge;

public class Algorithms {

    /**
     * @param   townGraph
     * @param   route
     *
     * @return
     */
    public static Optional<Integer> calculateDistanceForARoute(final TownGraph townGraph, final List<Integer> route) {
        if (townGraph == null || route == null || route.isEmpty()) {
            return Optional.absent();
        }

        final int[][] costMatrix = townGraph.getCostMatrix();

        int distance = 0;
        Integer prevVertex = null;
        for (Integer vertex : route) {
            if (prevVertex == null) {
                prevVertex = vertex;
            } else { }
        }

        return Optional.absent();
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
     * <p>Problem 5: the number of different routes from START vertex to FINISH vertex with a distance of less than
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
     * <p>The result is found by counting dp[START][w], where w < MAX_DISTANCE.
     *
     * <p>The worst case algorithm complexity is N^MAX_DISTANCE, where N - number of graph vertexes, the graph is
     * complete and the weight of each edge is 1.
     *
     * @return  number of routes, absent in case if no route is found
     */
    public static Optional<Integer> findNumberOfRoutesWithDistance(final TownGraph townGraph, final int start,
            final int finish, final int maxDistance) {

        int numberOfVertexes = townGraph.getVertexes().size();

        final Queue<NodeInQueue> queue = new LinkedList<>();

        queue.add(new NodeInQueue(finish, 0));

        final int[][] dp = new int[numberOfVertexes][maxDistance + 1];

        while (!queue.isEmpty()) {
            final NodeInQueue node = queue.poll();
            final List<TownGraphEdge> fromNeighbours = townGraph.getFromEdges(node.getVertex());

            //J-
            for (TownGraphEdge fromEdge : fromNeighbours) {
                NodeInQueue newNode = new NodeInQueue(fromEdge.getFrom(), node.getTotalDistanceToFinal() + fromEdge.getDistance());
                if (newNode.getTotalDistanceToFinal() <= maxDistance) {
                    dp[newNode.getVertex()][newNode.getTotalDistanceToFinal()] +=
                            dp[node.getVertex()][node.getTotalDistanceToFinal()] == 0
                                    ? 1 : dp[node.getVertex()][node.getTotalDistanceToFinal()];
                    queue.offer(newNode);
                }
            }
            //J+
        }

        int result = 0;

        for (int distance = 0; distance < maxDistance; distance++) {
            result += dp[start][distance];
        }

        return result != 0 ? Optional.of(result) : Optional.<Integer>absent();
    }

}
