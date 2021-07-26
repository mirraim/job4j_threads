package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Wget implements Runnable {
    private final String url;
    private final String target;
    private final int speed;

    public Wget(String url, String target, int speed) {
        this.url = url;
        this.target = target;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(target)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long finish = System.currentTimeMillis();
                long experation = finish - start;
                if (speed > experation) {
                    Thread.sleep(speed - experation);
                }
                start = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Wrong arguments");
        }
        String url = validatePath(args[0]);
        String target = validatePath(args[1]);
        int speed = validateSpeed(args[2]);
        Thread wget = new Thread(new Wget(url, target, speed));
        wget.start();
        wget.join();
    }

    private static String validatePath(String path) {
        if (Files.notExists(Paths.get(path))) {
            throw new IllegalArgumentException("File is not exists");
        }
        return path;
    }

    private static int validateSpeed(String speed) {
        int rsl = 0;
        try {
            rsl = Integer.parseInt(speed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Speed must be number");
        }
        return rsl;
    }
}
