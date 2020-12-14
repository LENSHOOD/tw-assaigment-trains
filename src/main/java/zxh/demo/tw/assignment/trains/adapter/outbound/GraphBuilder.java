package zxh.demo.tw.assignment.trains.adapter.outbound;

import zxh.demo.tw.assignment.trains.domain.repository.ValueGraph;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraphBuilder;

/**
 * GraphBuilder:
 * @author zhangxuhai
 * @date 2020/12/14
*/
public class GraphBuilder<N, V> implements ValueGraphBuilder<N, V> {
    private final Graph<N, V> graph = new Graph<>();

    @Override
    public ValueGraphBuilder<N, V> putEdgeValue(N nodeU, N nodeV, V value) {
        graph.putEdgeValue(nodeU, nodeV, value);
        return this;
    }

    @Override
    public ValueGraph<N, V> build() {
        return graph;
    }
}
