package zxh.demo.tw.assignment.trains.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;

class RailroadCalculatorTest {
    @Test
    public void should_build_calculator() {
        RailroadCalculator calculator = RailroadCalculator.builder()
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
    public void should_throw_Exception_if_null_station_or_distance() {
        RailroadCalculator.RailroadCalculatorBuilder builder1 = RailroadCalculator.builder();
        assertThrows(NullPointerException.class, () -> builder1.addRailroad(null, null, null), "Station cannot be null.");

        RailroadCalculator.RailroadCalculatorBuilder builder2 = RailroadCalculator.builder();
        Station a = Station.of("A");
        assertThrows(NullPointerException.class, () -> builder2.addRailroad(a, null, null), "Station cannot be null.");

        RailroadCalculator.RailroadCalculatorBuilder builder3 = RailroadCalculator.builder();
        Station b = Station.of("B");
        Station c = Station.of("C");
        assertThrows(NullPointerException.class, () -> builder3.addRailroad(b, c, null), "Distance cannot be null.");
    }

}