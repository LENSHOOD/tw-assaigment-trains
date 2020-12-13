package zxh.demo.tw.assignment.trains.domain.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.domain.FakeValueGraphBuilder;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraphBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;

class RailroadCalculatorBuilderTest {
    private final ValueGraphBuilder<Station, Distance> mockBuilder = new FakeValueGraphBuilder<>();

    @Test
    void should_build_calculator() {
        RailroadCalculator calculator = RailroadCalculatorBuilder.of(mockBuilder)
                .addRailroad(Station.of("A"), Station.of("B"), Distance.of(5))
                .addRailroad(Station.of("B"), Station.of("C"), Distance.of(4))
                .addRailroad(Station.of("C"), Station.of("D"), Distance.of(8))
                .addRailroad(Station.of("D"), Station.of("C"), Distance.of(8))
                .addRailroad(Station.of("D"), Station.of("E"), Distance.of(6))
                .addRailroad(Station.of("A"), Station.of("D"), Distance.of(5))
                .addRailroad(Station.of("C"), Station.of("E"), Distance.of(2))
                .addRailroad(Station.of("E"), Station.of("B"), Distance.of(3))
                .addRailroad(Station.of("A"), Station.of("E"), Distance.of(7))
                .build();

        assertNotNull(calculator);
    }

    @Test
    void should_throw_Exception_if_null_station_or_distance() {
        RailroadCalculatorBuilder builder1 = RailroadCalculatorBuilder.of(mockBuilder);
        assertThrows(NullPointerException.class, () -> builder1.addRailroad(null, null, null), "Station cannot be null.");

        RailroadCalculatorBuilder builder2 = RailroadCalculatorBuilder.of(mockBuilder);
        Station a = Station.of("A");
        assertThrows(NullPointerException.class, () -> builder2.addRailroad(a, null, null), "Station cannot be null.");

        RailroadCalculatorBuilder builder3 = RailroadCalculatorBuilder.of(mockBuilder);
        Station b = Station.of("B");
        Station c = Station.of("C");
        assertThrows(NullPointerException.class, () -> builder3.addRailroad(b, c, null), "Distance cannot be null.");
    }
}