import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class filtering {
    void filtering(){
        String s,filename,output_filename;
        String a[];
        int i;
        filename="D:/DBLAB/NPS-dataset/NPS_2011/sample_2011_20_01.txt";
        output_filename="./filtered_dataset.txt";
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            BufferedWriter out = new BufferedWriter(new FileWriter(output_filename));

            while((s=in.readLine())!=null){
                a=s.split(",");
                out.write(a[2]+","+a[3]+","+a[16]+","+a[24]+","+a[34]+","+a[35]+","+a[33]);
                out.newLine();
            }
            in.close();
            filename="D:/DBLAB/NPS-dataset/NPS_2011/sample_2011_20_02.txt";
            in = new BufferedReader(new FileReader(filename));
            s=in.readLine();
            while((s=in.readLine())!=null){
                a=s.split(",");
                out.write(a[2]+","+a[3]+","+a[16]+","+a[24]+","+a[34]+","+a[35]+","+a[33]);
                out.newLine();
            }
            in.close();
            filename="D:/DBLAB/NPS-dataset/NPS_2011/sample_2011_20_03.txt";
            in = new BufferedReader(new FileReader(filename));
            s=in.readLine();
            while((s=in.readLine())!=null){
                a=s.split(",");
                out.write(a[2]+","+a[3]+","+a[16]+","+a[24]+","+a[34]+","+a[35]+","+a[33]);
                out.newLine();
            }
            out.close();
        }catch(Exception e){

        }
    }
}
