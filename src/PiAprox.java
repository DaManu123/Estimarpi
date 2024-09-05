import java.util.SplittableRandom;
import java.util.concurrent.Callable;

public class PiAprox implements Callable<Double> {
    long n_points;
    public PiAprox(long n_points){
        this.n_points = n_points;
    }
    @Override
    public Double call() throws Exception{
        long within_circle = 0;
        double x,y, radius_squared;
        SplittableRandom random = new SplittableRandom();
        for (long i = 0; i < this.n_points; i++) {
            x = random.nextDouble(-1, 0 + Math.nextUp(1));
            y = random.nextDouble(-1, 0 + Math.nextUp(1));
            radius_squared = Math.pow(x, 2) + Math.pow(y, 2);
            if (radius_squared <= 1){
                within_circle += 1;
            }


        }
        double pi_estimate = 4.0 * within_circle / (double) this.n_points;
        return pi_estimate;
    }

}
