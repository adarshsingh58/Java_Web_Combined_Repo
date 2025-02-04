package Hibernate.HibernateOne2OneMapping.www.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle { //Make sure to add this in cfg.xml file as mapping entry
	
	@Id
	private int id;
	private String vehicleName;
	private String vehicleNumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
}
