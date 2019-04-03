/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import model.User;

/**
 *
 * @author raphaelcja
 * 
 * Here all the queries are made on the table Account due to 
 * the fact that User is a keyword of sqlplus.
 */
public class UserDAO extends AbstractDataBaseDAO {
    
    public UserDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Returns list of users from table Account.
     */
    public List<User> getListUsers() {
        List<User> list = new ArrayList<User>();
        
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Account");
            while (rs.next()) {
                User user = new User(rs.getString("id_account"), rs.getString("password"),
                rs.getString("last_name"), rs.getString("first_name"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }
    
    /**
     * Adds user on table Account.
     */
    public void addUser(String idAccount, String password, String lastName, String firstName) {
        String query = "INSERT INTO Account (id_account, password, last_name, first_name) VALUES (?,?,?,?)";
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setString(1, idAccount);
            ps.setString(2, password);
            ps.setString(3, lastName);
            ps.setString(4, firstName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Gets user with id_account identifier from table Account.
     */
    public User getUser(String idAccount) {
        String password, lastName, firstName;
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Account WHERE id_account=" + idAccount);
            rs.next();
            password = rs.getString("password");
            lastName = rs.getString("last_name");
            firstName = rs.getString("first_name");
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return new User(idAccount, password, lastName, firstName);
    }
    
    /**
     * Checks user with id_account identifier and password from table Account.
     */
    public boolean checkUser(String idAccount, String password) {

        String query = "SELECT id_account, password FROM Account WHERE id_account=? AND password=?";

        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idAccount);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) return true;
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return false;
    }
    
}
