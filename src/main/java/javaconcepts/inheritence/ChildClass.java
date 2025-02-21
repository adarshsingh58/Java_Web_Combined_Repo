package javaconcepts.inheritence;

//Student Object
public class ChildClass extends ParentClass implements ParentInterface {

    private final String rollNo;

    /*This constructor is mandatory because everytime child object is created.
     * Both child and parent constructor is called. So if we have a custom constructor in parent,
     * we need to call that explicitly in child.
     * Parent object is not created only parent constructor is called, coz child need parent's fields/attributes to complete its
     * own object. hence parent's constuctor is called to set all imp properties.
     *  */
    public ChildClass(String name, Integer age, String rollNo) {
        super(name, age);// Super is called first thing in child const, else wil give compile error.
        this.rollNo = rollNo;
    }

    public String getRollNo() {
        return rollNo;
    }
}
