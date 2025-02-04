package Hibernate.HibernateWithTransientTemporalLob.www.main;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import onilne.mrwallet.www.Entity.PersonEntity;

public class Main 
{
    public static void main( String[] args )
    {
        SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
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
		personEntity.setAddressLine1("line1");
		personEntity.setAddressLine2("line2");
		return personEntity;
	}
}
