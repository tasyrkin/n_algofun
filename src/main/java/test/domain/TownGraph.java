package test.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;

/**
 * Represents a connected graph of towns with a positive distance between them. Every instance of the class is immutable
 */
public class TownGraph {

    private final Map<Integer, List<TownGraphEdge>> edgesIndex = new HashMap<>();
    private final Map<Integer, List<TownGraphEdge>> revertedEdgesIndex = new HashMap<>();

    public TownGraph(final List<TownGraphEdge> graphEdges) {

        if (graphEdges == null || graphEdges.isEmpty()) {
            throw new IllegalArgumentException("edges are missing");
        }

        for (TownGraphEdge edge : graphEdges) {
            indexGraphEdge(edgesIndex, edge);
            reverseIndexGraphEdge(revertedEdgesIndex, edge);
        }
    }

    private static void indexGraphEdge(final Map<Integer, List<TownGraphEdge>> index, final TownGraphEdge edge) {

        final List<TownGraphEdge> toNeighbours = Objects.firstNonNull(index.get(edge.getFrom()),
                new ArrayList<TownGraphEdge>());
        toNeighbours.add(edge);
        index.put(edge.getFrom(), toNeighbours);

        if (index.get(edge.getTo()) == null) {
            index.put(edge.getTo(), new ArrayList<TownGraphEdge>());
        }
    }

    private static void reverseIndexGraphEdge(final Map<Integer, List<TownGraphEdge>> index, final TownGraphEdge edge) {

        final List<TownGraphEdge> toNeighbours = Objects.firstNonNull(index.get(edge.getTo()),
                new ArrayList<TownGraphEdge>());
        toNeighbours.add(edge);
        index.put(edge.getTo(), toNeighbours);

        if (index.get(edge.getFrom()) == null) {
            index.put(edge.getFrom(), new ArrayList<TownGraphEdge>());
        }
    }

    public List<TownGraphEdge> getToEdges(final int from) {
        final List<TownGraphEdge> list = edgesIndex.get(from);

        if (list == null) {
            return new ArrayList<>();
        }

        return list;
    }

    public List<TownGraphEdge> getFromEdges(final int to) {

        final List<TownGraphEdge> list = revertedEdgesIndex.get(to);

        if (list == null) {
            return new ArrayList<>();
        }

        return list;
    }

    public Set<Integer> getVertexes() {
        final Set<Integer> result = new HashSet<>();

        for (Map.Entry<Integer, List<TownGraphEdge>> entry : edgesIndex.entrySet()) {
            result.add(entry.getKey());
            for (TownGraphEdge townGraphEdge : entry.getValue()) {
                result.add(townGraphEdge.getFrom());
            }
        }

        return result;
    }

    public int[][] getCostMatrix() {
        final Set<Integer> vertexes = getVertexes();
        int[][] costMatrix = new int[vertexes.size()][vertexes.size()];

        for (int fromVertex : vertexes) {
            for (TownGraphEdge toTownGraphEdge : getToEdges(fromVertex)) {
                costMatrix[fromVertex][toTownGraphEdge.getFrom()] = toTownGraphEdge.getDistance();
            }
        }

        return costMatrix;
    }

    public boolean isInGraph(final int vertex) {
        return vertex >= 0 && vertex < getVertexes().size();
    }

}
