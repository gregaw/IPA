import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FullDomain_naive {
    public static HashMap<String,Double> lattice(Vector<Tuple> original_dataset, int[] max_level, double epsilon, int h) throws IOException {
        int lattice_height=0,searching=0;
        HashMap<String,Double> utility_result=new HashMap<>();
        Vector<generalizedTuple> result,generalized_dataset;
        BufferedWriter out=new BufferedWriter(new FileWriter("./result/"+epsilon+"_Information Loss.txt",true));
        PrintWriter pw=new PrintWriter(out,true);
        Double utility_metric=0.0;
        Vector<Double> utility_vector=new Vector<>();
        out.write("NODE_LEVEL\tNCP\tEMD\tRATE\tTUPLE_COUNT");
        out.newLine();
        for(int age=1;age<max_level[0]+1;age++){
            for(int sex=0;sex<max_level[1]+1;sex++){
                for(int cnt=1;cnt<max_level[2]+1;cnt++){
                    for(int operation=0;operation<max_level[3]+1;operation++){
                        for(int location=1;location<max_level[4]+1;location++){

                            generalized_dataset=generalization(original_dataset,new int[]{age,sex,cnt,operation,location});
                            System.out.println("Generalization finished");
                            result=insertion( generalized_dataset,epsilon );
                            System.out.println("Insertion finished");
                            utility_vector=Utility.getUtility(generalized_dataset,result);
                            for(int i=0;i<utility_vector.size();i++)
                                utility_metric+=utility_vector.get(i);
                            utility_metric/=(double)utility_vector.size();
                            utility_result.put(""+age+sex+cnt+operation+location,utility_metric);
                            System.out.println("Utility calculation finished");

                            out.write(age+""+sex+""+cnt+""+operation+""+location+"\t" + utility_vector.get(0)+"\t"+utility_vector.get(1)+"\t"+utility_vector.get(2)+"\t"+result.size());
                            out.newLine();


                            System.out.println("Index: "+age+sex+cnt+operation+location+"\tDataset size: "+result.size());

                        }
                    }
                }
            }
        }
        out.close();
        return utility_result;
    }
    public static HashMap<String,Double> lattice_last(Vector<Tuple> original_dataset, int[] max_level, double epsilon, int h) throws IOException {
        int lattice_height=0,searching=0;
        HashMap<String,Double> utility_result=new HashMap<>();
        Vector<generalizedTuple> result,generalized_dataset;
        BufferedWriter out=new BufferedWriter(new FileWriter("./result/"+epsilon+"_Information Loss.txt",true));
        PrintWriter pw=new PrintWriter(out,true);
        Double utility_metric=0.0;
        Vector<Double> utility_vector=new Vector<>();
        out.write("NODE_LEVEL\tNCP\tEMD\tRATE\tTUPLE_COUNT");
        out.newLine();

                            generalized_dataset=generalization(original_dataset,new int[]{0,0,0,0,0});
                            System.out.println("Generalization finished");
                            result=insertion( generalized_dataset,epsilon );

                            System.out.println("Insertion finished");
                            utility_vector=Utility.getUtility(generalized_dataset,result);
                            for(int i=0;i<utility_vector.size();i++)
                                utility_metric+=utility_vector.get(i);
                            utility_metric/=(double)utility_vector.size();
                            utility_result.put("00000",utility_metric);
                            System.out.println("Utility calculation finished");

                            out.write("00000"+"\t" + utility_vector.get(0)+"\t"+utility_vector.get(1)+"\t"+utility_vector.get(2)+"\t"+result.size());
                            out.newLine();


                            System.out.println("Index: 31313\tDataset size: "+result.size());


        out.close();
        return utility_result;
    }
    public static Vector<generalizedTuple> generalization(Vector<Tuple> original_dataset,int[] level){
        Vector<generalizedTuple> generalized_dataset=new Vector<>();
        int i;

        for(i=0;i<original_dataset.size();i++){
            generalized_dataset.add(Generalization.tupleGenearlization(original_dataset.get(i),level));
        }
        return generalized_dataset;
    }
    public static Vector<generalizedTuple> insertion(Vector<generalizedTuple> suppressed_datatset,double epsilon) {
        Vector<generalizedTuple> inserted_dataset=new Vector<>();
        HashMap<String,HashMap<String,Integer>> ec_map=new HashMap<>();
        HashSet<String> sa_set=new HashSet<>();
        String tempQI,tempSA;
        HashMap<String,Integer> tempVector;
        HashMap<String,Integer> tempMap;
        Set<String> keys,keys2;
        double noise;
        int tuple_count;
        String noise_sa;


        for(int i=0;i<suppressed_datatset.size();i++){
            tempQI=suppressed_datatset.get(i).getQI();
            tempSA=suppressed_datatset.get(i).getSA();
            sa_set.add(tempSA);

            if(ec_map.containsKey(tempQI)) {
                tempMap=ec_map.get(tempQI);
                if(tempMap.containsKey(tempSA)){
                    tempMap.put(tempSA,tempMap.get(tempSA)+1);
                }else{
                    tempMap.put(tempSA,1);
                }
            }
            else {
                tempMap=new HashMap<>();
                tempMap.put(tempSA,1);
                ec_map.put(tempQI,tempMap);
            }
        }

        keys=ec_map.keySet();
        for(String key:keys){
            tempMap=ec_map.get(key);
            System.out.println(key+","+tempMap.size());
            Iterator it1= sa_set.iterator();
            while(it1.hasNext()){
                String key2=(String)it1.next();
                if(tempMap.containsKey(key2)){
                    noise=Laplace.LaplaceMechanism(tempMap.get(key2),(epsilon));
                    if(noise<tempMap.get(key2)) noise=tempMap.get(key2);
                    tempMap.put(key2,(int)Math.round(noise));
                }else{
                    noise=Laplace.LaplaceMechanism(0,(epsilon));
                    if(noise<0) noise=0;
                    tempMap.put(key2,(int)Math.round(noise));
                }
            }
            keys2=tempMap.keySet();
            for(String key2:keys2){
                tuple_count=tempMap.get(key2);
                for(int i=0;i<tuple_count;i++){
                    inserted_dataset.add(new generalizedTuple(key+","+key2,suppressed_datatset.get(0).getLevel()));
                }
            }
//            inserted_dataset.add(new generalizedTuple(key+","+noise_sa,suppressed_datatset.get(0).getLevel()));

        }
        return inserted_dataset;
    }
    public static String randomSA(HashSet<String> sa_set,Vector<String> ec_sa,double epsilon)  {
        String result,temp;
        HashMap<String,Integer> sa_count=new HashMap<>();
        TreeMap<String,Double> prob_map=new TreeMap<>();
        ArrayList<Double> weight=new ArrayList<>();
        ArrayList<String> sa_list=new ArrayList<>();
        Double score=0.0;
        double de=ec_sa.size();
        HashSet<String> temp_set=new HashSet<>(sa_set);
        double domain=temp_set.size();
        for(int i=0;i<ec_sa.size();i++){
            temp=ec_sa.get(i);
            if(sa_count.containsKey(temp)){
                sa_count.put(temp,sa_count.get(temp)+1);
            }else{
                sa_count.put(temp,1);
            }
        }
        Iterator it=sortByValue(sa_count).iterator();

        while(it.hasNext()){
            String key=(String)it.next();
            int value=sa_count.get(key);
            sa_list.add(key);

//            score=Math.exp( ((value / (de + 1.0))*(epsilon*((9.0/30.0))))*(1.0/2.0) );
            score=value/(de+1.0); //ratio
            score=score*epsilon*(9.0/30.0)*(1.0/2.0); //budget
            score=Math.exp(score);
            weight.add(score);
            temp_set.remove(key);
        }

        Iterator it1= temp_set.iterator();
        while(it1.hasNext()){
            String key=(String)it1.next();
            sa_list.add(key);
//            score=Math.exp( ((1.0 /((domain-ec_sa.size())*(de + 1.0)) )*(epsilon*((9.0/30.0))))*(1.0/2.0) );
            score=1.0/((de+1.0)*(domain-ec_sa.size())); //ratio
            score=score*epsilon*(9.0/30.0)*(1.0/2.0); //budget
            score=Math.exp(score);
            weight.add(score);
        }

        result=sa_list.get(Exponential.ExponentialMechanism(weight));

        return result;
    }
    public static HashMap<String,Integer>  chkEquivalentClass(Vector<generalizedTuple> gt){
        HashMap<String,Integer> ec=new HashMap<>();

        for(int i=0;i<gt.size();i++){
            if(ec.containsKey(gt.get(i).getQI())){
                ec.put(gt.get(i).getQI(),(ec.get(gt.get(i).getQI())+1 ));
            }else
                ec.put(gt.get(i).getQI(),1);
        }

        return ec;
    }
    public static List sortByValue(final Map map) {
        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list,new Comparator() {
            public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);

                return ((Comparable) v2).compareTo(v1);
            }
        });
        //Collections.reverse(list);
        return list;
    }
}
