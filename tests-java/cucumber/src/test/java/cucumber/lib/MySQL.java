package cucumber.lib;

import com.mysql.cj.jdbc.Driver;
import cucumber.lib.User;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySQL {

    private Connection conn;
    private Logger logger;

    public MySQL   (final String hostname, 
                    final String port, 
                    final String username, 
                    final String password) {
        
        this.logger = Logger.getLogger(MySQL.class.getName());
        try {
            final String conn_url = String.format("jdbc:mysql://%s:%s/test", hostname, port);
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.setLoginTimeout(5);
            this.conn = DriverManager.getConnection(conn_url, username, password);
        
        } catch (Exception e) {

            this.logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    public void user_insert(BigDecimal balanceNum,
                            String balanceRaw,
                            String datatimeRetrieved, 
                            String msisdn, 
                            String prepaidPlan, 
                            String username) {
        try {
            String sql = "INSERT INTO test.users_results "
                + "(balance_num, balance_raw, datetime_retrieved, msisdn, prepaid_plan, username) "
                + " VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, balanceNum);
            ps.setString(2, balanceRaw);
            ps.setString(3, datatimeRetrieved);
            ps.setString(4, msisdn);
            ps.setString(5, prepaidPlan);
            ps.setString(6, username);
            
            ps.execute();

        } catch (SQLException e) {

            this.logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void connectionClose () {

        try {
            conn.close();
        
        } catch (SQLException e) {

            this.logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}