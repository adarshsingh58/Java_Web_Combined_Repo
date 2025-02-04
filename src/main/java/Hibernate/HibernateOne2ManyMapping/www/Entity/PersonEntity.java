package Hibernate.HibernateOne2ManyMapping.www.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "PERSON_ENTITY") // Now table namwe wil be PERSON_ENTITY
public class PersonEntity {

	@Id
	private int id;

	@Column(name = "NAMEX") // Now column name wil be NAMEX
	private String name;
	
	@OneToMany
	private List<Vehicle> vehicle;
	

	public List<Vehicle> getVehicle() {
		return vehicle;
	}

	public void setVehicle(List<Vehicle> vehicle) {
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
