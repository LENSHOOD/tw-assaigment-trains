package zxh.demo.tw.assignment.trains.app;

import zxh.demo.tw.assignment.trains.app.helper.RoadPermutationHelper;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;
import java.util.stream.Collectors;

public class StopsUseCase {
    private final RailroadCalculator calculator;

    public StopsUseCase(RailroadCalculator calculator) {
        this.calculator = calculator;
    }

    List<Road> getAllRoadsExactlyStopsFrom(Station start, Station end, int stops) {
        return getAllRoadsWithinStopsFrom(start, end, stops)
                .stream()
                .filter(r -> r.getAllStops().size() == stops + 1)
                .collect(Collectors.toList());
    }

    List<Road> getAllRoadsWithinStopsFrom(Station start, Station end, int stops) {
        List<Road> possibleRoadsWithinStops = calculator.getPossibleRoads(start, end)
                .stream()
                .filter(r -> r.getAllStops().size() <= stops + 1)
                .collect(Collectors.toList());
        return RoadPermutationHelper.doPermutation(
                calculator,
                possibleRoadsWithinStops,
                road -> road.getAllStops().size() <= stops + 1);
    }
}
