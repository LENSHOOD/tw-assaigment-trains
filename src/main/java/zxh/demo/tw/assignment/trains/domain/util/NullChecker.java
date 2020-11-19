package zxh.demo.tw.assignment.trains.domain.util;

import java.util.Objects;

public class NullChecker {
    private NullChecker() { }

    public static <T> T requireNonNull(T object) {
        return Objects.requireNonNull(object, String.format("%s cannot be null.", object.getClass().getName()));
    }
}
