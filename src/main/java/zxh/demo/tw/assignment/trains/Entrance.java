package zxh.demo.tw.assignment.trains;

import static java.util.Objects.isNull;

import zxh.demo.tw.assignment.trains.adapter.inbound.RouteAnswerer;
import zxh.demo.tw.assignment.trains.adapter.inbound.Scanner;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Entrance {
    private static final String INPUT_FILE_NAME = "input";

    public static void main(String[] args) {
        Entrance entrance = new Entrance();
        try {
            RailroadCalculator railroadCalculator = entrance.buildCalculator();
            doAnswer(railroadCalculator);
        } catch (IllegalArgumentException e) {
            exitWithError(e.getMessage());
        }
    }

    private RailroadCalculator buildCalculator() {
        URL resource = Entrance.class.getClassLoader().getResource(INPUT_FILE_NAME);
        if (isNull(resource)) {
            exitWithError("No input file detected, please check the resource dir.");
        }

        String routeDesc = null;
        try {
            // only need first line to build route, ignore others
            Optional<String> validFirstLine = Files.readAllLines(Path.of(resource.toURI()))
                    .stream()
                    .filter(s -> !s.trim().isEmpty())
                    .findFirst();

            if (validFirstLine.isEmpty()) {
                exitWithError("Input file is empty.");
            }

            routeDesc = validFirstLine.get();
        } catch (URISyntaxException | IOException e) {
            exitWithError(String.format("Read input file failed: %s", e.getMessage()));
        }

        return Scanner.scanToBuildCalculator(routeDesc);
    }

    private static void doAnswer(RailroadCalculator railroadCalculator) {
        RouteAnswerer answerer = new RouteAnswerer(railroadCalculator, System.out);
        // distance
        answerer.theDistanceOfRoute("A", "B", "C");
        answerer.theDistanceOfRoute("A", "D");
        answerer.theDistanceOfRoute("A", "D", "C");
        answerer.theDistanceOfRoute("A", "E", "B", "C", "D");
        answerer.theDistanceOfRoute("A", "E", "D");

        // stops
        answerer.theNumberOfTripWithMaximum("C", "C", 3);
        answerer.theNumberOfTripWithExactly("A", "C", 4);

        // shortest
        answerer.shortestRouteFrom("A", "C");
        answerer.shortestRouteFrom("B", "B");

        // maximum distance
        answerer.theNumberOfRoutesDistanceLessThan("C", "C", 30);
    }

    private static void exitWithError(String errorMsg) {
        System.err.println(errorMsg);
        System.exit(1);
    }
}
