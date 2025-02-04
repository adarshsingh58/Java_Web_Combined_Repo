package serialization.SerilizationDeserializationCustomSerialization.www.Beans;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable {

	private String name;
	private int age;
	private String password;

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

	/*
	 * To write our custom serialization logic we ahve to implement writeObject
	 * and readObject methods
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		/*
		 * Now oos.defaultWriteObject(); is responsible for calling default oos
		 * object which performs all the object serialization stuff. Now what we
		 * can do is that, we know our writeObject(oos) mwethod will be called
		 * automatically by JVM when it encounter a oos.writeObject() in Main
		 * class. Then in this method , before calling oos.defaultWriteObject(),
		 * we can manipulate the data in the object like above and then call the
		 * default method which will now perform serelization but with customzed
		 * data.
		 */
		this.setName("Mr." + this.getName());
		oos.defaultWriteObject();

	}
	

}
