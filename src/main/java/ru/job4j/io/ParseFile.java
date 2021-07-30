package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        try (InputStream i = new FileInputStream(file)) {
            return new ContentParser().parseContent(filter, i);
        }
    }
}
