package zxh.demo.tw.assignment.trains.domain.vo;

import java.util.Objects;

public class Distance {
    private final int value;

    private Distance(int value) {
        this.value = value;
    }

    public static Distance of(int value) {
        return new Distance(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return value == distance.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
