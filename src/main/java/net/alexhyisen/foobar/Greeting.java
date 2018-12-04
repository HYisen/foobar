package net.alexhyisen.foobar;

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
}
