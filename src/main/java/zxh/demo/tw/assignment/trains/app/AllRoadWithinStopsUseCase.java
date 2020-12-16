package zxh.demo.tw.assignment.trains.app;

import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;

public class AllRoadWithinStopsUseCase {
    private final RailroadCalculator calculator;

    public AllRoadWithinStopsUseCase(RailroadCalculator calculator) {
        this.calculator = calculator;
    }

    List<Road> getAllRoadsWithinStopsFrom(Station start, Station end, int stops) {
        return List.of();
    }
}
