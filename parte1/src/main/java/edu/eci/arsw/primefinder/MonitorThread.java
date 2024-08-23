package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MonitorThread {

    private int n;
    private int higherRange;
    private ArrayList<PrimeFinderThread> threads;
    private PrimeBuffer primeBuffer;

    public MonitorThread(int n, int higherRange){
        this.n = n;
        this.higherRange = higherRange;
        this.threads = new ArrayList<>();
        primeBuffer = new PrimeBuffer();
    }

    public void startThreads(){
        int threadRange = higherRange / n;

        if(n % 2 == 0){
            for(int i = 0; i < n; i++){
                threads.add(new PrimeFinderThread(i * threadRange, (i+1) * threadRange, primeBuffer));
                threads.get(i).start();
            }
        }
        else {
            for(int i = 0; i < n - 1; i++){
                threads.add(new PrimeFinderThread(i * threadRange, (i+1) * threadRange, primeBuffer));
                threads.get(i).start();
            }
            threads.add(new PrimeFinderThread((n-1) * threadRange, higherRange, primeBuffer));
            threads.get(n-1).start();
        }
    }

    public void pauseThreads() {
        primeBuffer.pauseBuffer();
    }

    public void resumeThreads() {
        primeBuffer.resumeBuffer();
    }

    public int getPrimeCount(){
        return primeBuffer.getPrimeCount();
    }

    public boolean stillAlive(){
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;
    }
}
