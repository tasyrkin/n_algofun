package test.logic.algorithms;

import test.domain.INPUT_PROBLEM;

/**
 * The method factory hides the creation of the AlgorithmExecutor object for appropriate problem. Usually done using
 * spring configuration so that the class satisfies open closed principle: open for extension and closed modification
 */
public class AlgorithmExecutorFactory {

    public AlgorithmExecutor getAlgorithmExecutor(final INPUT_PROBLEM inputProblem) {

        switch (inputProblem) {

            case P1 :
                return new P1CalculateDistanceForRouteAlgorithm();

            case P2 :
                return new P2FindNumberOfRoutesWithLessThanAndExactlyStopsAlgorithm();

            case P3 :
                return new P3FindNumberOfRoutesWithExactlyStopsAlgorithm();

            case P4 :
                return new P4FindShortestDistanceAlgorithm();

            case P5 :
                return new P5FindNumberOfRoutesWithDistanceAlgorithm();

            default :
                throw new IllegalArgumentException(String.format("Unknown algorithm executor for query [%s]",
                        inputProblem));
        }

    }

}
