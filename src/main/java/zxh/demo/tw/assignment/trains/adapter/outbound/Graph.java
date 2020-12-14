package zxh.demo.tw.assignment.trains.adapter.outbound;

import static java.util.Objects.*;

import zxh.demo.tw.assignment.trains.domain.repository.ValueGraph;
import java.util.*;

/**
 * Graph:
 * @author zhangxuhai
 * @date 2020/12/14
*/
public class Graph<N, V> implements ValueGraph<N, V> {
    private final Map<N, Map<N, V>> nodeTable = new HashMap<>();

    @Override
    public Set<N> successors(N node) {
        Map<N, V> connections = nodeTable.get(node);
        if (isNull(connections)) {
            return Collections.emptySet();
        }
        return Set.copyOf(connections.keySet());
    }

    @Override
    public Optional<V> edgeValue(N nodeU, N nodeV) {
        Map<N, V> connections = nodeTable.get(nodeU);
        if (isNull(connections)) {
            return Optional.empty();
        }

        return Optional.ofNullable(connections.get(nodeV));
    }

    void putEdgeValue(N startNode, N endNode, V value) {
        Map<N, V> connections = nodeTable.get(startNode);
        if (isNull(connections)) {
            connections = new HashMap<>();
        }
        connections.put(endNode, value);
        nodeTable.put(startNode, connections);
    }
}
