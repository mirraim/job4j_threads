package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParrallelFinder<T> extends RecursiveTask<Integer> {
    /**
     * Объект, индекс которого нужно найти в массиве и массив, в котором нужно осуществить поиск
     */
    private final T t;
    private final T[] arr;

    /**
     * Переменные from и to используются для определения границ внутри массива,
     * в рамках которых будет выполнятся поиск после разделения на подзадачи
     */
    private final int from;
    private final int to;

    public ParrallelFinder(T t, T[] arr, int from, int to) {
        this.t = t;
        this.arr = arr;
        this.from = from;
        this.to = to;
    }

    /**
     * Если массив больше 10 ячеек, то разделяем его пополам, создавая новые рекурсивные задачи
     * В конце выполняем слияние рекурсивных задач
     * Если меньше или равно 10, то выполняем поиск
     */
    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return find();
        }
        int mid = (to + from) / 2;
        ParrallelFinder<T> leftFinder = new ParrallelFinder<>(
                t, arr, from, mid);
        ParrallelFinder<T> rightFinder = new ParrallelFinder<>(
                t, arr, mid + 1, to);
        leftFinder.fork();
        rightFinder.fork();
        int left = leftFinder.join();
        int right = rightFinder.join();
        return left != -1 ? left : right;
    }

    private int find() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (arr[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Инициализируем пул потоков для поиска индекса.
     * Возвращаемое значение - индекс или -1, если объекта в массиве нет
     */
    public static int getIndex(Object[] t, Object obj) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParrallelFinder<>(obj, t, 0, t.length - 1));
    }
}
