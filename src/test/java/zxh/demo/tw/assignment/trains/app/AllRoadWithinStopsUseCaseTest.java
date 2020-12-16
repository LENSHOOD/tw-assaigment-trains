package zxh.demo.tw.assignment.trains.app;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.adapter.outbound.GraphBuilder;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.factory.RailroadCalculatorBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;
import java.util.stream.Collectors;

class AllRoadWithinStopsUseCaseTest {
    private RailroadCalculator railroadCalculator;

    @BeforeEach
    void setup() {
        railroadCalculator = RailroadCalculatorBuilder.of(new GraphBuilder<>())
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
    }

    @Test
    @Disabled
    void should_get_road_of_cdc_cebc_within_3_stop() {
        // given
        AllRoadWithinStopsUseCase useCase = new AllRoadWithinStopsUseCase(railroadCalculator);

        // when
        List<Road> roads = useCase.getAllRoadsWithinStopsFrom(Station.of("C"), Station.of("C"), 3);

        // then
        assertThat(roads.size(), is(2));
        List<String> roadOfStrs = roads.stream()
                .map(r -> r.getAllStops().stream().map(Station::getName).collect(Collectors.joining()))
                .collect(Collectors.toList());
        assertThat(roadOfStrs, containsInAnyOrder("CDC", "CEBC"));
    }

    @Test
    @Disabled
    void should_get_road_of_abcdc_adcdc_adebc_within_4_stop() {
        // given
        AllRoadWithinStopsUseCase useCase = new AllRoadWithinStopsUseCase(railroadCalculator);

        // when
        List<Road> roads = useCase.getAllRoadsWithinStopsFrom(Station.of("A"), Station.of("C"), 4);

        // then
        assertThat(roads.size(), is(5));
        List<String> roadOfStrs = roads.stream()
                .map(r -> r.getAllStops().stream().map(Station::getName).collect(Collectors.joining()))
                .collect(Collectors.toList());
        assertThat(roadOfStrs, containsInAnyOrder("ABC", "ADC", "AEBC", "ABCDC", "ADCDC", "ADEBC"));
    }
}