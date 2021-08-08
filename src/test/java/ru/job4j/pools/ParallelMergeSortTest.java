package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelMergeSortTest {

    @Test
    public void sort() {
        int[] arr = new int[] {3, 7, 6, 5, 1, -1};
        assertArrayEquals(new int[] {-1, 1, 3, 5, 6, 7}, ParallelMergeSort.sort(arr));
    }
}