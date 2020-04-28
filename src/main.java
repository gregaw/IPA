import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

public class main {
    public static void main(String[] args) throws IOException {
        int max=0,index=-1,cnt=0,h;
        int[] level;
        double epsilon=1.0;
        String result;
        HashMap<String, Double> utility_result;
        Vector<Tuple> original_datset=new Vector<>();
        Set<String> keys;

        RealAnalysis.realAnalysis();
        RealAnalysis.realAnalysis_patient();





        /********************************************exp
        original_datset=Dataread.getData("experimental_input.txt");
        level= new int[]{3, 1, 3, 1, 3};
        BufferedWriter clear=new BufferedWriter(new FileWriter("./result/Information Loss.txt"));
        clear.close();
        System.out.println("Original Dataset size: "+original_datset.size());
        h=2;
        for(int i=0;i<10;i++) {
            utility_result=FullDomain.lattice(original_datset,level,epsilon,h);
            result=selectNode.randomSelection(utility_result,epsilon);
            Output.resultPrint("./Exp_" +i+".txt",utility_result,result);
            System.out.println("File"+i+" Finished");
        }
     //   ****************/

        /********************************************exp
        original_datset=Dataread.getData("experimental_input.txt");
        level= new int[]{3, 1, 3, 1, 3};
        for(int q=0;q<3;q++) {
            if(q==0) epsilon=0.5;
            else if(q==1) epsilon=0.25;
            else epsilon=0.1;
            BufferedWriter clear=new BufferedWriter(new FileWriter("./result/"+epsilon+"_Information Loss.txt"));
            clear.close();
            System.out.println("Original Dataset size: " + original_datset.size());
            h = 2;
            for (int i = 0; i < 10; i++) {
                utility_result = FullDomain.lattice(original_datset, level, epsilon, h);
                result = selectNode.randomSelection(utility_result, epsilon);
                Output.resultPrint("./Exp_"+q+"_" + i + ".txt", utility_result, result);
                System.out.println("File" + i + " Finished");
            }
        }
         //   ****************/
        /********************************************         * test
        original_datset=Dataread.getData("test_input.txt");
        level= new int[]{3, 0, 1, 0, 0};
        BufferedWriter clear=new BufferedWriter(new FileWriter("./result/Information Loss.txt"));
        clear.close();
        System.out.println("Original Dataset size: "+original_datset.size());
        h=2;
        utility_result=FullDomain.lattice(original_datset,level,epsilon,h);
        result=selectNode.randomSelection(utility_result,epsilon);
        Output.resultPrint("./Exp_test.txt",utility_result,result);
        System.out.println("Filetest Finished");
        //**************************************************/

        /*****************
        original_datset=Dataread.getData("experimental_input.txt");
        level= new int[]{0, 0, 0, 0, 0};

            BufferedWriter clear=new BufferedWriter(new FileWriter("./result/naive_Information Loss.txt"));
            clear.close();
            System.out.println("Original Dataset size: " + original_datset.size());
            h = 2;
            utility_result = FullDomain_naive.lattice_last(original_datset, level, epsilon, h);
//            result = selectNode.randomSelection(utility_result, epsilon);
//            Output.resultPrint("./Exp_naive.txt", utility_result, result);
//            **************/


        




    }

}

