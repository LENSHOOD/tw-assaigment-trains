package zxh.demo.tw.assignment.trains.app;

import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ShortestRoadUseCase {
    private final RailroadCalculator calculator;

    public ShortestRoadUseCase(RailroadCalculator calculator) {
        this.calculator = calculator;
    }

    Optional<Road> getShortestRoadFrom(Station start, Station end) {
        List<Road> possibleRoads = calculator.getPossibleRoads(start, end);
        return possibleRoads.stream().min(Comparator.comparing(r -> r.getWholeDistance().getValue()));
    }
}
