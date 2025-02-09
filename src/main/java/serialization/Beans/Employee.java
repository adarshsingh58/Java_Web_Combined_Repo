package serialization.Beans;

import serialization.SerilizationDeserializationInheritanceSuperNotSERChildSER.www.Beans.Person;

import java.io.Serializable;

/*When Super class is not extending Serializable child class then we must call a no arg constructor of super class */
public class Employee extends Person implements Serializable{

	private int empId;
	private String department;
	
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
