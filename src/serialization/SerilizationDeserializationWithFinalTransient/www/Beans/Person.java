package serialization.SerilizationDeserializationWithFinalTransient.www.Beans;

import java.io.Serializable;

public class Person implements Serializable {

	private String name;
	private int age;
	/*
	 * Making a field final will mean that at compile time all references of
	 * password var wil be replaced by its value. SO till runtime there will be
	 * no final variable, but the data in password will remain the part of the
	 * object. There fore this will get serialized. Now because there is no variable left after compile time even if we use transient keyword
	 * final var will still get serelized as for JVM there is no variable name to apply transient keyword at all.
	 */
	/*transient*/ private final String password = "password";
	

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

}
