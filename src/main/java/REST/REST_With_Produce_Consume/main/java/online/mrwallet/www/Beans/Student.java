package REST.REST_With_Produce_Consume.main.java.online.mrwallet.www.Beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
	
	private String name;
	private String id;
	
	public Student() {} // empty constuructor mandatory for jaxb to create xml out of this class's object. 
	
	public Student(String name,String id)
	{
		this.name=name;
		this.id=id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
