package zxh.demo.tw.assignment.trains.domain;


import zxh.demo.tw.assignment.trains.domain.repository.ValueGraph;
import java.util.Optional;
import java.util.Set;

/**
 * FakeValueGraph:
 * @author zhangxuhai
 * @date 2020/12/13
*/
public class FakeValueGraph<N, V> implements ValueGraph<N, V> {
    private final com.google.common.graph.ValueGraph<N, V> valueGraph;

    public FakeValueGraph(com.google.common.graph.ValueGraph<N, V> valueGraph) {
        this.valueGraph = valueGraph;
    }

    @Override
    public Set<N> successors(N node) {
        return valueGraph.successors(node);
    }

    @Override
    public Optional<V> edgeValue(N nodeU, N nodeV) {
        return valueGraph.edgeValue(nodeU, nodeV);
    }
}
