public class Generalization {

    public Generalization(){

    }
    public static generalizedTuple tupleGenearlization(Tuple t,int[] level){
        String age=ageTree(t.getAge(),level[0]);
        String sex=sexTree(t.getSex(),level[1]);
        String cnt=cntTree(t.getCnt(),level[2]);
        String opr=operationTree(t.getOperation(),level[3]);
        String loc=locationTree(t.getLocation(),level[4]);

        return new generalizedTuple(age,sex,cnt,opr,loc,t.getDisease(),level);
    }
    public static String ageTree(int value,int level){
        String generalizedValue=new String();
        int lower,upper;
        if(level==0){
            generalizedValue= String.valueOf(value);
        }else if(level==1){
            lower=(value/10)*10;
            upper=((value+10)/10)*10;
            generalizedValue="["+lower+"-"+upper+")";
        }else if(level==2){
            if(value<30){
                generalizedValue="[0-30)";
            }else if(value>=30&&value<60){
                generalizedValue="[30-60)";
            }else if(value>=60){
                generalizedValue="[60-100)";
            }
        }else if(level==3){
            generalizedValue="*";
        }

        return generalizedValue;
    }

    public static String sexTree(int value,int level){
        String generalizedValue=new String();

        if(level==0){
            generalizedValue= String.valueOf(value);
        }else if(level==1){
            generalizedValue="*";
        }

        return generalizedValue;
    }

    public static String cntTree(int value,int level){
        String generalizedValue=new String();
        int lower,upper;
        if(level==0){
            generalizedValue= String.valueOf(value);
        }else if(level==1){
            if(value==1){
                generalizedValue=String.valueOf(value);
            }else if(value>1&&value<5){
                generalizedValue="[2-5)";
            }else if(value>=5&&value<10) {
                generalizedValue = "[5-10)";
            }else if(value>=10&&value<30){
                    generalizedValue="[10-30)";
            }else if(value>=30){
                generalizedValue="[30-400)";
            }else{
                generalizedValue=Integer.toString(value);
            }
        }else if(level==2){
            if(value==1){
                generalizedValue=String.valueOf(value);
            }else{
                generalizedValue="[2-400)";
            }
        }else if(level==3){
            generalizedValue="*";
        }

        return generalizedValue;
    }

    public static String operationTree(int value,int level){
        String generalizedValue=new String();

        if(level==0){
            generalizedValue= String.valueOf(value);
        }else if(level==1){
            generalizedValue="*";
        }

        return generalizedValue;
    }

    public static String locationTree(String value,int level){
        String generalizedValue=new String();
        int lower,upper;
        if(level==0){
            generalizedValue=value;
        }else if(level==1){
            if(value.compareTo("11")==0)
                generalizedValue="11";
            else if(Integer.parseInt(value)>20&&Integer.parseInt(value)<30)
                generalizedValue = "[21-27)";
            else
                generalizedValue="[31-40)";

        }else if(level==2){
            if(value.compareTo("11")==0)
                generalizedValue="11";
            else
                generalizedValue="[21-40)";
        }else if(level==3){
            generalizedValue="*";
        }

        return generalizedValue;
    }
}
