package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

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

		//MonitorThread monitorThread = new MonitorThread(3, 30000000);
		//monitorThread.startThreads();

		Scanner scanner = new Scanner(System.in);

		while (true) {
			try {
				// Espera 5 segundos
				MonitorThread monitorThread = new MonitorThread(1, 30000000);
				monitorThread.startThreads();
				Thread.sleep(5000);

				System.out.println(monitorThread.getPrimesSize());
				//monitorThread.stopThreads();
				System.out.println("Han pasado 5 segundos. Presiona Enter para continuar...");
				scanner.nextLine(); // Espera a que el usuario presione Enter

			} catch (InterruptedException e) {
				System.out.println("El programa fue interrumpido.");
				break;
			}
		}

		/**
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
		}**/


		
	}
	
}
