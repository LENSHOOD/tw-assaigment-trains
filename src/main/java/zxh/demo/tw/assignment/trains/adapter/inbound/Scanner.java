package zxh.demo.tw.assignment.trains.adapter.inbound;

import zxh.demo.tw.assignment.trains.adapter.outbound.GraphBuilder;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.factory.RailroadCalculatorBuilder;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scanner:
 *
 * @author zhangxuhai
 * @date 2020/12/17
 */
public class Scanner {
    /**
     * Group 1: start station
     * Group 2: end station
     * Group 3: distance integer
     */
    private static final Pattern ROUTE = Pattern.compile("^([A-Za-z])([A-Za-z])(\\d+)$");
    private static final String HEADER = "Graph:";

    public static RailroadCalculator scanToBuildCalculator(String input) {
        RailroadCalculatorBuilder builder = RailroadCalculatorBuilder.of(new GraphBuilder<>());
        String[] routeStrs = input.replace(HEADER, "").split(",");
        for (String routeStr : routeStrs) {
            Matcher matcher = ROUTE.matcher(routeStr.trim());
            if (!matcher.find()) {
                throw new IllegalArgumentException(String.format("Wrong input route: %s", routeStr.trim()));
            }

            builder.addRailroad(
                    Station.of(matcher.group(1)),
                    Station.of(matcher.group(2)),
                    Distance.of(Integer.parseInt(matcher.group(3))));
        }
        return builder.build();
    }

    private Scanner() {}
}
