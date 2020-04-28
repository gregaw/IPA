import java.util.*;

public class selectNode {
    public static String randomSelection(HashMap<String,Double> utility_result,Double epsilon){
        Set<String> keys;
        ArrayList<String> index_array=new ArrayList<>();
        ArrayList<Double> weight_array=new ArrayList<>();
        double score;

        Iterator it=sortByValue(utility_result).iterator();
        while(it.hasNext()){
            String key=(String)it.next();
            index_array.add(key);
            score=(1-utility_result.get(key));
            score*=epsilon*(1.0/3.0)*(1.0/2.0);
            score=Math.exp(score);
            weight_array.add(score);
            System.out.println(key+": "+utility_result.get(key));
            System.out.println(key+"(weight): "+score);
        }
        return index_array.get(Exponential.ExponentialMechanism(weight_array));
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
        Collections.reverse(list);
        return list;
    }
}
