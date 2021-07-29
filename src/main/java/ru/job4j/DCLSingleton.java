package ru.job4j;

public class DCLSingleton {

    private DCLSingleton() {
    }

    private static final class DCLHolder {
        private static final DCLSingleton INST = new DCLSingleton();
    }

    public static DCLSingleton instOf() {
        return DCLHolder.INST;
    }
}
