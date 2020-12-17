package zxh.demo.tw.assignment.trains.app.helper;

import zxh.demo.tw.assignment.trains.domain.entity.RailroadCalculator;
import zxh.demo.tw.assignment.trains.domain.entity.Road;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class RoadPermutationHelper {
    private RoadPermutationHelper() {}

    /**
     * Combine and permute the given roads by RailRoadCalculator to get the permutation of roads.
     * Using predicates to control whether the merged road is eligible to contained in return collection.
     *
     * @param calculator RailroadCalculator, contains road and distance info
     * @param possibleRoads possible roads to combined and permuted
     * @param predicates predicates of control whether the merged road is eligible to contained in return collection
     * @return roads list
     */
    public static List<Road> doPermutation(RailroadCalculator calculator, List<Road> possibleRoads, Predicate<Road> predicates) {
        List<Road> finalRoads = new CopyOnWriteArrayList<>(possibleRoads);

        BiConsumer<Road, Road> tryMergeThenAdd = (left, right) -> {
            Optional<Road> mergedOp = calculator.tryBestMerge(left, right);
            if (mergedOp.isPresent() &&
                    predicates.test(mergedOp.get()) &&
                    !finalRoads.contains(mergedOp.get())) {
                finalRoads.add(mergedOp.get());
            }
        };

        // do not optimize to enhanced 'for' since it implicit use iterator,
        // that will lead to wrong result because of modify the list while traverse it.
        for (int i=0; i<finalRoads.size(); i++) {
            Road curr = finalRoads.get(i);
            for (Road possibleRoad : possibleRoads) {
                tryMergeThenAdd.accept(curr, possibleRoad);
                tryMergeThenAdd.accept(possibleRoad, curr);
            }
        }

        return finalRoads;
    }
}
