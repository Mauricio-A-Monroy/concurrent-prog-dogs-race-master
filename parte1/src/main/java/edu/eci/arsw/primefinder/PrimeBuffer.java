package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeBuffer{

    private AtomicInteger primesCount;
    private boolean isPaused;
    private final Object pauseLock = new Object();

    public PrimeBuffer(){
        this.primesCount = new AtomicInteger(0);
        this.isPaused = false;
    }

    public int getPrimeCount() {
        return primesCount.get();
    }

    public void incrementPrimeCount() {
        synchronized (pauseLock) {
            while (isPaused) {
                try {
                    pauseLock.wait();  // Pausa el hilo si está marcado como pausado
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            primesCount.incrementAndGet();
        }
    }

    public void pauseBuffer() {
        synchronized (pauseLock) {
            this.isPaused = true;
        }
    }

    public void resumeBuffer() {
        synchronized (pauseLock) {
            this.isPaused = false;
            pauseLock.notifyAll();  // Despertar todos los hilos que están en espera
        }
    }
}
