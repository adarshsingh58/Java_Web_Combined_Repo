package Hibernate.HibernateOne2ManyMapping.www.main;

import java.util.ArrayList;
import java.util.List;

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
        Vehicle vehicle1=new Vehicle();
        Vehicle vehicle2=new Vehicle();
        
        getEntities(personEntity1,vehicle1,vehicle2);
        
        session.save(personEntity1);
        session.save(vehicle1);
        session.save(vehicle2);
        session.getTransaction().commit();
        
        session.close();
	}

	private static void getEntities(PersonEntity personEntity, Vehicle vehicle1, Vehicle vehicle2) {
		
		personEntity.setId(3);
		personEntity.setName("Adarsh");
		vehicle1.setVehicleName("yahamaFZ");
		vehicle1.setVehicleNumber("123");
		vehicle2.setVehicleName("Pulasar");
		vehicle2.setVehicleNumber("456");
		
		List<Vehicle>  vehiclesList=new ArrayList<>();
		vehiclesList.add(vehicle1);
		vehiclesList.add(vehicle2);
		personEntity.setVehicle(vehiclesList);
	}
}
