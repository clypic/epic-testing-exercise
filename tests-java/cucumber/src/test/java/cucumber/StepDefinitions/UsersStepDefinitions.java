package cucumber.StepDefinitions;

import com.mysql.cj.jdbc.Driver;
import cucumber.lib.User;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.junit.jupiter.api.Assertions;


public class UsersStepDefinitions {

    private Connection conn;
    private List<User> users = new ArrayList();


    @Given("the connection details hostname {string} on port {string} with username {string} and password {string}")
    public void givenConnectionDetails (final String hostname, 
                                        final String port, 
                                        final String username, 
                                        final String password) 
                                        throws Exception {

        String conn_url = String.format("jdbc:mysql://%s:%s/test", hostname, port);
        Class.forName("com.mysql.cj.jdbc.Driver");
        DriverManager.setLoginTimeout(5);
        this.conn = DriverManager.getConnection(conn_url, username, password);
    }

    @When("we get all users from table users")
    public void createUser() 
        throws SQLException {

        String sqlSelectAllUsers = "SELECT * FROM users;";
        PreparedStatement ps = this.conn.prepareStatement(sqlSelectAllUsers);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            String pass = rs.getString("pass");
            Boolean is_valid = rs.getBoolean("is_valid");
            this.users.add(new User(name, pass, "", is_valid));
        }
    }

    @Then("the list must contain user {string}")
    public void responseStatusCodeMustBeInteger(final String username) {
        Assertions.assertEquals(this.users.size(), 1);
        User user = this.users.get(0);
        Assertions.assertEquals(user.getName(), "99368021");
        Assertions.assertEquals(user.getPass(), "Voda1234");
    }

    @After
    public void closeConnections()
        throws SQLException {
        if (this.conn instanceof Connection) {
            this.conn.close();
        }
    }
}