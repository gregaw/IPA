import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Vector;

public class Dataread {
    public static Vector<Tuple> getData(String filename){

        Vector<Tuple> inputData=new Vector<>();
        String s;   float max0=0,max1=0,max2=0,max3=0;
        String a[];
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            System.out.println("Data loading...");
            while((s=in.readLine())!=null){
                a=s.split(",");
                if(Float.parseFloat(a[0])<1.0||Float.parseFloat(a[0])>100.0) continue;
                /*
                if(max0<Float.parseFloat(a[0]))
                    max0=Float.parseFloat(a[0]);
                if(max2<Float.parseFloat(a[2]))
                    max2=Float.parseFloat(a[2]);

                 */
                inputData.add(new Tuple(s));
            }
            in.close();

        }catch(Exception e){

        }
        System.out.println("Data are loaded");
        return inputData;
    }
    public static HashMap<String, Vector<Double> > getEXPData(String filename){
        HashMap<String,Vector<Double>> information_loss=new HashMap<>();
        Vector<Double> temp_vector;
        String s,a[];

        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            System.out.println("EXP Data loading...");
            while((s=in.readLine())!=null){
                a=s.split("\t");
                if(a[0].compareTo("NODE_LEVEL")==0){
                    continue;
                }
                temp_vector=new Vector<>();
                if (information_loss.containsKey(a[0])) {
                    temp_vector=information_loss.get(a[0]);
                    temp_vector.set(0,temp_vector.get(0)+Double.parseDouble(a[1]));
                    temp_vector.set(0,temp_vector.get(1)+Double.parseDouble(a[2]));
                    temp_vector.set(0,temp_vector.get(2)+Double.parseDouble(a[3]));
                }else{
                    temp_vector.add(Double.parseDouble(a[1]));
                    temp_vector.add(Double.parseDouble(a[2]));
                    temp_vector.add(Double.parseDouble(a[3]));
                }
                information_loss.put(a[0],temp_vector);
            }
        }catch(Exception e){
        }
        return information_loss;
    }
}
