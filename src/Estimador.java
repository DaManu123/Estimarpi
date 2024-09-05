import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Estimador implements Runnable{
    long n_puntos;
    public void run(){
        this.estimar_pi();
    }
    public Estimador(long n_puntos){
        this.n_puntos = n_puntos;
    }
    public void estimar_pi(){
        long n_en_circulo=0;
        double x,y, radio_cuadrado;
        SplittableRandom random = new SplittableRandom();
        for(long i=0; i<this.n_puntos; i++){
            x = random.nextDouble(-1, 0+ Math.nextUp(1));
            y = random.nextDouble(-1, 0+ Math.nextUp(1));
            radio_cuadrado = (Math.pow(x,2)+Math.pow(y,2));
            if(radio_cuadrado <= 1){
                n_en_circulo += 1;
            }

        }
        System.out.println("N en circulo: "+n_en_circulo+" N en puntos:" + n_puntos);
        double pi_estimado = 4 * n_en_circulo / (double)n_puntos;
        System.out.println("Pi estimado es: " + pi_estimado);
    }
    public static Double run_test_futures(long n_points, int n_repeats, int processors){
        Double mejor_pi = 0.0;
        try{
            //int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(processors);
            List<Future<Double>> lista = new ArrayList<Future<Double>>();
            Callable callable = new PiAprox(n_points);
            for (int i = 0; i < n_repeats; i++) {
                Future<Double> future_pi = executor.submit(callable);
                lista.add(future_pi);
            }
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
            Double pi = 3.14159265;
            Double menor = pi;
            Double menor_dif = pi;
            Double dif = 0.0;
            for (Future<Double> future : lista) {
                dif =    pi - future.get();
                String d = String.format("%,8f",dif);
                System.out.println("Dif"+d);
                if (Math.abs(dif) < menor){
                    menor = dif;
                    menor_dif = future.get();
                    System.out.println("Pi:"+menor+" diferencia: "+d);
                }
            }
            executor.shutdown();
            mejor_pi = menor_dif;
        }
        catch (Exception e){
            System.out.println("Test_futures:"+e.getMessage());
        }
        return mejor_pi;
    }
}
