package zxh.demo.tw.assignment.trains.domain.factory;

import static zxh.demo.tw.assignment.trains.domain.util.NullChecker.requireNonNull;

import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraphBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;

/**
 * RailroadCalculatorBuilder:
 *
 * @author zhangxuhai
 * @date 2020/12/13
 */
public class RailroadCalculatorBuilder {
    private final ValueGraphBuilder<Station, Distance> railroadGraphBuilder;

    public static RailroadCalculatorBuilder of(ValueGraphBuilder<Station, Distance> railroadGraphBuilder) {
        return new RailroadCalculatorBuilder(railroadGraphBuilder);
    }

    private RailroadCalculatorBuilder(ValueGraphBuilder<Station, Distance> railroadGraphBuilder) {
        this.railroadGraphBuilder = railroadGraphBuilder;
    }

    public RailroadCalculatorBuilder addRailroad(Station from, Station to, Distance distance) {
        railroadGraphBuilder.putEdgeValue(requireNonNull(from), requireNonNull(to), requireNonNull(distance));
        return this;
    }

    public RailroadCalculator build() {
        return new RailroadCalculator(railroadGraphBuilder.build());
    }
}
