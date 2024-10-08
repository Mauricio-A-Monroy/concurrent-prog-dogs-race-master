package edu.eci.arsw.primefinder;

import java.util.ArrayList;

public class MonitorThread {

    private int n;
    private int higherRange;
    private ArrayList<PrimeFinderThread> threads;
    private PrimeBuffer primeBuffer;

    public MonitorThread(int n, int higherRange){
        this.n = n;
        this.higherRange = higherRange;
        this.threads = new ArrayList<>();
        this.primeBuffer = new PrimeBuffer(new ArrayList<>());
    }

    public void startThreads(){
        int threadRange = higherRange / n;

        if(n % 2 == 0){
            for(int i = 0; i < n; i++){
                threads.add(new PrimeFinderThread((i * threadRange) + 1, (i+1) * threadRange, primeBuffer));
                threads.get(i).start();
            }
        }
        else {
            for(int i = 0; i < n - 1; i++){
                threads.add(new PrimeFinderThread((i * threadRange) + 1, (i+1) * threadRange, primeBuffer));
                threads.get(i).start();
            }
            threads.add(new PrimeFinderThread(((n-1) * threadRange) + 1, higherRange, primeBuffer));
            threads.get(n-1).start();
        }
    }

    public void turnThreads() {
        primeBuffer.turn();
    }

    public int getPrimeCount(){
        return primeBuffer.getPrimes().size();
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