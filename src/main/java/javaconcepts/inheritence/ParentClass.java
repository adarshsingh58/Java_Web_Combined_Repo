package javaconcepts.inheritence;

//Person Object
public class ParentClass {

    /*
    These are private and final, because they are mandatory for the Person Object(Name and DOB defines a Person)
    Since these fields are imp they are initialied in constructor and are made final coz they cant change once the object
    for a given person is created.
    And since they are private they are provided with a getter (not setter because once set we dont want it to change)
     */
    private final String name;
    private final Integer dob;

    /*
    * Current Location or residence for a person may change over time. So even if the person object is created, its location
    * should be changeable over time. Hence we are not keeping it final and provided both getter and setter for it.
    * Also, its not a mandatory field hence not initialized in constructor.
    * */
    private Integer location;

    public ParentClass(String name, Integer age) {
        this.name = name;
        this.dob = age;
    }

    public String getName() {
        return name;
    }

    public Integer getDob() {
        return dob;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }
}
