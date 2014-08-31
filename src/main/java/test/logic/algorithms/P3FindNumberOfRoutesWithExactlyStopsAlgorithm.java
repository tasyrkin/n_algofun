package test.logic.algorithms;

import com.google.common.base.Preconditions;

import test.domain.INPUT_PROBLEM;
import test.domain.TownGraph;

import test.logic.VertexMapper;

import test.logic.utils.MatrixUtils;

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
public class P3FindNumberOfRoutesWithExactlyStopsAlgorithm implements AlgorithmExecutor {
    @Override
    public String validateParametersAndExecute(final TownGraph townGraph, final VertexMapper mapper,
            final String[] parameters) {

        Preconditions.checkArgument(parameters != null && parameters.length == 4,
            "Expected input: P3 X Y N - problem 3: find the number of routes from START vertex to FINISH vertex with a exactly number of STOPS. X - START vertex, Y - FINISH vertex, N - number of STOPS to consider");
        Preconditions.checkArgument(INPUT_PROBLEM.valueOf(parameters[0]) == INPUT_PROBLEM.P3,
            "Wrong parameters, expected P3, found [%s]", parameters[0]);

        char from = parameters[1].charAt(0);
        char to = parameters[2].charAt(0);
        int stops = Integer.parseInt(parameters[3]);

        long result = algorithm(townGraph, mapper.id(from), mapper.id(to), stops);

        return String.valueOf(result);
    }

    public long algorithm(final TownGraph townGraph, final int from, final int to, final int stops) {

        Preconditions.checkArgument(stops >= 0, "Number of stops must be non negative, was [%s]", stops);

        int multiplyTimes = stops;

        long[][] connectivityMatrix = townGraph.getConnectivityMatrix();

        long[][] result = MatrixUtils.identityMatrix(connectivityMatrix.length, connectivityMatrix[0].length);

        while (multiplyTimes > 0) {
            if (multiplyTimes % 2 == 1) {
                result = MatrixUtils.multiplyMatrices(result, connectivityMatrix);
                multiplyTimes--;
            } else {
                connectivityMatrix = MatrixUtils.multiplyMatrices(connectivityMatrix, connectivityMatrix);
                multiplyTimes >>= 1;
            }
        }

        return result[from][to];
    }

}
