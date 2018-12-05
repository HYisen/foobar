package net.alexhyisen.foobar.module;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Greeting {
    private final long count;
    private final String msg;

    public long getCount() {
        return count;
    }

    public String getMsg() {
        return msg;
    }

    public Greeting(long count, String msg) {
        this.count = count;
        this.msg = msg;
    }

    public static void main(String[] args) {
        var one = LocalDateTime.now().minus(18, ChronoUnit.HOURS);
        System.out.print(one.toString());
    }
}
