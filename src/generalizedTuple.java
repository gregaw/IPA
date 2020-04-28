public class generalizedTuple {
    private String age;
    private String sex;
    private String cnt;
    private String operation;
    private String location;
    private String disease;
    private int[] level;

    public generalizedTuple(String s){
        String[] a;
        a=s.split(",");
        this.age=a[0];
        this.sex=a[1];
        this.cnt=a[2];
        this.operation=a[3];
        this.location=a[4];
        this.disease=a[5];
    }
    public generalizedTuple(String s,int[] level){
        String[] a;
        a=s.split(",");
        this.age=a[0];
        this.sex=a[1];
        this.cnt=a[2];
        this.operation=a[3];
        this.location=a[4];
        this.disease=a[5];
        this.level=level;
    }
    public generalizedTuple(String age,String sex,String cnt,String operation,String location,String disease){
        this.age=age;
        this.sex=sex;
        this.cnt=cnt;
        this.operation=operation;
        this.location=location;
        this.disease=disease;
    }
    public generalizedTuple(String age,String sex,String cnt,String operation,String location,String disease,int[] level){
        this.age=age;
        this.sex=sex;
        this.cnt=cnt;
        this.operation=operation;
        this.location=location;
        this.disease=disease;
        this.level=level;
    }
    public String getQI(){
        return this.age+","+this.sex+","+this.cnt+","+this.operation+","+this.location;
    }
    public String getSA(){
        return this.disease;
    }
    public int[] getLevel(){
        return this.level;
    }
    @Override
    public String toString() {
        return this.age+","+this.sex+","+this.cnt+","+this.operation+","+this.location+","+this.disease;
    }
}
