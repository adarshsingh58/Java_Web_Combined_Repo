package serialization.SerilizationDeserializationWithTransient.www.Beans;

import java.io.Serializable;

public class Person implements Serializable {

	private String name;
	private int age;
	/*
	 * You certainly dont wnt password to get saved in a file or transfered over
	 * the network like this. So you declare it trasient and this field will not
	 * get serelized i.e. the data it holds in an object of Person will not get
	 * saved during serializtion process. and While deserizliztion you will receive default value which for String is NUll.
	 */
	transient private String password;

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

}
