# Escuela Colombiana de Ingeniería

## Arquitecturas de Software – ARSW

#### Taller – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

#### Mauricio Monroy y Samuel Rojas

##### Parte I – Antes de terminar la clase.

Creación, puesta en marcha y coordinación de hilos.

1. Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

    ![image](https://github.com/user-attachments/assets/40a2c620-b85f-48ed-8fa4-76774f63d4b3)
    
    8 de los núcleos son usados cuando se ejecuta el programa.

2. Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.

    Creación de los 3 hilos, cada uno resuelve un tercio del problema original.

    ![image](https://github.com/user-attachments/assets/ec6a92ad-84e3-4863-b277-9285c265ce45)


    8 de los núcleos son usados cuando se ejecuta el programa.

    ![image](https://github.com/user-attachments/assets/7a8528cf-14e0-417e-97f6-c15c829aec89)


4. Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismos.

##### Parte II 

Para este ejercicio se va a trabajar con un simulador de carreras de galgos (carpeta parte2), cuya representación gráfica corresponde a la siguiente figura:

![](./img/media/image1.png)

En la simulación, todos los galgos tienen la misma velocidad (a nivel de programación), por lo que el galgo ganador será aquel que (por cuestiones del azar) haya sido más beneficiado por el *scheduling* del procesador (es decir, al que más ciclos de CPU se le haya otorgado durante la carrera). El modelo de la aplicación es el siguiente:

![](./img/media/image2.png)

Como se observa, los galgos son objetos ‘hilo’ (Thread), y el avance de los mismos es visualizado en la clase Canodromo, que es básicamente un formulario Swing. Todos los galgos (por defecto son 17 galgos corriendo en una pista de 100 metros) comparten el acceso a un objeto de tipo RegistroLLegada. Cuando un galgo llega a la meta, accede al contador ubicado en dicho objeto (cuyo valor inicial es 1), y toma dicho valor como su posición de llegada, y luego lo incrementa en 1. El galgo que logre tomar el ‘1’ será el ganador.

Al iniciar la aplicación, hay un primer error evidente: los resultados (total recorrido y número del galgo ganador) son mostrados antes de que finalice la carrera como tal. Sin embargo, es posible que una vez corregido esto, haya más inconsistencias causadas por la presencia de condiciones de carrera.

Taller.

1.  Corrija la aplicación para que el aviso de resultados se muestre sólo cuando la ejecución de todos los hilos ‘galgo’ haya finalizado.
    Para esto tenga en cuenta:

    a.  La acción de iniciar la carrera y mostrar los resultados se realiza a partir de la línea 38 de MainCanodromo.

    b.  Puede utilizarse el método join() de la clase Thread para sincronizar el hilo que inicia la carrera, con la finalización de los hilos de los galgos.

2.  Una vez corregido el problema inicial, corra la aplicación varias veces, e identifique las inconsistencias en los resultados de las mismas viendo el ‘ranking’ mostrado en consola (algunas veces podrían salir resultados válidos, pero en otros se pueden presentar dichas inconsistencias). A partir de esto, identifique las regiones críticas del programa.

    Una de las inconsistencias es que varios galgos pueden compartir la misma posición, en este caso, 4 galgos quedaron en la primera posición:
        
    ![image](https://github.com/user-attachments/assets/e5f89ad8-10bf-4abe-85dc-ae2ff8481f10)


    Otra inconsistencia es cuando se muestra el galgo ganador, la cantidad de galgos varía:
    
    ![image](https://github.com/user-attachments/assets/e7694c79-06d9-4228-ab54-0121da89c9fb)
    ![image](https://github.com/user-attachments/assets/5f19ee5e-1a62-4154-a0e5-0e83490a6689)
    ![image](https://github.com/user-attachments/assets/5880afa8-8e7e-4b3b-8bd5-8bb07eef3330)


    Los botones "stop" y "continue" únicamente hacen que aparezca un mensaje en consola y no cumplen con su función.
    
    ![image](https://github.com/user-attachments/assets/d173bacf-8d05-40f0-b02e-e791d7551e5f)

Regiones críticas:

- Parte del método corra() de la clase Galgo, en especial las líneas 29 y 30, ya que es aquí donde se determinan las posiciones de llegada de los galgos

    ![image](https://github.com/user-attachments/assets/adf4c92b-9104-41bb-a750-17d597469abd)

- Los métodos getUltimaPosicionAlcanzada() y setUltimaPosicionAlcanzada() de la clase RegistroLlegada, ya que es aquí donde se está almacenando la ultima posición alcanzada por alguno de los galgos y su acceso es compartido entre todos los hilos.

  ![image](https://github.com/user-attachments/assets/01eb0f6e-6e29-4123-8db4-c4848514c326)

4.  Utilice un mecanismo de sincronización para garantizar que a dichas regiones críticas sólo acceda un hilo a la vez. Verifique los resultados.

5.  Implemente las funcionalidades de pausa y continuar. Con estas, cuando se haga clic en ‘Stop’, todos los hilos de los galgos deberían dormirse, y cuando se haga clic en ‘Continue’ los mismos deberían despertarse y continuar con la carrera. Diseñe una solución que permita hacer esto utilizando los mecanismos de sincronización con las primitivas de los Locks provistos por el lenguaje (wait y notifyAll).

## Criterios de evaluación

1. Funcionalidad.

    1.1. La ejecución de los galgos puede ser detenida y resumida consistentemente.
    
    1.2. No hay inconsistencias en el orden de llegada registrado.
    
2. Diseño.   

    2.1. Se hace una sincronización de sólo la región crítica (sincronizar, por ejemplo, todo un método, bloquearía más de lo necesario).
    
    2.2. Los galgos, cuando están suspendidos, son reactivados son sólo un llamado (usando un monitor común).

