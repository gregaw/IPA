import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class mapping {
    void mapping(){
        String s,filename,filename2,output_filename;
        String a[];
        int cnt=0;
        HashMap<String,String> map= new HashMap<String,String>();
        filename="./filtered_dataset.txt";
        filename2="./map_table.txt";
        output_filename="./filtered_mapped_dataset.txt";
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename2));
            in.readLine();
            while((s=in.readLine())!=null){
                s=s.replace("\"","");
                a=s.split(",");
                map.put(a[0],a[7]);
            }
            in.close();
        }catch (Exception e){

        }

        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            BufferedWriter out = new BufferedWriter(new FileWriter(output_filename));
            in.readLine();
            String temp;
            while((s=in.readLine())!=null){
                s=s.replace("\"","");
                a=s.split(",");
                if(a[5].contains("nncd"))
                    continue;
                temp=map.get(a[4]);
                s=a[0]+","+a[1]+","+a[2]+","+a[3]+","+temp+","+a[5]+","+a[6];
                out.write(s);
                out.newLine();
                cnt++;
            }
            in.close();
            out.close();
            System.out.println(cnt);
        }catch(Exception e){

        }
    }
}
