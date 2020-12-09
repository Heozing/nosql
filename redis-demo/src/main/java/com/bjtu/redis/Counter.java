package com.bjtu.redis;

public class Counter {
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
        return "Counter{" +
                "counter=" +counter +'\''+
                ",time="+time+'\'' +
                '}';
    }
}



