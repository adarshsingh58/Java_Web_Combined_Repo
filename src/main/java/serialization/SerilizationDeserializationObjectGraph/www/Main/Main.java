package serialization.SerilizationDeserializationObjectGraph.www.Main;

import serialization.SerilizationDeserializationObjectGraph.www.Beans.Address;
import serialization.SerilizationDeserializationObjectGraph.www.Beans.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		serializePerson();
		deserializePerson();

	}

	private static void deserializePerson() throws IOException, ClassNotFoundException {
		/*
		 * Open FileInputStream taikng in the File location where the object to
		 * read reside
		 */
		FileInputStream fileInputStream = new FileInputStream(new File("serializedFile.txt"));

		/*
		 * Now we wil open a ObjectInputStream because FileInputStream doesnt
		 * directly work with saving Java Objects ObjectInputStream will channel
		 * fileoutput stream format from file into java Objects
		 */
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		/*
		 * Read the object from the file. The return type is always Object, we
		 * wil have to case it into the object that was stored. Which also eans
		 * that we must know prehand what object type was serialized before
		 * deserialization
		 */
		Object object = objectInputStream.readObject();
		Person person = (Person) object;

		System.out.println("Age:" + person.getAge());
		System.out.println("Name:" + person.getName());
		System.out.println("Password:" + person.getPassword());
		/*BECAUSE ADDRESS CLASS WAS IPLEMENTING SERIALIZABLE ALSO AS AN IBJECT GRAPH IT IS ALSO SERIZLIZED */
		System.out.println("address:" + person.getAddress().getLine());

		/* Close all Streams */
		objectInputStream.close();
		fileInputStream.close();
	}

	private static void serializePerson() throws FileNotFoundException, IOException {
		/* Creating object of class to serialize and some data to it */
		Person person = new Person();
		person.setName("Adarsh");
		person.setAge(24);
		person.setPassword("password");

		/*
		 * HERE WE WILL ADD AN OBJECT REFERNEC ALSO IN OUT MAIN OBJECT WHICH IS
		 * TO BE SEREALIZED
		 */
		/*
		 * NOW ALONG WITH PERSON, ADDRESS WILL ALSO GET SERELIZED EVEN THOUGH WE
		 * ARE NOT EXPLII=CITLY SEREIZLIAING IT. THIS IS CALLED OBJECT GRAPH,
		 * WHERE UPON SERIALIZATING ONE OBJECT ALL OBJECTS INSIDE IT ALSO GETS
		 * SERIZLIZED, GIVEN ALL IMPLEMENTS SERIZLIABLE ELSE
		 * java.io.NotSerializableException WILL OCCUR
		 */
		Address address = new Address();
		address.setFlat_number("101");
		address.setLine("DSE Winsop");

		person.setAddress(address);

		/*
		 * Now we will open a FileOutputStream passing in the File where objct
		 * will be saved
		 */
		FileOutputStream fileOutputStream = new FileOutputStream(new File("serializedFile.txt"));

		/*
		 * Now we wil open a ObjectOutputStream because FileputputStream doesnt
		 * directly work with saving Java Objects ObjectOutputStream will
		 * channel java Objects into fileoutput stream format
		 */
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		/* Now write the object to save/serialize into objectOutputStream */
		objectOutputStream.writeObject(person);

		/* close all streams */
		objectOutputStream.close();
		fileOutputStream.close();
	}
}
