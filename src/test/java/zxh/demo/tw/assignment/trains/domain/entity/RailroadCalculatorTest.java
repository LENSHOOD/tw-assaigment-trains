package zxh.demo.tw.assignment.trains.domain.entity;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.domain.FakeValueGraphBuilder;
import zxh.demo.tw.assignment.trains.domain.factory.RailroadCalculatorBuilder;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraph;
import zxh.demo.tw.assignment.trains.domain.repository.ValueGraphBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;
import java.util.stream.Collectors;

class RailroadCalculatorTest {
    private final ValueGraphBuilder<Station, Distance> mockBuilder = new FakeValueGraphBuilder<>();

    @Test
    public void should_get_for_roads_from_a_to_c() {
        // given
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

        // when
        List<Road> possibleRoads = calculator.getPossibleRoads(Station.of("A"), Station.of("C"));

        // then
        assertThat(possibleRoads.size(), is(4));
        List<String> roads = possibleRoads.stream().map(Road::getAllStops).map(stepList -> stepList.stream().map(Station::getName).collect(Collectors.joining())).collect(Collectors.toList());
        assertThat(roads, hasItem("ABC"));
        assertThat(roads, hasItem("AEBC"));
        assertThat(roads, hasItem("ADC"));
        assertThat(roads, hasItem("ADEBC"));
    }
}