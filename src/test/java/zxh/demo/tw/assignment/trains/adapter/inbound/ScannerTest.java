package zxh.demo.tw.assignment.trains.adapter.inbound;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;
import java.util.stream.Collectors;

class ScannerTest {
    @Test
    void should_scan_AB5_BC4_CD8() {
        // given
        String input = "Graph: AB15, BC42, CD81";

        // when
        RailroadCalculator railroadCalculator = Scanner.scanToBuildCalculator(input);

        // then
        List<Road> ab = railroadCalculator.getPossibleRoads(Station.of("A"), Station.of("B"));
        assertThat(ab.size(), is(1));
        assertThat(ab.get(0).getAllStops().stream().map(Station::getName).collect(Collectors.joining()), is("AB"));
        assertThat(ab.get(0).getWholeDistance().getValue(), is(15));

        List<Road> bc = railroadCalculator.getPossibleRoads(Station.of("B"), Station.of("C"));
        assertThat(bc.size(), is(1));
        assertThat(bc.get(0).getAllStops().stream().map(Station::getName).collect(Collectors.joining()), is("BC"));
        assertThat(bc.get(0).getWholeDistance().getValue(), is(42));

        List<Road> cd = railroadCalculator.getPossibleRoads(Station.of("C"), Station.of("D"));
        assertThat(cd.size(), is(1));
        assertThat(cd.get(0).getAllStops().stream().map(Station::getName).collect(Collectors.joining()), is("CD"));
        assertThat(cd.get(0).getWholeDistance().getValue(), is(81));
    }

    @Test
    void should_get_error_when_wrong_input() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> Scanner.scanToBuildCalculator("Graph: AAB5, BC4, CD8"),
                "Wrong input route: AAB5");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> Scanner.scanToBuildCalculator("AB5, BC1A, CD200"),
                "Wrong input route: BC1A");
    }
}