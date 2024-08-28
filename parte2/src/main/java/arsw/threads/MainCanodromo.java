package arsw.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainCanodromo {

    private static Galgo[] galgos;

    private static Canodromo can;

    private static RegistroLlegada reg = new RegistroLlegada();

    private final Object pauseLock = new Object();

    public static void main(String[] args) {
        can = new Canodromo(5, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        //Acción del botón start
        can.setStartAction(
                new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
						//como acción, se crea un nuevo hilo que cree los hilos
                        //'galgos', los pone a correr, y luego muestra los resultados.
                        //La acción del botón se realiza en un hilo aparte para evitar
                        //bloquear la interfaz gráfica.
                        ((JButton) e.getSource()).setEnabled(false);
                        new Thread() {
                            public void run() {
                                try {
                                    for (int i = 0; i < can.getNumCarriles(); i++) {
                                        //crea los hilos 'galgos'
                                        galgos[i] = new Galgo(can.getCarril(i), "" + i, reg);
                                        //inicia los hilos
                                        galgos[i].start();

                                    }

                                    for (Galgo galgo : galgos) {
                                        galgo.join();
                                    }

                                    can.winnerDialog(reg.getGanador(), can.getNumCarriles());
                                    System.out.println("El ganador fue:" + reg.getGanador());
                                }
                                catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }.start();

                    }
                }
        );

        can.setStopAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (Galgo galgo : galgos) {
                            galgo.pauseRace();
                        }
                        System.out.println("Carrera pausada!");
                    }
                }
        );

        can.setContinueAction(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        for (Galgo galgo : galgos) {
                            galgo.continueRace();
                        }
                        System.out.println("Carrera reanudada!");
                    }
                }
        );

    }

}
