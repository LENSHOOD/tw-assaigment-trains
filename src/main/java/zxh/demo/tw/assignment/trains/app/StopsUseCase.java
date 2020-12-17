package zxh.demo.tw.assignment.trains.app;

import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        return doPermutation(possibleRoadsWithinStops, stops);
    }

    private List<Road> doPermutation(List<Road> possibleRoads, int maxStops) {
        List<Road> finalRoads = new ArrayList<>(possibleRoads);
        for (int i = 0; i < possibleRoads.size(); i++) {
            for (Road possibleRoad : possibleRoads) {
                Optional<Road> mergedOp = calculator.tryBestMerge(possibleRoads.get(i), possibleRoad);
                if (mergedOp.isEmpty()) {
                    continue;
                }

                if (mergedOp.get().getAllStops().size() <= maxStops + 1) {
                    finalRoads.add(mergedOp.get());
                }
            }
        }

        return finalRoads;
    }
}
