package zxh.demo.tw.assignment.trains.domain.entity;

import static zxh.demo.tw.assignment.trains.domain.util.NullChecker.requireNonNull;

import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Road {
    private final List<Map<Station, Distance>> stationStore;

    private Road(List<Map<Station, Distance>> stationStore) {
        this.stationStore = stationStore;
    }

    public static RoadBuilder from(Station fromStation) {
        return new RoadBuilder(fromStation);
    }

    public List<Station> getAllStops() {
        return List.copyOf(stationStore.stream().flatMap(pair -> pair.keySet().stream()).collect(Collectors.toList()));
    }

    public Distance getWholeDistance() {
        return Distance.of(stationStore.stream().flatMap(pair -> pair.values().stream()).mapToInt(Distance::getValue).sum());
    }

    public static class RoadBuilder {
        private final List<Map<Station, Distance>> stationStore = new ArrayList<>();

        private RoadBuilder(Station fromStation) {
            stationStore.add(Map.of(requireNonNull(fromStation), Distance.of(0)));
        }

        public RoadBuilder addStop(Station stop, Distance distance) {
            stationStore.add(Map.of(requireNonNull(stop), requireNonNull(distance)));
            return this;
        }

        public Road build() {
            return new Road(stationStore);
        }
    }
}
