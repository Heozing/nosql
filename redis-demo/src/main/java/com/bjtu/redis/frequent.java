package com.bjtu.redis;

public class frequent {

    private String time;
    private long counter;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "frequent{" +
                "counter=" +counter +'\''+
                ",time="+time+'\'' +
                '}';
    }
}
