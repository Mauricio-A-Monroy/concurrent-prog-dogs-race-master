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

		Scanner scanner = new Scanner(System.in);
		MonitorThread monitorThread = new MonitorThread(1, 30000000);
		monitorThread.startThreads();

		while (true) {
			try {
				// Espera 5 segundos
				Thread.sleep(5000);

				System.out.println(monitorThread.getPrimesSize());
				//monitorThread.stopThreads();
				System.out.println(monitorThread.stillAlive());
				System.out.println("Han pasado 5 segundos. Presiona Enter para continuar...");
				scanner.nextLine(); // Espera a que el usuario presione Enter

			} catch (InterruptedException e) {
				System.out.println("El programa fue interrumpido.");
				break;
			}

			if (!monitorThread.stillAlive()){
				System.out.println("Todos los hilos han terminado, se encontraron " +  monitorThread.getPrimesSize() + " primos");
				break;
			}
		}
		
	}
	
}
