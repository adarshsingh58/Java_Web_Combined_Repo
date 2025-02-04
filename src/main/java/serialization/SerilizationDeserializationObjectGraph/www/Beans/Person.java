package serialization.SerilizationDeserializationObjectGraph.www.Beans;

import java.io.Serializable;

public class Person implements Serializable{

	private String name;
	private int age;
	private String password;
	
	/*Now we have a Reference Object also in our class  */
	private Address address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
