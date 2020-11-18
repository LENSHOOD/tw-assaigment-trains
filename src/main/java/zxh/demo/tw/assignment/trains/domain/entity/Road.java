package zxh.demo.tw.assignment.trains.domain.entity;

import com.google.common.graph.ValueGraph;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;

public class Road {
    private Station from;
    private Station to;
    private ValueGraph<Station, Distance> roadGraph;

    public List<Station> getAllStops() {
        return null;
    }

    public Distance getWholeDistance() {
        return null;
    }

    public static class RoadBuilder {
        public static RoadBuilder from() {
            return null;
        }

        public RoadBuilder addStop(Station stop, Distance distance) {
            return null;
        }

        public Road build() {
            return null;
        }
    }
}
