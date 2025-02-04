package Hibernate.HibernateWithAnnotatedTableColumnName.www.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="PERSON_ENTITY")//Now table namwe wil be PERSON_ENTITY
public class PersonEntity {

	@Id
	private int id;
	@Column(name="NAMEX") //Now column name wil be NAMEX
	private String name;
	
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
