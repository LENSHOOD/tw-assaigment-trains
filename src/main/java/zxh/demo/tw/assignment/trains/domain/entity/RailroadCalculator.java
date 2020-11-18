package zxh.demo.tw.assignment.trains.domain.entity;

import com.google.common.graph.ValueGraph;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.List;

public class RailroadCalculator {
    private ValueGraph<Station, Distance> stationGraph;

    public void addRoad(Station from, Station to, Distance distance) {

    }

    public List<Road> getPossibleRoads(Station startStation, Station endStation) {
        return null;
    }
}
