package edu.eci.arsw.primefinder;

import java.util.List;

public class PrimeBuffer {
    private List<Integer> primes;
    private boolean isPaused;

    public PrimeBuffer(List<Integer> primes){
        this.primes = primes;
        this.isPaused = false;
    }

    public synchronized void addPrime(int prime){
        while(isPaused){
            try{
                wait();
            }
            catch(InterruptedException e){
                System.out.println("An exception was caught");
            }
        }
        this.primes.add(prime);
        notifyAll();
    }

    public synchronized void turn(){
        this.isPaused = !this.isPaused;
        notifyAll();
    }

    public List<Integer> getPrimes(){
        return this.primes;
    }
}