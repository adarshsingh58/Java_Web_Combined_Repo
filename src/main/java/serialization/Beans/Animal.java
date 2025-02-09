package serialization.Beans;

import java.io.Serializable;

public class Animal implements Serializable
{

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
