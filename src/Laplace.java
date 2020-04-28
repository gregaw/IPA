import org.apache.commons.math3.distribution.LaplaceDistribution;

import java.util.Random;

public class Laplace {
    public static double LaplaceMechanism(double count, double epsilon)
    {
        LaplaceDistribution lap = new LaplaceDistribution(0, 1/epsilon);
        Random generator = new Random();
        double randomProbability = generator.nextDouble();
        double noise = lap.inverseCumulativeProbability(randomProbability);

        return count + noise;
    }
}
