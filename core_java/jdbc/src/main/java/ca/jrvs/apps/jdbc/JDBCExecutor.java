package ca.jrvs.apps.jdbc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCExecutor {

  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager(
        "localhost", "hplussport","postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = new Customer();
      customer.setFirstName("Andrew");
      customer.setLastName("Wiggin");
      customer.setEmail("andrew.wiggin@if.org");
      customer.setPhone("(921) 465 1010");
      customer.setAddress("237 Dragon Ar");
      customer.setCity("Greensboro");
      customer.setState("North Carolina");
      customer.setZipCode("27401");

      customerDAO.create(customer);
    } catch(SQLException e) {
      e.printStackTrace();
    }

  }

}
