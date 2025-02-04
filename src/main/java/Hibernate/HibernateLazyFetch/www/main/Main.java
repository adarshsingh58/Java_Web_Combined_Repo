package Hibernate.HibernateLazyFetch.www.main;

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
		/*Here getName() will give name but collection data i.e. listOfPhoneNumbers is still not fetched by hibernate*/
		/*
		 * listOfPhoneNumbers wbeing a collection might contain a lot of data.
		 * So in LAZY FETCH collections and some other dtaa types are not
		 * fetched untill required. Upon doing getListOfPhoneNumbers(),
		 * hibernate will fire one more query to get collection data from the
		 * JOIN TABLE which stores colelction data.
		 * This is LAZY FETCH
		 */
		
		/*System.out.println(personEntity.getListOfPhoneNumbers().size());//output will be 2*/		
		
		/*However if we close session first before doing getListOfPhoneNumbers(), hiernate cant fire the query and we will get LazyInitializationException */
		session.close();
		System.out.println(personEntity.getListOfPhoneNumbers().size());//LazyInitializationException coz hibernate now cant fire query after sessin is closed
		
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
