package SpringBoot.repository;

import SpringBoot.Entity.Employee;
import SpringBoot.util.ConnectionPool;
import SpringBoot.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SimpleRepository {
    ConnectionPool connectionPool = new ConnectionPool("jdbc:h2:~/TEST", "sa", "password");

    public String getHome(){

        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from testtable");
            while (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.restoreConnection(conn);
        }
        return "Default Data";
    }

    public String getHomeHibernate() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //Add new Employee object
        Employee emp = new Employee();
        emp.setEmail("demo-user@mail.com");
        emp.setFirstName("demo");
        emp.setLastName("user");

        session.persist(emp);

        session.getTransaction().commit();
        session.close();
        HibernateUtil.shutdown();
        return "success";
    }
}
