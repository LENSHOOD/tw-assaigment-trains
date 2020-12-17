package zxh.demo.tw.assignment.trains.domain.entity;

import static zxh.demo.tw.assignment.trains.domain.util.NullChecker.requireNonNull;

import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.*;
import java.util.stream.Collectors;

public class Road {
    private final List<Pair> stationStore;

    private Road(List<Pair> stationStore) {
        this.stationStore = stationStore;
    }

    public static RoadBuilder from(Station fromStation) {
        return new RoadBuilder(fromStation);
    }

    public List<Station> getAllStops() {
        return List.copyOf(stationStore.stream().map(pair -> pair.station).collect(Collectors.toList()));
    }

    public Distance getWholeDistance() {
        return Distance.of(stationStore.stream().map(pair -> pair.distance).mapToInt(Distance::getValue).sum());
    }

    public Station getHead() {
        return stationStore.get(0).station;
    }

    public Station getTail() {
        return stationStore.get(stationStore.size() - 1).station;
    }

    public static class RoadBuilder {
        private final List<Pair> stationStore = new ArrayList<>();

        private RoadBuilder(Station fromStation) {
            stationStore.add(new Pair(requireNonNull(fromStation), Distance.of(0)));
        }

        public RoadBuilder addStop(Station stop, Distance distance) {
            stationStore.add(new Pair(requireNonNull(stop), requireNonNull(distance)));
            return this;
        }

        public Road build() {
            return new Road(stationStore);
        }
    }

    private static class Pair {
        private final Station station;
        private final Distance distance;

        private Pair(Station station, Distance distance) {
            this.station = station;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(station, pair.station) && Objects.equals(distance, pair.distance);
        }

        @Override
        public int hashCode() {
            return Objects.hash(station, distance);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return Objects.equals(stationStore, road.stationStore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationStore);
    }
}
