import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Exponential {
    public static int ExponentialMechanism(ArrayList<Double> weightList)
    {
        double weightSum = 0;

        for(double weight : weightList)
            weightSum += weight;

        if(weightSum <= 0)
        {
            return 0;
        }

        double rand = ThreadLocalRandom.current().nextDouble(0, weightSum);

        for(int i = 0; i < weightList.size(); i++)
        {
            rand -= weightList.get(i);

            if(rand < 0)
                return i;
        }

        return -1;
    }

}
