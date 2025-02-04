package Hibernate.HibernateLazyFetch.www.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

	@ElementCollection
	Set<String> listOfPhoneNumbers ; 
	
	
	public Set<String> getListOfPhoneNumbers() {
		return listOfPhoneNumbers;
	}

	public void setListOfPhoneNumbers(Set<String> listOfPhoneNumbers) {
		this.listOfPhoneNumbers = listOfPhoneNumbers;
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
