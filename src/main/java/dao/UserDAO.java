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
                User user = new User(rs.getString("login"), rs.getString("password"),
                rs.getString("nom"), rs.getString("prenom"));
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
    public void addUser(String login, String password, String nom, String prenom) {
        String query = "INSERT INTO Account (login, password, nom, prenom) VALUES (?,?,?,?)";
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setString(3, nom);
            ps.setString(4, prenom);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Gets user using login identifier from table Account.
     */
    public User getUser(String login) {
        String password, nom, prenom;
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Account WHERE login=" + login);
            rs.next();
            password = rs.getString("password");
            nom = rs.getString("nom");
            prenom = rs.getString("prenom");
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return new User(login, password, nom, prenom);
    }
    
    /**
     * Checks user using login identifier and password from table Account.
     */
    public boolean checkUser(String login, String password) {

        String query = "SELECT login, password FROM Account WHERE login=? AND password=?";

        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) return true;
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return false;
    }
    
}
