package zxh.demo.tw.assignment.trains.domain.entity;

import static zxh.demo.tw.assignment.trains.domain.util.NullChecker.requireNonNull;

import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.LinkedHashMap;
import java.util.List;

public class Road {
    private final LinkedHashMap<Station, Distance> stationStore;

    private Road(LinkedHashMap<Station, Distance> stationStore) {
        this.stationStore = stationStore;
    }

    public static RoadBuilder from(Station fromStation) {
        return new RoadBuilder(fromStation);
    }

    public List<Station> getAllStops() {
        return List.copyOf(stationStore.keySet());
    }

    public Distance getWholeDistance() {
        return Distance.of(stationStore.values().stream().mapToInt(Distance::getValue).sum());
    }

    public static class RoadBuilder {
        private final LinkedHashMap<Station, Distance> stationStore = new LinkedHashMap<>();

        private RoadBuilder(Station fromStation) {
            stationStore.put(requireNonNull(fromStation), Distance.of(0));
        }

        public RoadBuilder addStop(Station stop, Distance distance) {
            stationStore.put(requireNonNull(stop), requireNonNull(distance));
            return this;
        }

        public Road build() {
            return new Road(stationStore);
        }
    }
}
