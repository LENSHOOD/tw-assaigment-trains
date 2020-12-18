package zxh.demo.tw.assignment.trains.app;

import static zxh.demo.tw.assignment.trains.domain.util.NullChecker.requireNonNull;

import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DistanceUseCase {
    private final RailroadCalculator calculator;

    public DistanceUseCase(RailroadCalculator calculator) {
        this.calculator = calculator;
    }

    public Optional<Distance> getDirectlyDistance(Station... routeOfStations) {
        if (routeOfStations.length < 2) {
            throw new IllegalArgumentException("We need at least two stations to calculate distance.");
        }

        if (Stream.of(routeOfStations).distinct().count() != routeOfStations.length) {
            throw new IllegalArgumentException("Cannot contain a loop in the road of stations.");
        }

        List<Road> possibleRoads = calculator.getPossibleRoads(
                requireNonNull(requireNonNull(routeOfStations[0])),
                requireNonNull(routeOfStations[routeOfStations.length - 1]));

        return possibleRoads.stream()
                .filter(road -> Arrays.equals(road.getAllStops().toArray(new Station[]{}), routeOfStations))
                .map(Road::getWholeDistance)
                .findAny();
    }
}
