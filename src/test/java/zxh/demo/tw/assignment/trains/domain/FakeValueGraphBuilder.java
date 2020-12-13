package zxh.demo.tw.assignment.trains.domain;


import com.google.common.graph.ImmutableValueGraph;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraph;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraphBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;

/**
 * FakeValueGraph:
 * @author zhangxuhai
 * @date 2020/12/13
*/
public class FakeValueGraphBuilder<N, V> implements ValueGraphBuilder<N, V> {
    private final ImmutableValueGraph.Builder<N, V> valueGraphBuilder;

    public FakeValueGraphBuilder() {
        this.valueGraphBuilder = com.google.common.graph.ValueGraphBuilder.directed().immutable();
    }

    @Override
    public ValueGraphBuilder<N, V> putEdgeValue(N nodeU, N nodeV, V value) {
        valueGraphBuilder.putEdgeValue(nodeU, nodeV, value);
        return this;
    }

    @Override
    public ValueGraph<N, V> build() {
        return new FakeValueGraph<>(valueGraphBuilder.build());
    }
}
