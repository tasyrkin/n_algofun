package test.logic.algorithms;

import com.google.common.base.Preconditions;

import test.domain.INPUT_PROBLEM;
import test.domain.TownGraph;

import test.logic.VertexMapper;

import test.logic.utils.MatrixUtils;

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
public class P2FindNumberOfRoutesWithLessThanAndExactlyStopsAlgorithm implements AlgorithmExecutor {
    @Override
    public String validateParametersAndExecute(final TownGraph townGraph, final VertexMapper mapper,
            final String[] parameters) {

        Preconditions.checkArgument(parameters != null && parameters.length == 4,
            "Expected input: P2 X Y N - find the number of routes from START vertex to FINISH vertex with a less or equal number of STOPS. X - START vertex, Y - FINISH vertex, N - number of STOPS to consider");
        Preconditions.checkArgument(INPUT_PROBLEM.valueOf(parameters[0]) == INPUT_PROBLEM.P2,
            "Wrong parameters, expected P2, found [%s]", parameters[0]);

        char from = parameters[1].charAt(0);
        char to = parameters[2].charAt(0);
        int maxStopsInclusive = Integer.parseInt(parameters[3]);

        long result = findNumberOfRoutesWithLessThanAndExactlyStops(townGraph, mapper.id(from), mapper.id(to),
                maxStopsInclusive);

        return String.valueOf(result);
    }

    public long findNumberOfRoutesWithLessThanAndExactlyStops(final TownGraph townGraph, final int from, final int to,
            final int maxStopsInclusive) {
        Preconditions.checkArgument(maxStopsInclusive >= 0, "Number of stops must be non negative, was [%s]",
            maxStopsInclusive);

        long[][] connectivityMatrix = townGraph.getConnectivityMatrix();

        long[][] result = MatrixUtils.identityMatrix(connectivityMatrix.length, connectivityMatrix[0].length);

        long numberOfRoutes = 0;
        for (int i = 0; i < maxStopsInclusive; i++) {
            result = MatrixUtils.multiplyMatrices(result, connectivityMatrix);
            numberOfRoutes += result[from][to];
        }

        return numberOfRoutes;
    }

}
