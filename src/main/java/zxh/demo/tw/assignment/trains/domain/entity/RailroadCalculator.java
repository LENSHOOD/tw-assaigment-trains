package zxh.demo.tw.assignment.trains.domain.entity;

import static zxh.demo.tw.assignment.trains.domain.util.NullChecker.*;

import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import zxh.demo.tw.assignment.trains.domain.util.NullChecker;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;

public class RailroadCalculator {
    private ValueGraph<Station, Distance> railroadGraph;

    private RailroadCalculator(ValueGraph<Station, Distance> railroadGraph) {
        this.railroadGraph = railroadGraph;
    }

    public static RailroadCalculatorBuilder builder() {
        return new RailroadCalculatorBuilder();
    }

    public List<Road> getPossibleRoads(Station startStation, Station endStation) {
        return null;
    }

    public static class RailroadCalculatorBuilder {
        private ImmutableValueGraph.Builder<Station, Distance> railroadGraphBuilder = ValueGraphBuilder.directed().immutable();

        public RailroadCalculatorBuilder addRailroad(Station from, Station to, Distance distance) {
            railroadGraphBuilder.putEdgeValue(requireNonNull(from), requireNonNull(to), requireNonNull(distance));
            return this;
        }

        public RailroadCalculator build() {
            return new RailroadCalculator(railroadGraphBuilder.build());
        }
    }
}
