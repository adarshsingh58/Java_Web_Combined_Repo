package java8.eightFeatures.streams;

public class Person {

    private String name;
    private String address;
    private int age;

    public Person( String name,String address, int age){
       this.name=name;
       this.age=age;
       this.address=address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString(){
        return this.name+" : "+this.address+" : "+this.age;

    }
}
