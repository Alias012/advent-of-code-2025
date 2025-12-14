package util;

import java.util.ArrayList;
import java.util.List;

public class Combinations {
    public static <T> List<List<T>> powerSet(List<T> data) {
        List<List<T>> powerSet = new ArrayList<>();
        int n = data.size();
        int max = (1 << n);
        for (int mask = 0; mask < max; mask++) {
            List<T> subset = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                if (((mask >> j) & 1) == 1) {
                    subset.add(data.get(j));
                }
            }
            powerSet.add(subset);
        }
        return powerSet;
    }

//    public static <T> List<List<T>> combinations(List<T> data, int size) {
//        List<List<T>> combinations = new ArrayList<>();
//        if (size == 0) {
//            return combinations;
//        }
//
//        int n = data.size();
//        int max = (1 << n);
//        int mask = (1 << size) - 1;
//        while (mask < max) {
//            List<T> subset = new ArrayList<>(n);
//            for (int j = 0; j < n; j++) {
//                if (((mask >> j) & 1) == 1) {
//                    subset.add(data.get(j));
//                }
//            }
//            combinations.add(subset);
//
//            int c = mask & -mask;
//            int r = mask + c;
//            mask = (((r ^ mask) >> 2) / c) | r;
//        }
//        return combinations;
//    }

//    private static List<List<Boolean>> binaryPowerSet(int n) {
//        List<List<Boolean>> powerSet = new ArrayList<>();
//        for (int i = 0; i < (1 << n); i++) {
//            List<Boolean> subset = new ArrayList<>(n);
//            for (int j = 0; j < n; j++) {
//                int grayEquivalent = i ^ (i >> 1);
//                subset.add(((1 << j) & grayEquivalent) > 0);
//            }
//            powerSet.add(subset);
//        }
//        return powerSet;
//    }
}
