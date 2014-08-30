package test.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

    private final Map<Integer, List<EndEdge>> graphData = new HashMap<>();
    private final Map<Integer, List<EndEdge>> revertedData;

    public Graph(final Map<Integer, List<EndEdge>> graphData) {

        for (Map.Entry<Integer, List<EndEdge>> entry : graphData.entrySet()) {
            this.graphData.put(entry.getKey(), entry.getValue());
        }

        revertedData = revertGraphData(graphData);
    }

    private static Map<Integer, List<EndEdge>> revertGraphData(final Map<Integer, List<EndEdge>> graphData) {

        final Map<Integer, List<EndEdge>> revertedData = new HashMap<>();

        for (Map.Entry<Integer, List<EndEdge>> entry : graphData.entrySet()) {
            int from = entry.getKey();
            for (EndEdge endEdge : entry.getValue()) {
                List<EndEdge> listToNodes = revertedData.get(endEdge.getVertex());
                if (listToNodes == null) {
                    listToNodes = new ArrayList<EndEdge>();
                }

                listToNodes.add(new EndEdge(from, endEdge.getCost()));

                revertedData.put(endEdge.getVertex(), listToNodes);
            }
        }

        return revertedData;
    }

    public List<EndEdge> getToNeighbours(final int from) {
        final List<EndEdge> list = graphData.get(from);

        if (list == null) {
            return new ArrayList<>();
        }

        return list;
    }

    public List<EndEdge> getFromNeighbours(final int to) {

        final List<EndEdge> list = revertedData.get(to);

        if (list == null) {
            return new ArrayList<>();
        }

        return list;
    }

    public Set<Integer> getVertexes() {
        final Set<Integer> result = new HashSet<>();

        for (Map.Entry<Integer, List<EndEdge>> entry : graphData.entrySet()) {
            result.add(entry.getKey());
            for (EndEdge endEdge : entry.getValue()) {
                result.add(endEdge.getVertex());
            }
        }

        return result;
    }

}
