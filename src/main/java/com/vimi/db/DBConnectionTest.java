package com.vimi.db;

/**
 * Created by vymi1016 on 10/18/2017.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionTest {

    private static final String QUERY = "select id,name,email,country,password from Users";
    private static final String  sql = "DROP TABLE COMPANY; ";
    public static void main(String[] args) {

        //using try-with-resources to avoid closing resources (boiler plate code)

        try(Connection con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        
       /* try(Connection con = PreparedDBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY)) {

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String password = rs.getString("password");
                System.out.println(id + "," +name+ "," +email+ "," +country+ "," +password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
    }

}