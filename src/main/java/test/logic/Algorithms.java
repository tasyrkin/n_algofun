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
     * <p>Problem 5: the number of different routes from START to FINISH vertex with a distance of less than X.
     *
     * <p>asdad
     */
    public static int findNumberOfRoutesWithDistance(final Graph graph, final int from, final int to,
            final int maxDistance) {

        final Queue<NodeInQueue> queue = new LinkedList<NodeInQueue>();

        queue.add(new NodeInQueue(to, 0));

        final int[][] dp = new int[graph.getVertexes().size()][maxDistance + 1];

        while (!queue.isEmpty()) {
            final NodeInQueue node = queue.poll();
            final List<EndEdge> fromNeighbours = graph.getFromNeighbours(node.getVertex());

            for (EndEdge endEdge : fromNeighbours) {
                if (endEdge.getCost() + node.getDistance() <= maxDistance) {
                    NodeInQueue newNode = new NodeInQueue(endEdge.getVertex(), endEdge.getCost() + node.getDistance());
                    dp[newNode.getVertex()][newNode.getDistance()] +=
                        dp[node.getVertex()][node.getDistance()] == 0 && node.getVertex() == to
                        ? 1 : dp[node.getVertex()][node.getDistance()];
                    queue.offer(newNode);
                }
            }
        }

        int result = 0;

        for (int distance = 0; distance < maxDistance; distance++) {
            result += dp[from][distance];
        }

        return result;
    }

}
