package serialization.SerilizationDeserializationInheritanceSuperNotSERChildSER.www.Beans;

import java.io.Serializable;

public class Person {

	private String name;
	private int age;
	private String password;

	/*
	 * When SUper class is not serializanle then its no arg constructor is
	 * called mandatorily. Parameterized cons cant be called , it has to be no
	 * arg constructor. Therefore a no arg constructor is madnatory fr a
	 * nonserializable super class. All the fields from this Suoer class will
	 * appear as default values while deserialization. If any data is set in
	 * default constructor only that will appear as is during deserialization
	 * ebverything else will be dfault values
	 */
	public Person() {
		/*here name wil come but pass  and age will be null and 0 which ae the default values */
		this.name="Name From Super constructor";
	}

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
