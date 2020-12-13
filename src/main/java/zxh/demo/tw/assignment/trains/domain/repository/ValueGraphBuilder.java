package zxh.demo.tw.assignment.trains.domain.repository;

/**
 * ValueGraphBuilder:
 * @author zhangxuhai
 * @date 2020/12/13
*/
public interface ValueGraphBuilder<N, V> {
    ValueGraphBuilder<N, V> putEdgeValue(N nodeU, N nodeV, V value);

    ValueGraph<N, V> build();
}
