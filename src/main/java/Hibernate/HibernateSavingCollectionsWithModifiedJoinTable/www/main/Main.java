package Hibernate.HibernateSavingCollectionsWithModifiedJoinTable.www.main;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import onilne.mrwallet.www.Entity.PersonEntity;

public class Main 
{
    public static void main( String[] args )
    {
    	/*Rem sessinFactory is a heavy weigth object so we create it only one time and resuse it */
		/*
		 * While Session is a lightweigt and non-thread safe object so we create
		 * it,use it and close it and create it again whenver needed.
		 */
        SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
        saveEntityIntoTable(sessionFactory);
        retreiveDataIntoEntity(sessionFactory);
        sessionFactory.close();
    }

	private static void retreiveDataIntoEntity(SessionFactory sessionFactory) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		PersonEntity personEntity=session.get(PersonEntity.class, 2); //pass Entity name and primary key value for whom value will be fetched
		System.out.println(personEntity.getName());
		session.close();
		
	}

	private static void saveEntityIntoTable(SessionFactory sessionFactory) {
		Session session=sessionFactory.openSession();
        session.beginTransaction();
        PersonEntity personEntity1=getEntity();
        
        session.save(personEntity1);
        session.getTransaction().commit();
        
        session.close();
	}

	private static PersonEntity getEntity() {
		PersonEntity personEntity=new PersonEntity();
		personEntity.setId(2);
		personEntity.setName("Adarsh");
		personEntity.setDob(new Date());
		personEntity.setAge(24);
		Set<String> listOfPhoneNumbers = new HashSet<>();
		listOfPhoneNumbers.add("123");
		listOfPhoneNumbers.add("446");
		personEntity.setListOfPhoneNumbers(listOfPhoneNumbers);
		
		return personEntity;
	}
}
