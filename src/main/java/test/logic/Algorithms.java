package test.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import test.domain.EndEdge;
import test.domain.Graph;

public class Algorithms {

    private static class NodeInQueue {
        private int vertex;
        private int distance;

        private NodeInQueue(final int vertex, final int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public int getVertex() {
            return vertex;
        }

        public int getDistance() {
            return distance;
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
     * <p>The result is found by counting dp[START][w], where w < MAX_DISTANCE
     */
    public static int findNumberOfRoutesWithDistance(final Graph graph, final int start, final int finish,
            final int maxDistance) {

        final Queue<NodeInQueue> queue = new LinkedList<NodeInQueue>();

        queue.add(new NodeInQueue(finish, 0));

        final int[][] dp = new int[graph.getVertexes().size()][maxDistance + 1];

        while (!queue.isEmpty()) {
            final NodeInQueue node = queue.poll();
            final List<EndEdge> fromNeighbours = graph.getFromNeighbours(node.getVertex());

            for (EndEdge endEdge : fromNeighbours) {
                if (endEdge.getCost() + node.getDistance() <= maxDistance) {
                    NodeInQueue newNode = new NodeInQueue(endEdge.getVertex(), endEdge.getCost() + node.getDistance());
                    dp[newNode.getVertex()][newNode.getDistance()] +=
                        dp[node.getVertex()][node.getDistance()] == 0 && node.getVertex() == finish
                        ? 1 : dp[node.getVertex()][node.getDistance()];
                    queue.offer(newNode);
                }
            }
        }

        int result = 0;

        for (int distance = 0; distance < maxDistance; distance++) {
            result += dp[start][distance];
        }

        return result;
    }

}
