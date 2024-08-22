package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{


	int a,b;

	private PrimeBuffer primes;

	public PrimeFinderThread(int a, int b, PrimeBuffer primes) {
		super();
		this.a = a;
		this.b = b;
		this.primes = primes;
	}

	public void run(){
		for (int i=a;i<=b;i++){
			if (isPrime(i)){
				primes.addPrime(i);
				//System.out.println(i);
			}
		}
	}

	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public PrimeBuffer getPrimes() {
		return primes;
	}



	
}
