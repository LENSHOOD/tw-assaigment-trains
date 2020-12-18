package zxh.demo.tw.assignment.trains.app;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.adapter.outbound.GraphBuilder;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.factory.RailroadCalculatorBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.Optional;

class DistanceUseCaseTest {
    @Test
    void should_get_distance_of_a_b_as_5_when_AB5() {
        // given
        RailroadCalculator railroadCalculator = RailroadCalculatorBuilder.of(new GraphBuilder<>())
                .addRailroad(Station.of("A"), Station.of("B"), Distance.of(5))
                .build();
        DistanceUseCase distanceUseCase = new DistanceUseCase(railroadCalculator);

        // when
        Optional<Distance> directlyDistance = distanceUseCase.getDirectlyDistance(Station.of("A"), Station.of("B"));

        // then
        assertTrue(directlyDistance.isPresent());
        assertThat(directlyDistance.get().getValue(), is(5));
    }

    @Test
    void should_get_distance_of_a_b_d_as_12_when_AB4_BC2_CA1_BD8() {
        // given
        RailroadCalculator railroadCalculator = RailroadCalculatorBuilder.of(new GraphBuilder<>())
                .addRailroad(Station.of("A"), Station.of("B"), Distance.of(4))
                .addRailroad(Station.of("B"), Station.of("C"), Distance.of(2))
                .addRailroad(Station.of("C"), Station.of("A"), Distance.of(1))
                .addRailroad(Station.of("B"), Station.of("D"), Distance.of(8))
                .build();
        DistanceUseCase distanceUseCase = new DistanceUseCase(railroadCalculator);

        // when
        Optional<Distance> directlyDistance = distanceUseCase.getDirectlyDistance(
                Station.of("A"), Station.of("B"), Station.of("D"));

        // then
        assertTrue(directlyDistance.isPresent());
        assertThat(directlyDistance.get().getValue(), is(12));
    }

    @Test
    void should_get_error_when_given_road_of_stations_contain_loop() {
        RailroadCalculator railroadCalculator = RailroadCalculatorBuilder.of(new GraphBuilder<>()).build();
        DistanceUseCase distanceUseCase = new DistanceUseCase(railroadCalculator);

        assertThrows(IllegalArgumentException.class,
                () -> distanceUseCase.getDirectlyDistance(Station.of("A"), Station.of("B"), Station.of("A")),
                "Cannot contain a loop in the road of stations.");
    }

    @Test
    void should_get_error_when_given_road_of_stations_less_than_two() {
        RailroadCalculator railroadCalculator = RailroadCalculatorBuilder.of(new GraphBuilder<>()).build();
        DistanceUseCase distanceUseCase = new DistanceUseCase(railroadCalculator);

        assertThrows(IllegalArgumentException.class,
                () -> distanceUseCase.getDirectlyDistance(Station.of("A")),
                "We need at least two stations to calculate distance.");
    }
}