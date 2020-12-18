package zxh.demo.tw.assignment.trains.domain.vo;

import static java.util.Objects.*;

import java.util.Objects;

public class Station {
    private final String name;

    private Station(String name) {
        this.name = name;
    }

    public static Station of(String name) {
        if (isNull(name) || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Station name cannot be empty.");
        }

        return new Station(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return hash(name);
    }
}
