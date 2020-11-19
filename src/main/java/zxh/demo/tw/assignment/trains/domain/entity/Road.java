package zxh.demo.tw.assignment.trains.domain.entity;

import static java.util.Objects.*;

import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Road {
    private final Station from;
    private final ValueGraph<Station, Distance> roadGraph;

    private Road(Station from, ValueGraph<Station, Distance> roadGraph) {
        this.from = from;
        this.roadGraph = roadGraph;
    }

    public static RoadBuilder from(Station fromStation) {
        return new RoadBuilder(fromStation);
    }

    public List<Station> getAllStops() {
        List<Station> allStops = new ArrayList<>();
        setSuccessors(from, allStops);
        return List.copyOf(allStops);
    }

    private void setSuccessors(Station start, List<Station> allStopsHolder) {
        allStopsHolder.add(start);
        roadGraph.successors(start).forEach(stop -> setSuccessors(stop, allStopsHolder));
    }

    public Distance getWholeDistance() {
        List<Station> allStops = getAllStops();
        int distanceValue = 0;
        for (int i = 0; i < allStops.size(); i++) {
            if (i == allStops.size() - 1) {
                break;
            }

            Station currStation = allStops.get(i);
            Station nextStation = allStops.get(i + 1);
            distanceValue += roadGraph.edgeValue(currStation, nextStation).orElse(Distance.of(0)).getValue();
        }
        return Distance.of(distanceValue);
    }

    public static class RoadBuilder {
        private Station from;
        private Station to;
        private ImmutableValueGraph.Builder<Station, Distance> roadGraphBuilder;

        private RoadBuilder(Station fromStation) {
            from = to = requireNonNull(fromStation, "Station cannot be null.");
            roadGraphBuilder = ValueGraphBuilder
                    .directed()
                    .<Station, Distance>immutable()
                    .addNode(fromStation);
        }

        public RoadBuilder addStop(Station stop, Distance distance) {
            roadGraphBuilder.putEdgeValue(
                    to,
                    requireNonNull(stop, "Station cannot be null."),
                    requireNonNull(distance, "Distance cannot be null."));
            to = stop;
            return this;
        }

        public Road build() {
            return new Road(from, roadGraphBuilder.build());
        }
    }
}
