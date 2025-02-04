package serialization.SerilizationDeserializationInheritanceSuperSERChildNotSER.www.Beans;


/*When Super class is extending Serializable child class is automatically deemed to be serializable */
public class Employee extends Person{

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
