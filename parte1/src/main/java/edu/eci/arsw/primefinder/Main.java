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
		MonitorThread monitorThread = new MonitorThread(100, 30000000);
		monitorThread.startThreads();

		while (monitorThread.stillAlive()) {
			try {
				// Espera 5 segundos
				Thread.sleep(5000);

				monitorThread.turnThreads();
				System.out.println(monitorThread.getPrimeCount());
				System.out.println("Han pasado 5 segundos. Presiona Enter para continuar...");
				scanner.nextLine(); // Espera a que el usuario presione Enter
				//System.out.println(monitorThread.getPrimeCount()); descomentar para verificar que la cantidad de primos no cambia mientras se da el enter
				monitorThread.turnThreads();

			}
			catch (InterruptedException e) {
				System.out.println("El programa fue interrumpido.");
				break;
			}

			if (!monitorThread.stillAlive()){
				System.out.println("Todos los hilos han terminado, se encontraron " +  monitorThread.getPrimeCount() + " primos");
			}
		}
		
	}
	
}
