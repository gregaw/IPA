import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FullDomain {
    int age_level,sex_level,cnt_level,operation_level,location_level;

    /*
    public FullDomain(int age_level,int sex_level,int cnt_level, int operation_level, int location_level){
        this.age_level=age_level;
        this.sex_level=sex_level;
        this.cnt_level=cnt_level;
        this.operation_level=operation_level;
        this.location_level=location_level;
    }
    */
    public static HashMap<String,Double> lattice(Vector<Tuple> original_dataset,int[] max_level,double epsilon,int h) throws IOException{
        int lattice_height=0,searching=0;
        HashMap<String,Double> utility_result=new HashMap<>();
        Vector<generalizedTuple> result,generalized_dataset;
        BufferedWriter out=new BufferedWriter(new FileWriter("./result/"+epsilon+"_Information Loss.txt",true));
        PrintWriter pw=new PrintWriter(out,true);
        Double utility_metric=0.0;
        Vector<Double> utility_vector=new Vector<>();
        out.write("NODE_LEVEL\tNCP\tEMD\tRATE\tTUPLE_COUNT");
        out.newLine();
        for(int age=0;age<max_level[0]+1;age++){
            for(int sex=0;sex<max_level[1]+1;sex++){
                for(int cnt=0;cnt<max_level[2]+1;cnt++){
                    for(int operation=0;operation<max_level[3]+1;operation++){
                        for(int location=0;location<max_level[4]+1;location++){

                            generalized_dataset=generalization(original_dataset,new int[]{age,sex,cnt,operation,location});
                            System.out.println("Generalization finished");
                            result=suppression( generalized_dataset,h,epsilon );
                            System.out.println("Suppression finished");
                            result=insertion( result,epsilon );
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


    public static Vector<generalizedTuple> generalization(Vector<Tuple> original_dataset,int[] level){
        Vector<generalizedTuple> generalized_dataset=new Vector<>();
        int i;

        for(i=0;i<original_dataset.size();i++){
            generalized_dataset.add(Generalization.tupleGenearlization(original_dataset.get(i),level));
        }
        return generalized_dataset;
    }
    public static Vector<generalizedTuple> suppression(Vector<generalizedTuple> generalized_datatset, int h, double epsilon){
        Vector<generalizedTuple> suppressed_dataset=new Vector<>();
        HashMap<String, String> temp_map=new HashMap<>();
        HashMap<String,Integer> ec_set=chkEquivalentClass(generalized_datatset);
        Set<String> keys=ec_set.keySet();
        HashSet<String> suppressed_ec=new HashSet<>();

        Double m_noise;
        for(String key:keys){
            m_noise=Laplace.LaplaceMechanism(h,epsilon*0.1);
            if(m_noise<h)
                m_noise=(double)h;
            if(ec_set.get(key)<m_noise){
                suppressed_ec.add(key);
            }
        }

        for(int i=0;i<generalized_datatset.size();i++){
            generalizedTuple temp=generalized_datatset.get(i);
            if(suppressed_ec.contains(temp.getQI())){
                suppressed_dataset.add(new generalizedTuple("*,*,*,*,*,"+temp.getSA(),generalized_datatset.get(0).getLevel()));
            }else{
                suppressed_dataset.add(temp);
            }
        }

        return suppressed_dataset;
    }
    public static Vector<generalizedTuple> insertion(Vector<generalizedTuple> suppressed_datatset,double epsilon) {
        Vector<generalizedTuple> inserted_dataset=new Vector<>();
        HashMap<String,Vector<String>> ec_map=new HashMap<>();
        HashSet<String> sa_set=new HashSet<>();
        String tempQI,tempSA;
        Vector<String> tempVector;
        Set<String> keys;
        double noise;
        int tuple_count;
        String noise_sa;
        for(int i=0;i<suppressed_datatset.size();i++){
            tempQI=suppressed_datatset.get(i).getQI();
            tempSA=suppressed_datatset.get(i).getSA();
            sa_set.add(tempSA);
            tempVector=new Vector<>();
            if(ec_map.containsKey(tempQI)){
                tempVector=ec_map.get(tempQI);
                tempVector.add(tempSA);
                ec_map.put(tempQI,tempVector);
            }else{
                tempVector.add(tempSA);
                ec_map.put(tempQI,tempVector);
            }
        }
        keys=ec_map.keySet();
        for(String key:keys){
            tempVector=ec_map.get(key);
            noise=Laplace.LaplaceMechanism(tempVector.size(),(epsilon*(9.0/30.0)));
            tuple_count= (int) Math.round(noise);
            if(tuple_count<tempVector.size())
                tuple_count=tempVector.size();

            //original tuple add
            for(int i=0;i<tempVector.size();i++){
                inserted_dataset.add(new generalizedTuple(key+","+tempVector.get(i),suppressed_datatset.get(0).getLevel()));
            }

            //fake tuple add
            for(int i=0;i<tuple_count-tempVector.size();i++){
                noise_sa=randomSA(sa_set,tempVector,epsilon);
//                System.out.println(tempVector+": "+noise_sa);
                inserted_dataset.add(new generalizedTuple(key+","+noise_sa,suppressed_datatset.get(0).getLevel()));
            }

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
