package zxh.demo.tw.assignment.trains.domain.entity;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.stream.Collectors;

class RoadTest {
    @Test
    void should_create_road_with_node_ABE_and_total_distance_10() {
        Road road = Road.from(Station.of("A"))
                .addStop(Station.of("B"), Distance.of(7))
                .addStop(Station.of("E"), Distance.of(3))
                .build();

        assertThat(road.getAllStops(), contains(Station.of("A"), Station.of("B"), Station.of("E")));
        assertThat(road.getWholeDistance(), is(Distance.of(10)));
    }

    @Test
    void should_create_single_station_road_with_total_distance_0() {
        Road road = Road.from(Station.of("A")).build();

        assertThat(road.getAllStops(), contains(Station.of("A")));
        assertThat(road.getWholeDistance(), is(Distance.of(0)));
    }

    @Test
    void should_throw_Exception_if_null_station_or_distance() {
        assertThrows(NullPointerException.class, () -> Road.from(null), "Station cannot be null.");

        Road.RoadBuilder roadBuilder1 = Road.from(Station.of("A"));
        Distance distance = Distance.of(0);
        assertThrows(NullPointerException.class, () -> roadBuilder1.addStop(null, distance), "Station cannot be null.");

        Road.RoadBuilder roadBuilder2 = Road.from(Station.of("A"));
        Station b = Station.of("B");
        assertThrows(NullPointerException.class, () -> roadBuilder2.addStop(b, null), "Distance cannot be null.");
    }
}