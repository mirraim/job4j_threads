package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParrallelFinderTest {

    @Test
    public void getIndex() {
        Integer[] arr = new Integer[] {3, 2, 1, 0};
        assertEquals(0, ParrallelFinder.getIndex(arr, 3));
    }

    @Test
    public void whenNoIndex() {
        Integer[] arr = new Integer[] {3, 2, 1, 0};
        assertEquals(-1, ParrallelFinder.getIndex(arr, 12));
    }

    @Test
    public void whenMany() {
        Integer[] arr = new Integer[] {0, 1, 2, 3, 24, 5, 6, 7, 8, 9, 10,
                                        11, 12, 13, 4, 15, 16, 17, 18, 19, 20,
                                        21, 22, 23, 14, 25, 26, 27, 28, 29, 30};
        assertEquals(0, ParrallelFinder.getIndex(arr, 0));
    }
}