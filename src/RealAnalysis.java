import java.io.*;

public class RealAnalysis {
    public static void realAnalysis() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("realworld_output.txt"));
        BufferedReader in = new BufferedReader(new FileReader("realworld_input.txt"));
        BufferedWriter out1 = new BufferedWriter(new FileWriter("MN_output.txt"));
        BufferedWriter out3 = new BufferedWriter(new FileWriter("FN_output.txt"));

        String s;
        String a[];
        in.readLine();
        while((s=in.readLine())!=null){
            a=s.split(",");
            if(a[5].contains("I6")) {
                if(a[1].compareTo("1")==0&&a[3].compareTo("0")==0){
                    out1.write(a[0].charAt(1)+""+a[0].charAt(2));
                    out1.newLine();
                }
                if(a[1].compareTo("2")==0&&a[3].compareTo("0")==0){
                    out3.write(a[0].charAt(1)+""+a[0].charAt(2));
                    out3.newLine();
                }
            }
        }
        in.close();
        out.close();
        out1.close();
        out3.close();

    }
    public static void realAnalysis_patient() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("realworld_input.txt"));
        BufferedWriter out1 = new BufferedWriter(new FileWriter("P_MN_output.txt"));
        BufferedWriter out3 = new BufferedWriter(new FileWriter("P_FN_output.txt"));

        String s,c,d;
        String a[],b[];
        double temp=0;
        in.readLine();
        while((s=in.readLine())!=null){
            a=s.split(",");
            if(a[5].contains("I6")) {
                if(a[2].compareTo("0")==0||a[2].compareTo("1")==0) {
                    temp = Double.parseDouble(a[2]);
                }else if(a[2].compareTo("*")==0){
                    temp=5.0;
                }
                else {
                    b = a[2].split("-");
                    c=b[0].replace("[","");
                    d=b[1].replace(")","");
                    temp=(Double.parseDouble(c)+Double.parseDouble(d))/2.0;
                }
                if(a[1].compareTo("1")==0&&a[3].compareTo("0")==0){
                    out1.write(a[0].charAt(1)+""+a[0].charAt(2)+"\t"+temp);
                    out1.newLine();
                }
                if(a[1].compareTo("2")==0&&a[3].compareTo("0")==0){
                    out3.write(a[0].charAt(1)+""+a[0].charAt(2)+"\t"+temp);
                    out3.newLine();
                }
            }
        }
        in.close();
        out1.close();
        out3.close();
    }
}
