public class Tuple {
    private int age;
    private int sex;
    private int cnt;
    private int operation;
    private String location;
    private String disease;

    public Tuple(int age,int sex, int cnt,int operation, String location, String disease){
        this.age=age;
        this.sex=sex;
        this.cnt=cnt;
        this.operation=operation;
        this.location=location;
        this.disease=disease;
    }
    public Tuple(String s){
        String[] a;
        a=s.split(",");
        this.age=Integer.parseInt(a[0]);
        this.sex=Integer.parseInt(a[1]);
        this.cnt=Integer.parseInt(a[2]);
        this.operation=Integer.parseInt(a[3]);
        this.location=a[4];
        this.disease=a[5];
    }
    public int getAge(){
        return this.age;
    }
    public int getSex(){
        return this.sex;
    }
    public int getCnt(){
        return this.cnt;
    }
    public int getOperation(){
        return this.operation;
    }
    public String getLocation(){
        return this.location;
    }
    public String getDisease(){
        return this.disease;
    }
    public String getQI(){
        return this.age+","+this.sex+","+this.cnt+","+this.operation+","+this.location;
    }
    public String getSA(){
        return this.disease;
    }
    @Override
    public String toString() {
        return this.age+","+this.sex+","+this.cnt+","+this.operation+","+this.location+","+this.disease;
    }
}
