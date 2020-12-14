package zxh.demo.tw.assignment.trains.adapter.outbound;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraph;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraphBuilder;

class GraphBuilderTest {
    @Test
    void should_create_graph_with_builder() {
        // given
        ValueGraphBuilder<String, Integer> builder = new GraphBuilder<String, Integer>()
                .putEdgeValue("A", "B", 1)
                .putEdgeValue("B", "C", 2)
                .putEdgeValue("C", "D", 3)
                .putEdgeValue("D", "C", 4)
                .putEdgeValue("D", "E", 5)
                .putEdgeValue("A", "D", 6)
                .putEdgeValue("C", "E", 7)
                .putEdgeValue("E", "B", 8)
                .putEdgeValue("A", "E", 9);

        // when
        ValueGraph<String, Integer> graph = builder.build();

        // then
        assertThat(graph.successors("A"), containsInAnyOrder("B", "E", "D"));
        assertThat(graph.successors("B"), containsInAnyOrder("C"));
        assertThat(graph.successors("C"), containsInAnyOrder("D", "E"));
        assertThat(graph.successors("D"), containsInAnyOrder("C", "E"));
        assertThat(graph.successors("E"), containsInAnyOrder("B"));

        assertThat(graph.edgeValue("A", "B").get(), is(1));
        assertThat(graph.edgeValue("B", "C").get(), is(2));
        assertThat(graph.edgeValue("C", "D").get(), is(3));
        assertThat(graph.edgeValue("D", "C").get(), is(4));
        assertThat(graph.edgeValue("D", "E").get(), is(5));
        assertThat(graph.edgeValue("A", "D").get(), is(6));
        assertThat(graph.edgeValue("C", "E").get(), is(7));
        assertThat(graph.edgeValue("E", "B").get(), is(8));
        assertThat(graph.edgeValue("A", "E").get(), is(9));

        assertFalse(graph.edgeValue("A", "C").isPresent());
        assertFalse(graph.edgeValue("E", "D").isPresent());
        assertFalse(graph.edgeValue("B", "E").isPresent());
    }
}