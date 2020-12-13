package zxh.demo.tw.assignment.trains.domain.entity;

import zxh.demo.tw.assignment.trains.domain.repository.ValueGraph;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.*;
import java.util.stream.Collectors;

public class RailroadCalculator {
    private ValueGraph<Station, Distance> railroadGraph;

    public RailroadCalculator(ValueGraph<Station, Distance> railroadGraph) {
        this.railroadGraph = railroadGraph;
    }

    public List<Road> getPossibleRoads(Station startStation, Station endStation) {
        ArrayList<List<Station>> finalRailroads = new ArrayList<>();
        recursivelyFindRailroad(startStation, endStation, new ArrayList<>(List.of(startStation)), finalRailroads);

        return finalRailroads.stream().map(this::toRoad).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    private void recursivelyFindRailroad(Station startStation, Station endStation, List<Station> preRailroad, List<List<Station>> finalRailroads) {
        for (Station next : railroadGraph.successors(startStation)) {
            if (preRailroad.contains(next)) {
                continue;
            }

            ArrayList<Station> curRailroad = new ArrayList<>(preRailroad);
            curRailroad.add(next);
            if (!next.equals(endStation)) {
                recursivelyFindRailroad(next, endStation, curRailroad, finalRailroads);
            } else {
                finalRailroads.add(curRailroad);
            }
        }
    }

    private Optional<Road> toRoad(List<Station> railroad) {
        if (Objects.isNull(railroad) || railroad.isEmpty()) {
            return Optional.empty();
        }

        Road.RoadBuilder builder = Road.from(railroad.get(0));
        for (int i = 1; i < railroad.size(); i++) {
            builder.addStop(railroad.get(i), railroadGraph.edgeValue(railroad.get(i - 1), railroad.get(i)).orElse(Distance.of(0)));
        }

        return Optional.of(builder.build());
    }

}
