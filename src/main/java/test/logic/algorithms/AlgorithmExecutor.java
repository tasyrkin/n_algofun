package test.logic.algorithms;

import test.domain.TownGraph;

import test.logic.VertexMapper;

public interface AlgorithmExecutor {

    String NO_ROUTE_FOUND = "NO SUCH ROUTE";

    String validateParametersAndExecute(TownGraph townGraph, VertexMapper mapper, String[] parameters);
}
