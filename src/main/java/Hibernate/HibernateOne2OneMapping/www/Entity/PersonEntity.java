package Hibernate.HibernateOne2OneMapping.www.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "PERSON_ENTITY") // Now table namwe wil be PERSON_ENTITY
public class PersonEntity {

	@Id
	private int id;

	@Column(name = "NAMEX") // Now column name wil be NAMEX
	private String name;
	
	@OneToOne
	private Vehicle vehicle;
	

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
