package arsw.threads;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {			
			Thread.sleep(100);
			if(!carril.getIsStop()){
				paso++;
			}
			carril.setPasoOn(paso);
			carril.displayPasos(paso);
			
			if (paso == carril.size()) {
				synchronized (regl){
					carril.finish();
					AtomicInteger ubicacion=regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada();
					System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion.get());
					if (ubicacion.get() == 1){
						regl.setGanador(this.getName());
					}
				}
			}
		}
	}

	@Override
	public void run() {
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Carril getCarril(){
		return this.carril;
	}

}
