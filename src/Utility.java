import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
public class Utility {

//age 0~99, sex 0,1 cnt 1~400 operation 0,1 location 16(11,21~26,31~39)
    public static double generalizationError(String anonymized_QI,int[] level){
        double ncp=0.0;
        String[] qi=anonymized_QI.split(",");
        String[] temp;

        if(anonymized_QI.compareTo("*,*,*,*,*")==0)
            return 1.0;
        //age
        if(level[0]==1||level[0]==2){
            qi[0]=qi[0].replace("[","");
            qi[0]=qi[0].replace(")","");
            temp=qi[0].split("-");
            if(temp.length<2)
                System.out.println(qi[0]);
            ncp+=(Double.parseDouble(temp[1])-Double.parseDouble(temp[0]))/(100.0);
        }else if(level[0]==3){
            ncp+=1.0;
        }

        //sex
        if(level[1]==1)
            ncp+=1.0;
        //cnt
        if((level[2]==1||level[2]==2)){
            qi[2]=qi[2].replace("[","");
            qi[2]=qi[2].replace(")","");
            temp=qi[2].split("-");
            if(temp.length>1)
                ncp+=(Double.parseDouble(temp[1])-Double.parseDouble(temp[0]))/(100.0);
        }else if(level[0]==3){
            ncp+=1.0;
        }
        //operation
        if(level[3]==1)
            ncp+=1.0;
        //location
        if(level[4]==1){
            if(qi[4].compareTo("[21-27)")==0){
                ncp+=6.0/16.0;
            }else if(qi[4].compareTo("[31-40)")==0){
                ncp+=9.0/16.0;
            }
        }else if(level[4]==2){
            if(qi[4].compareTo("[21-40)")==0) {
                ncp += 6.0 / 16.0;
            }
        }else if(level[4]==3){
            ncp+=1.0;
        }
        ncp/=5.0;
        return ncp;
    }
    public static double distributionError(Vector<String> original_equivalent_class,Vector<String> anonymized_equivalent_class){
        double emd=0.0;
        int temp;
        HashMap<String,Integer> anonymized_map=new HashMap<>();
        HashMap<String,Integer> original_map=new HashMap<>();
        double[] a,b;
        int cnt=0;
        Set<String> keys;
        for(int i=0;i<anonymized_equivalent_class.size();i++){
            if(anonymized_map.containsKey(anonymized_equivalent_class.get(i))){
                temp=anonymized_map.get(anonymized_equivalent_class.get(i))+1;
                anonymized_map.put(anonymized_equivalent_class.get(i),temp);
            }else{
                anonymized_map.put(anonymized_equivalent_class.get(i),1);
            }
        }
        for(int i=0;i<original_equivalent_class.size();i++){
            if(original_map.containsKey(original_equivalent_class.get(i))){
                temp=original_map.get(original_equivalent_class.get(i))+1;
                original_map.put(original_equivalent_class.get(i),temp);
            }else{
                original_map.put(original_equivalent_class.get(i),1);
            }
        }
        a= new double[anonymized_map.size()];
        b= new double[anonymized_map.size()];

        keys=anonymized_map.keySet();
        for(String key:keys){
            a[cnt]=(double)anonymized_map.get(key)/(double)anonymized_equivalent_class.size();
            if(original_map.containsKey(key)){
                b[cnt]=(double)original_map.get(key)/(double)original_equivalent_class.size();
            }else
                b[cnt]=0.0;
        }
        emd=compute(a,b);
        return emd;
    }
    public static double compute(double[] a, double[] b) throws DimensionMismatchException {
            MathArrays.checkEqualLength(a, b);
            double lastDistance = 0;
            double totalDistance = 0;
            for (int i = 0; i < a.length; i++) {
                final double currentDistance = (a[i] + lastDistance) - b[i];
                totalDistance += FastMath.abs(currentDistance);
                lastDistance = currentDistance;
            }
            return totalDistance;
    }

    public static Vector<Double> getUtility(Vector<generalizedTuple> original_dataset, Vector<generalizedTuple> anonymized_dataset){
        double ncp=0.0,emd=0.0,rate=0.0;
        Vector<Double> utility_vector=new Vector<>();
        HashMap<String,Vector<String>> equivalent_class=new HashMap<>();
        HashMap<String,Vector<String>> equivalent_class_original=new HashMap<>();
        Vector<String> tempVector,tempVector_o;
        generalizedTuple temp,temp_o;
        String tempQI,tempSA;
        Set<String> keys;


        for(int i=0;i<original_dataset.size();i++){
            temp_o=original_dataset.get(i);
            tempQI=temp_o.getQI();
            tempSA=temp_o.getSA();
            tempVector=new Vector<>();

            if(equivalent_class_original.containsKey(tempQI)){
                tempVector=equivalent_class_original.get(tempQI);
                tempVector.add(tempSA);
                equivalent_class_original.put(tempQI,tempVector);
            }else{
                tempVector.add(tempSA);
                equivalent_class_original.put(tempQI,tempVector);
            }
        }

        for(int i=0;i<anonymized_dataset.size();i++){
            temp=anonymized_dataset.get(i);
            tempQI=temp.getQI();
            tempSA=temp.getSA();
            tempVector=new Vector<>();
            ncp+=generalizationError(tempQI,temp.getLevel());

            if(equivalent_class.containsKey(tempQI)){
                tempVector=equivalent_class.get(tempQI);
                tempVector.add(tempSA);
                equivalent_class.put(tempQI,tempVector);
            }else{
                tempVector.add(tempSA);
                equivalent_class.put(tempQI,tempVector);
            }
        }
        ncp/=anonymized_dataset.size();

        System.out.println("NCP of "+ Arrays.toString(anonymized_dataset.get(0).getLevel())+": "+ncp);

        keys=equivalent_class.keySet();
        for(String key:keys){
         //   if(key.compareTo("*,*,*,*,*")==0)   continue;
            tempVector=equivalent_class.get(key);
            tempVector_o=equivalent_class_original.get(key);
            rate+=(double)(tempVector.size()-tempVector_o.size())/tempVector.size();
            emd+=distributionError(tempVector_o,tempVector);
        }
        rate/= keys.size();

        System.out.println("Rate of "+ Arrays.toString(anonymized_dataset.get(0).getLevel())+": "+rate);

        emd/=keys.size();

        System.out.println("EMD of "+ Arrays.toString(anonymized_dataset.get(0).getLevel())+": "+emd);


        utility_vector.add(ncp);
        utility_vector.add(emd);
        utility_vector.add(rate);
        return utility_vector;
    }

}
