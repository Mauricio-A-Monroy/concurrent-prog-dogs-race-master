package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeBuffer{

    private ArrayList<Integer> primes;

    public PrimeBuffer(ArrayList<Integer> primes){
        this.primes = primes;
    }

    public int getPrimesSize(){
        return this.primes.size();
    }

    public synchronized void addPrime(int prime){
        this.primes.add(prime);
        //System.out.println(primes);
        notifyAll();
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
