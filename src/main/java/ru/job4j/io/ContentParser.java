package ru.job4j.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class ContentParser {

    public String parseContent(Predicate<Character> filter, InputStream in) throws IOException {
        StringBuffer output = new StringBuffer();
        byte[] buffer = new byte[1024];
        synchronized (this) {
            while (in.read(buffer, 0, 1024) > 0) {
                output.append(filterBuff(filter, buffer));
            }
            return output.toString();
        }
    }

    private String filterBuff(Predicate<Character> filter, byte[] buffer) {
        StringBuffer output = new StringBuffer();
        for (byte data : buffer) {
            if (filter.test((char) data)) {
                output.append((char) data);
            }
        }
        return output.toString();
    }

}
