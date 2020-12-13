package zxh.demo.tw.assignment.trains.domain.repository;

import java.util.Optional;
import java.util.Set;

/**
 * ValueGraph:
 * @author zhangxuhai
 * @date 2020/12/13
*/
public interface ValueGraph<N, V> {
    Set<N> successors(N node);

    Optional<V> edgeValue(N nodeU, N nodeV);
}
