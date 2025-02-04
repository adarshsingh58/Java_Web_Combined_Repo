package Hibernate.HibernateWithFetch.www.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "PERSON_ENTITY") // Now table namwe wil be PERSON_ENTITY
public class PersonEntity {

	@Id
	private int id;

	@Column(name = "NAMEX") // Now column name wil be NAMEX
	private String name;

	@Column(name = "DOB")
	@Temporal(TemporalType.DATE) // This will not save the date as Timestamp(which is default), instead will save only date
	private Date dob;

	@Column(name = "AGE")
	private int age;

	@Column(name = "ADDRESS")
	private String address;

	@Transient
	private String addressLine1;
	@Transient
	private String addressLine2;
	
	@Lob
	@Column(name="DESCRIPTION")
	private String description;
	
	

	/*
	 * We have removed the setter and getters for address and in setter of addressline1 and 2  we are combinng
	 * values from addressline1 amd addressline2 into address field. Also we are marking
	 * addressline1 and addressline2 as Transient coz they will not go into DB.
	 * They will contain data but address as a whole will go into db
	 */

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
		if (address != null)
			address = address + addressLine1;
		else
			address = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
		if (address != null)
			address = address + addressLine2;
		else
			address = addressLine2;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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
