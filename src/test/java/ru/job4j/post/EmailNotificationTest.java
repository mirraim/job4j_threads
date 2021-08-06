package ru.job4j.post;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailNotificationTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenNull() {
        EmailNotification post = new EmailNotification();
        post.emailTo(null);
    }
}