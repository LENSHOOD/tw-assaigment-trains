package zxh.demo.tw.assignment.trains.app;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.adapter.outbound.GraphBuilder;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.factory.RailroadCalculatorBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class RoadByDistanceUseCaseTest {
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
    void should_get_shortest_road_from_a_to_c() {
        // given
        RoadByDistanceUseCase useCase = new RoadByDistanceUseCase(railroadCalculator);

        // when
        Optional<Road> shortestRoadOp = useCase.getShortestRoadFrom(Station.of("A"), Station.of("C"));

        // then
        assertTrue(shortestRoadOp.isPresent());
        assertThat(
                shortestRoadOp.get().getAllStops().stream().map(Station::getName).collect(Collectors.joining()),
                is("ABC"));
        assertThat(
                shortestRoadOp.get().getWholeDistance().getValue(), is(9));
    }

    @Test
    void should_get_shortest_road_from_b_to_b() {
        // given
        RoadByDistanceUseCase useCase = new RoadByDistanceUseCase(railroadCalculator);

        // when
        Optional<Road> shortestRoadOp = useCase.getShortestRoadFrom(Station.of("B"), Station.of("B"));

        // then
        assertTrue(shortestRoadOp.isPresent());
        assertThat(
                shortestRoadOp.get().getAllStops().stream().map(Station::getName).collect(Collectors.joining()),
                is("BCEB"));
        assertThat(
                shortestRoadOp.get().getWholeDistance().getValue(), is(9));
    }

    @Test
    void should_get_roads_from_c_to_c_that_distance_less_than_30() {
        // given
        RoadByDistanceUseCase useCase = new RoadByDistanceUseCase(railroadCalculator);

        // when
        List<Road> roads = useCase.getRoadsDistanceLessThan(Station.of("C"), Station.of("C"), Distance.of(30));

        // then
        assertThat(roads.size(), is(7));
        List<String> roadOfStrs = roads.stream()
                .map(r -> r.getAllStops().stream().map(Station::getName).collect(Collectors.joining()))
                .collect(Collectors.toList());
        assertThat(roadOfStrs, containsInAnyOrder("CDC", "CEBC", "CEBCDC", "CDCEBC", "CDEBC", "CEBCEBC", "CEBCEBCEBC"));
    }
}