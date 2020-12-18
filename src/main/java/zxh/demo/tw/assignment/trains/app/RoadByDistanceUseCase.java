package zxh.demo.tw.assignment.trains.app;

import zxh.demo.tw.assignment.trains.app.helper.RoadPermutationHelper;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RoadByDistanceUseCase {
    private final RailroadCalculator calculator;

    public RoadByDistanceUseCase(RailroadCalculator calculator) {
        this.calculator = calculator;
    }

    public Optional<Road> getShortestRoadFrom(Station start, Station end) {
        return calculator.getPossibleRoads(start, end)
                .stream()
                .min(Comparator.comparing(r -> r.getWholeDistance().getValue()));
    }

    public List<Road> getRoadsDistanceLessThan(Station start, Station stop, Distance maxDistance) {
        return RoadPermutationHelper.doPermutation(
                calculator,
                calculator.getPossibleRoads(start, stop),
                road -> road.getWholeDistance().getValue() < maxDistance.getValue());
    }
}
