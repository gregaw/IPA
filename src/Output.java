import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Output {
    public static void print(Vector<generalizedTuple> anonymized_dataset, String filename) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));

        for(int i=0;i<anonymized_dataset.size();i++) {
            out.write(anonymized_dataset.get(i).toString());
            out.newLine();
        }
        out.close();
    }
    public static void resultPrint(String filename, HashMap<String,Double> utility_result,String result) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));

        Iterator it=sortByValue(utility_result).iterator();
        out.write("Result is : "+result);
        out.newLine();
        while(it.hasNext()){
            String temp=(String)it.next();
            out.write(temp+": "+utility_result.get(temp));
            out.newLine();
        }
        out.close();
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
