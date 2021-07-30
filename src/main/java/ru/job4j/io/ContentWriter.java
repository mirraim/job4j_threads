package ru.job4j.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ContentWriter {
    private final File file;

    public ContentWriter(File file) {
        this.file = file;
    }
    public synchronized void saveContent(String content) throws IOException {
        try (OutputStream o = new FileOutputStream(file)) {
            o.write(content.getBytes());
        }
    }
}
