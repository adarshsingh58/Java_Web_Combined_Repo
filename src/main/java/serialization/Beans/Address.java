package serialization.Beans;

import java.io.Serializable;

/* If this class is not serialized wwe will get java.io.NotSerializableException*/
public class Address implements Serializable{

	private String line;
	private String flat_number;
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getFlat_number() {
		return flat_number;
	}
	public void setFlat_number(String flat_number) {
		this.flat_number = flat_number;
	}
	
}
