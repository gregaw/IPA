import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;

public class sampling {
    void sampling(){
        String s,filename,temp,output_filename,output_filename2;
        String a[];
        int cnt=0;
        HashMap<String,String> map= new HashMap<String,String>();
        filename="./filtered_mapped_dataset.txt";
        output_filename="./experimental_input.txt";
        //output_filename2="./chk.txt";
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            BufferedWriter out = new BufferedWriter(new FileWriter(output_filename));
            //BufferedWriter out2 = new BufferedWriter(new FileWriter(output_filename2));
            while((s=in.readLine())!=null){
                a=s.split(",");
                temp=a[0].replace(".0","");
                /*
                if(map.containsKey(a[6])){
                    out2.write(s);
                    out2.newLine();
                    out2.write(map.get(a[6]));
                    out2.newLine();
                }*/
                if(Float.parseFloat(temp)<1) temp="0";
                if(Integer.parseInt(temp)>100) continue;
                if(a[0].compareTo("null")==0||a[1].compareTo("null")==0||a[2].compareTo("null")==0||a[3].compareTo("null")==0||a[4].compareTo("null")==0||a[5].compareTo("null")==0) continue;
                if(a[0].compareTo("nncd")==0||a[1].compareTo("nncd")==0||a[2].compareTo("nncd")==0||a[3].compareTo("nncd")==0||a[4].compareTo("nncd")==0||a[5].compareTo("nncd")==0) continue;

                map.put(a[6],temp+","+a[1]+","+a[2]+","+a[3]+","+a[4]+","+a[5]);
            }
            Iterator<String> keySetIterator = map.keySet().iterator();
            while (keySetIterator.hasNext()) {
                String key = keySetIterator.next();
                out.write(map.get(key));
                out.newLine();
                cnt++;
            }
            System.out.println(cnt);
            in.close();
            out.close();
          //  out2.close();
        }catch (Exception e){

        }

    }
}
