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
        primeBuffer = new PrimeBuffer(new ArrayList<>());
    }

    public void startThreads(){
        int threadRange = higherRange / n;

        if(n % 2 == 0){
            for(int i = 0; i < n; i++){
                threads.add(new PrimeFinderThread(i * threadRange, ((i+1) * threadRange), primeBuffer));
                threads.get(i).start();
            }
        }
        else {
            for(int i = 0; i < n - 1; i++){
                threads.add(new PrimeFinderThread(i * threadRange, ((i+1) * threadRange), primeBuffer));
                threads.get(i).start();
            }
            threads.add(new PrimeFinderThread((n-1) * threadRange, 30000000, primeBuffer));
            threads.get(n-1).start();
        }
    }

    public void stopThreads(){
        primeBuffer.waitThreads();
    }

    public int getPrimesSize(){
        return primeBuffer.getPrimesSize();
    }

    public boolean stillAlive(){
        int livingThreads = 0;
        for(Thread i : threads){
            if(i.isAlive()){
                livingThreads += 1;
            }
        }
        boolean ans = false;

        if (livingThreads == this.n){
            ans = true;
        }

        return ans;
    }
}
