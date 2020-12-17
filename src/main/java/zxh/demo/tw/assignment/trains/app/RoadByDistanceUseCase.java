package zxh.demo.tw.assignment.trains.app;

import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

public class RoadByDistanceUseCase {
    private final RailroadCalculator calculator;

    public RoadByDistanceUseCase(RailroadCalculator calculator) {
        this.calculator = calculator;
    }

    Optional<Road> getShortestRoadFrom(Station start, Station end) {
        return calculator.getPossibleRoads(start, end)
                .stream()
                .min(Comparator.comparing(r -> r.getWholeDistance().getValue()));
    }

    List<Road> getRoadsDistanceLessThan(Station start, Station stop, Distance maxDistance) {
        return doPermutation(calculator.getPossibleRoads(start, stop), maxDistance.getValue());
    }

    private List<Road> doPermutation(List<Road> possibleRoads, int maxDistanceCount) {
        List<Road> finalRoads = new CopyOnWriteArrayList<>(possibleRoads);

        BiConsumer<Road, Road> tryMergeThenAdd = (left, right) -> {
            Optional<Road> leftMergedOp = calculator.tryBestMerge(left, right);
            if (leftMergedOp.isPresent() &&
                    leftMergedOp.get().getWholeDistance().getValue() < maxDistanceCount &&
                    !finalRoads.contains(leftMergedOp.get())) {
                finalRoads.add(leftMergedOp.get());
            }
        };

        // do not optimize to enhanced 'for' since it implicit use iterator,
        // that will lead to wrong result because of modify the list while traverse it.
        for (int i=0; i<finalRoads.size(); i++) {
            Road curr = finalRoads.get(i);
            for (Road possibleRoad : possibleRoads) {
                tryMergeThenAdd.accept(curr, possibleRoad);
                tryMergeThenAdd.accept(possibleRoad, curr);
            }
        }

        return finalRoads;
    }
}
