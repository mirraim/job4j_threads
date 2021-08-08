package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParrallelFinder<T> extends RecursiveTask<Integer> {
    private final T t;
    private final T[] arr;


    public ParrallelFinder(T t, T[] arr) {
        this.t = t;
        this.arr = arr;
    }


    @Override
    protected Integer compute() {
        if (arr.length <= 10) {
            return find();
        }
        int mid = arr.length / 2;
        ParrallelFinder<T> leftFinder = new ParrallelFinder<>(
                t, Arrays.copyOfRange(arr, 0, mid)
        );
        ParrallelFinder<T> rightFinder = new ParrallelFinder<>(
                t, Arrays.copyOfRange(arr, mid, arr.length)
        );
        leftFinder.fork();
        rightFinder.fork();
        int left = leftFinder.join();
        int right = rightFinder.join();
        return left != -1 ? left : right;
    }

    private int find() {
        int rsl = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }

    public static int getIndex(Object[] t, Object obj) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParrallelFinder<>(obj, t));
    }
}
