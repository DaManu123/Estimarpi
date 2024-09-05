import java.time.Duration;
import java.time.Instant;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;

//Autor: Manuel Munguia Rubio

public class Main {

    public static void main(String[] args) {

        Instant inicio = Instant.now();
        //long n=6600000000L;
//        estimar_pi(n);
        estimar_pi_paralelo(60000000,8);
        Instant fin = Instant.now();
        System.out.println("Tiempo de cálculo: " + Duration.between(inicio, fin));

    }

    //Funcion estimar pi
    public static void estimar_pi(long n_puntos) {

        long n_en_circulo = 0;
        double x, y, radio_cuadrado;
        int i = 0;

        SplittableRandom random = new SplittableRandom();
        for (i = 0; i < n_puntos; i++) {
            x = random.nextDouble(-1, 0 + Math.nextUp(1));
            y = random.nextDouble(-1, 0 + Math.nextUp(1));
            radio_cuadrado = (Math.pow(x, 2) + Math.pow(y, 2));
            if (radio_cuadrado <= 1)
                n_en_circulo += 1;
        }
        double pi_estimado = 4 * n_en_circulo / (double) n_puntos;
        System.out.println("Pi estimado es:" + pi_estimado);
    }
    public static void estimar_pi_paralelo(long n_puntos, int procesadores){
        try {

            ExecutorService executor = Executors.
                    newFixedThreadPool(procesadores);
            for (int i = 0; i < 10; i++) {
                executor.submit(new Estimador(n_puntos));
            }
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }catch (Exception e){
            System.out.println("Estimar_pi_Paralelo: " + e.getMessage());
        }
    }

}

