package SpringBoot.util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil {

    String x="{\"dataWithContent\":\"<w:sdtContent><w:p><w:r><w:rPr><w:color w:val=\\\"000000”/><w:t>Adarsh</w:t></w:rPr></w:r></w:p></w:sdtContent>\"}";
    private static SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory()
    {
        try
        {
            if (sessionFactory == null)
            {
                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                        .configure("main/java/SpringBoot/hibernate.cfg.xml").build();
                Metadata metaData = new MetadataSources(standardRegistry)
                        .getMetadataBuilder()
                        .build();
                sessionFactory = metaData.getSessionFactoryBuilder().build();
            }
            return sessionFactory;
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        if(sessionFactory==null || sessionFactory.isClosed()){
            buildSessionFactory();
        }
        return sessionFactory;
    }
    public static void shutdown() {
        getSessionFactory().close();
    }
}