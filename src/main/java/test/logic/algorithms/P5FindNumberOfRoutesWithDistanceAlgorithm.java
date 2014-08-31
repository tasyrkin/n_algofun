package test.logic.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import test.domain.INPUT_PROBLEM;
import test.domain.TownGraph;
import test.domain.TownGraphEdge;

import test.logic.VertexMapper;

/**
 * <p>Problem 5: find the number of different routes from START vertex to FINISH vertex with a distance of less than
 * MAX_DISTANCE.
 *
 * <p>This problem is NP-hard since it is possible to make a reduction from longest path problem to it in polynomial
 * time.
 *
 * <p>The solution is based on dynamic programming where the state is dp[current_vertex][w] - number of paths to FINISH
 * vertex from current_vertex with path weight less than or equal to w.
 *
 * <p>Algorithm starts from FINISH vertex and traverses all paths with breadth-first search until the path weight is
 * less or equal than MAX_DISTANCE.
 *
 * <p>The result is found by summing up dp[START][w], where w < MAX_DISTANCE.
 *
 * <p>The worst case time complexity is V^MAX_DISTANCE, where V - number of graph vertexes, the graph is complete and
 * the weight of each edge is 1.
 *
 * @return  number of routes, absent in case if no route is found
 */
public class P5FindNumberOfRoutesWithDistanceAlgorithm implements AlgorithmExecutor {
    @Override
    public String validateParametersAndExecute(final TownGraph townGraph, final VertexMapper mapper,
            final String[] parameters) {
        Preconditions.checkArgument(parameters != null && parameters.length == 3,
            "Expected input: P5 X Y N - problem 5: find the number of different routes from START vertex to FINISH vertex with a distance of less than MAX_DISTANCE."
                + "X - START vertex, Y - FINISH vertex, N - short integer representing MAX_DISTANCE");
        Preconditions.checkArgument(INPUT_PROBLEM.valueOf(parameters[0]) == INPUT_PROBLEM.P5,
            "Wrong parameters, expected P5, found [%s]", parameters[0]);

        char from = parameters[1].charAt(0);
        char to = parameters[2].charAt(0);
        short maxDistanceExclusive = Short.parseShort(parameters[3]);

        Optional<Integer> result = algorithm(townGraph, mapper.id(from), mapper.id(to), maxDistanceExclusive);

        return result.isPresent() ? String.valueOf(result) : NO_ROUTE_FOUND;
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

    public Optional<Integer> algorithm(final TownGraph townGraph, final int start, final int finish,
            final int maxDistanceExclusive) {

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
