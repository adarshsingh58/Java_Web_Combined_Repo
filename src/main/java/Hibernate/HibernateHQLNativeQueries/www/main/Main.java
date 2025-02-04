package Hibernate.HibernateHQLNativeQueries.www.main;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import onilne.mrwallet.www.Entity.PersonEntity;

public class Main 
{
    public static void main( String[] args )
    {
        SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
        saveEntityIntoTable(sessionFactory);
        retreiveDataIntoEntity(sessionFactory);
        sessionFactory.close();
    }

	private static void retreiveDataIntoEntity(SessionFactory sessionFactory) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		
		//PersonEntity personEntity=session.get(PersonEntity.class, 2); //pass Entity name and primary key value for whom value will be fetched
		 
		/*
		 * Instead of following session.get() approach we will make use of HQL.
		 * HQL are written with the help of Query class in which we can write
		 * SQL queries but passing in Entity names rather then DB names.
		 * '*' cant be used here for 'all'. By default there is *. select is only to be used when columns are to be sepertated
		 */
		/*
		 * Here native named query is used. This parameter is defined as the
		 * name of a query in the Entity itself under @NamedNativeQuery
		 * annotation. This will execute the query written under there.
		 */
		Query query=session.getNamedNativeQuery("personEntityById");
		query.setInteger(0, 5);
		
		/*
		 * createQuery creates and executes too if it is just a select query.
		 * Here because we are getting all data from person_entity we get it
		 * into a result lIst
		 */
		System.out.println(query.list().size());
		for(Object object:query.list()){	
			PersonEntity personEntity=(PersonEntity) object;
			System.out.println(personEntity.getName());
		}
		session.getTransaction().commit();		
		session.close();
		
	}

	private static void saveEntityIntoTable(SessionFactory sessionFactory) {
		Session session=sessionFactory.openSession();
        for(int i=0;i<20;i++){
        session.beginTransaction();
        PersonEntity personEntity1=getEntity(i);
        session.save(personEntity1);
        session.getTransaction().commit();
        }
        
        session.close();
	}

	private static PersonEntity getEntity(int i) {
		PersonEntity personEntity=new PersonEntity();
		personEntity.setId(i);
		personEntity.setName("Adarsh"+i);
		personEntity.setDob(new Date());
		personEntity.setAge(24+i);
		personEntity.setAddressLine1("line1 "+i);
		personEntity.setAddressLine2("line2 "+i);
		return personEntity;
	}
}
