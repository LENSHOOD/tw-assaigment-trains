package zxh.demo.tw.assignment.trains.domain.entity;

import static java.util.Objects.*;

import zxh.demo.tw.assignment.trains.domain.repository.ValueGraph;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.*;
import java.util.stream.Collectors;

public class RailroadCalculator {
    private final ValueGraph<Station, Distance> railroadGraph;

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
            ArrayList<Station> curRailroad = new ArrayList<>(preRailroad);
            curRailroad.add(next);
            if (!next.equals(endStation)) {
                if (preRailroad.contains(next)) {
                    continue;
                }

                recursivelyFindRailroad(next, endStation, curRailroad, finalRailroads);
            } else {
                finalRailroads.add(curRailroad);
            }
        }
    }

    private Optional<Road> toRoad(List<Station> railroad) {
        if (isNull(railroad) || railroad.isEmpty()) {
            return Optional.empty();
        }

        Road.RoadBuilder builder = Road.from(railroad.get(0));
        for (int i = 1; i < railroad.size(); i++) {
            builder.addStop(railroad.get(i), railroadGraph.edgeValue(railroad.get(i - 1), railroad.get(i)).orElse(Distance.of(0)));
        }

        return Optional.of(builder.build());
    }

    public Optional<Road> tryBestMerge(Road left, Road right) {
        Station leftTail = left.getTail();
        LinkedList<Station> rightRoadOfStations = new LinkedList<>(right.getAllStops());
        Station rightHead = rightRoadOfStations.poll();
        while (nonNull(rightHead)) {
            if (railroadGraph.successors(leftTail).contains(rightHead)) {
                break;
            }
            rightHead = rightRoadOfStations.poll();
        }

        if (isNull(rightHead)) {
            return Optional.empty();
        }

        ArrayList<Station> mergedStations = new ArrayList<>(left.getAllStops());
        mergedStations.add(rightHead);
        mergedStations.addAll(rightRoadOfStations);
        return toRoad(mergedStations);
    }
}
