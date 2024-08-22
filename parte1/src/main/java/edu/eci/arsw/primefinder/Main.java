package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class Main {

	public static void main(String[] args) {
		
		//PrimeFinderThread pft=new PrimeFinderThread(0, 30000000);
		/**
		PrimeFinderThread pft1=new PrimeFinderThread(0, 10000000);
		PrimeFinderThread pft2=new PrimeFinderThread(10000001, 20000000);
		PrimeFinderThread pft3=new PrimeFinderThread(20000001, 30000000);
		
		pft1.start();
		pft2.start();
		pft3.start();
		**/
		int n = 3;
		int threadRange = 30000000 / n;

		long startTime = System.currentTimeMillis();

		ArrayList<PrimeFinderThread> threads = new ArrayList<>();
		PrimeBuffer primeBuffer = new PrimeBuffer(new ArrayList<>());

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

		int stillAlive = 0;

		while(stillAlive  != n){
			for(Thread i : threads){
				if(i.isAlive()){
					stillAlive += 1;
				}
			}

			if((System.currentTimeMillis() - startTime) >= 5000){
				System.out.println(stillAlive);
				primeBuffer.waitThreads();
				System.out.println(primeBuffer.getPrimesSize());
				System.out.println("Oprima enter");
				Scanner scanner = new Scanner(System.in);
				scanner.nextLine();
				primeBuffer.startThreads();
			}
		}


		
	}
	
}
