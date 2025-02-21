package javaconcepts.comparatorcomparable;

public class Student implements Comparable {

    private String Name;
    private Integer RollNo;

    public Student(String Name, Integer RollNo) {
        this.Name = Name;
        this.RollNo = RollNo;
    }

    public String getName() {
        return Name;
    }

    public Integer getRollNo() {
        return RollNo;
    }

    @Override
    public int compareTo(Object o) {
        Student stu = (Student) o;
        int comparevalue;
        //we want to sort by name and if name is same sort in ascending by rollno

        return this.Name.compareTo(stu.getName()) != 0 ? this.Name.compareTo(stu.getName()) : this.getRollNo().compareTo(stu.getRollNo());
    }

    @Override
    public String toString() {
        return this.getName() + " -> " + this.getRollNo();
    }
}
