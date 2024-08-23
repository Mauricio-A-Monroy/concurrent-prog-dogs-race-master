package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeBuffer{

    private AtomicInteger primesCount;

    public PrimeBuffer(){
        this.primesCount = new AtomicInteger(0);
    }

    public int getPrimeCount() {
        return primesCount.get();
    }

    public void incrementPrimeCount() {
        primesCount.incrementAndGet();
    }

    public void waitThreads(){
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startThreads(){
        notifyAll();
    }
}
