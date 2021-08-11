package ru.job4j.pools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int size = max(matrix);
        Sums[] rsl = new Sums[size];
        for (int i = 0; i < size; i++) {
            rsl[i] = new Sums();
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int element = matrix[i][j];
                rsl[i].setRowSum(rsl[i].getRowSum() + element);
                rsl[j].setColSum(rsl[j].getColSum() + element);
            }
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = max(matrix);
        Sums[] rsl = new Sums[size];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        int mid = size / 2;
        if (size % 2 == 1) {
           futures.put(mid, getSum(matrix, mid));
        }
        for (int i = 0; i < mid; i++) {
            futures.put(i, getSum(matrix, i));
            futures.put(size - 1 - i, getSum(matrix, size - 1 - i));
        }

        for (Integer key : futures.keySet()) {
            rsl[key] = futures.get(key).get();
        }
        return rsl;
    }

    public static CompletableFuture<Sums> getSum(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums rsl = new Sums();
            if (matrix.length >= index) {
                rsl.setRowSum(Arrays.stream(matrix[index]).reduce(0, Integer::sum));
            }
            int col = 0;
            for (int[] ints : matrix) {
                if (ints.length > index) {
                    col += ints[index];
                }
            }
            rsl.setColSum(col);
            return rsl;
        });
    }

    private static int max(int[][] matrix) {
        int cols = matrix.length;
        int rows = cols > 0 ? matrix[0].length : 0;
        return Math.max(cols, rows);
    }
}
