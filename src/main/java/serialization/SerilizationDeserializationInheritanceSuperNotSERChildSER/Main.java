package serialization.SerilizationDeserializationInheritanceSuperNotSERChildSER;

import serialization.Beans.Employee;

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
		/*Open FileInputStream taikng in the File location where the object to read reside*/
		FileInputStream fileInputStream=new FileInputStream(new File("serializedFile.txt"));
		
		/*Now we wil open a  ObjectInputStream because FileInputStream doesnt directly work with saving Java Objects 
		 * ObjectInputStream will channel fileoutput stream format from file into java Objects */
		ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
		
		/*Read the object from the file. The return type is always Object, we wil have to case it into the object that was stored.
		 * Which also eans that we must know prehand what object type was serialized before deserialization  */
		Object object=objectInputStream.readObject();
		Employee employee=(Employee)object;
		
		System.out.println("Age:"+employee.getAge());
		System.out.println("Name:"+employee.getName());
		System.out.println("Password:"+employee.getPassword());
		System.out.println("Department:"+employee.getDepartment());
		System.out.println("EmpId:"+employee.getEmpId());
		
		/*Close all Streams */
		objectInputStream.close();
		fileInputStream.close();
	}

	private static void serializePerson() throws FileNotFoundException, IOException {
		/*Creating object of class to serialize and some data to it*/
		Employee employee=new Employee();
		employee.setAge(24);
		employee.setDepartment("CS");
		employee.setEmpId(1);
		employee.setName("Adarsh");
		employee.setPassword("password");
		
		
		/*Now we will open a FileOutputStream passing in the File where objct will be saved */
		FileOutputStream fileOutputStream=new FileOutputStream(new File("serializedFile.txt"));
		
		/*Now we wil open a  ObjectOutputStream because FileputputStream doesnt directly work with saving Java Objects 
		 * ObjectOutputStream will channel java Objects into fileoutput stream format*/
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
		
		/*Now write the object to save/serialize into objectOutputStream */
		objectOutputStream.writeObject(employee);
		
		/*close all streams */
		objectOutputStream.close();
		fileOutputStream.close();
	}
}
