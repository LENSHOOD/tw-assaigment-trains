package zxh.demo.tw.assignment.trains.adapter.inbound;

import zxh.demo.tw.assignment.trains.app.DistanceUseCase;
import zxh.demo.tw.assignment.trains.app.RoadByDistanceUseCase;
import zxh.demo.tw.assignment.trains.app.StopsUseCase;
import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import zxh.demo.tw.assignment.trains.domain.vo.Distance;
import zxh.demo.tw.assignment.trains.domain.vo.Station;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RouteAnswerer {
    private final RailroadCalculator calculator;
    private final PrintStream printStream;

    public RouteAnswerer(RailroadCalculator calculator, PrintStream outputStream) {
        this.calculator = calculator;
        this.printStream = outputStream;
    }

    public void theDistanceOfRoute(String... routes) {
        DistanceUseCase useCase = new DistanceUseCase(calculator);
        Optional<Distance> distanceOp = useCase.getDirectlyDistance(
                Stream.of(routes).map(Station::of).toArray(Station[]::new));

        if (distanceOp.isEmpty()) {
            printStream.println("NO SUCH ROUTE");
            return;
        }

        printStream.println(distanceOp.get().getValue());
    }

    public void theNumberOfTripWithMaximum(String start, String stop, int maximumStops) {
        StopsUseCase useCase = new StopsUseCase(calculator);
        List<Road> roads = useCase.getAllRoadsWithinStopsFrom(Station.of(start), Station.of(stop), maximumStops);
        printStream.println(roads.size());
    }

    public void theNumberOfTripWithExactly(String start, String stop, int exactlyStops) {
        StopsUseCase useCase = new StopsUseCase(calculator);
        List<Road> roads = useCase.getAllRoadsExactlyStopsFrom(Station.of(start), Station.of(stop), exactlyStops);
        printStream.println(roads.size());
    }

    public void shortestRouteFrom(String start, String stop) {
        RoadByDistanceUseCase useCase = new RoadByDistanceUseCase(calculator);
        Optional<Road> roadOp = useCase.getShortestRoadFrom(Station.of(start), Station.of(stop));
        if (roadOp.isEmpty()) {
            printStream.println("NO SUCH ROUTE");
            return;
        }

        printStream.println(roadOp.get().getWholeDistance().getValue());
    }

    public void theNumberOfRoutesDistanceLessThan(String start, String stop, int maximumDistance) {
        RoadByDistanceUseCase useCase = new RoadByDistanceUseCase(calculator);
        List<Road> roads = useCase.getRoadsDistanceLessThan(Station.of(start), Station.of(stop), Distance.of(maximumDistance));
        printStream.println(roads.size());
    }
}
