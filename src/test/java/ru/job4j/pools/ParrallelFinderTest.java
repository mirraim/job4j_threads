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
}