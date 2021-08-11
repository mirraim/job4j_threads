package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenOne() {
        int[][] example = new int[][] {
                {1, 2}
        };
        RolColSum.Sums[] rsl = RolColSum.sum(example);
        assertEquals(rsl[1].getRowSum(), 0);
    }

    @Test
    public void whenThree() {
        int[][] example = new int[][] {
                {1, 2},
                {1, 2},
                {1}
        };
        RolColSum.Sums[] rsl = RolColSum.sum(example);
        assertEquals(rsl[2].getRowSum(), 1);
    }


    @Test
    public void whenAsyncGetSum() throws ExecutionException, InterruptedException {
        int[][] example = new int[][] {
                {1, 2},
                {1, 2},
                {1}
        };
        int rsl = RolColSum.getSum(example, 2).get().getRowSum();
        assertEquals(rsl, 1);
    }

    @Test
    public void whenAsyncNone() throws ExecutionException, InterruptedException {
        int[][] example = new int[][]{};
        RolColSum.Sums[] rsl = RolColSum.asyncSum(example);
        assertEquals(rsl.length, 0);
    }

    @Test
    public void whenAsyncCube() throws ExecutionException, InterruptedException {
        int[][] example = new int[][] {
                {1, 2},
                {1, 2}
        };
        RolColSum.Sums[] rsl = RolColSum.asyncSum(example);
        assertEquals(rsl[1].getRowSum(), 3);
    }

    @Test
    public void whenAsyncThree() throws ExecutionException, InterruptedException {
        int[][] example = new int[][] {
                {1, 2},
                {1, 2},
                {1}
        };
        RolColSum.Sums[] rsl = RolColSum.asyncSum(example);
        assertEquals(rsl[2].getRowSum(), 1);
    }
}