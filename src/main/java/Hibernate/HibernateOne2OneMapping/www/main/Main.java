package Hibernate.HibernateOne2OneMapping.www.main;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import onilne.mrwallet.www.Entity.PersonEntity;
import onilne.mrwallet.www.Entity.Vehicle;

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
		PersonEntity personEntity=session.get(PersonEntity.class, 2); //pass Entity name and primary key value for whom value will be fetched
		System.out.println(personEntity.getName());
		session.close();
		
	}

	private static void saveEntityIntoTable(SessionFactory sessionFactory) {
		Session session=sessionFactory.openSession();
        session.beginTransaction();
        
        PersonEntity personEntity1=new PersonEntity();
        Vehicle vehicle=new Vehicle();
        
        getEntities(personEntity1,vehicle);
        
        session.save(personEntity1);
        session.save(vehicle);
        session.getTransaction().commit();
        
        session.close();
	}

	private static void getEntities(PersonEntity personEntity, Vehicle vehicle) {
		
		personEntity.setId(2);
		personEntity.setName("Adarsh");
		vehicle.setVehicleName("yahamaFZ");
		vehicle.setVehicleNumber("123");
		personEntity.setVehicle(vehicle);
	}
}
