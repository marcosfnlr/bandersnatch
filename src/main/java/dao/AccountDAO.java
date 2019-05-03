package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import model.Account;

public class AccountDAO extends AbstractDAO {

    public AccountDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Adds account on table Account.
     */
    public void addAccount(String idAccount, String password, String lastName, String firstName) {

        String query = "INSERT INTO Account (id_account, password, last_name, first_name) VALUES (?,?,?,?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, idAccount);
            ps.setString(2, password);
            ps.setString(3, lastName);
            ps.setString(4, firstName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Gets account with id_account identifier from table Account.
     */
    public Account getAccount(String idAccount) {
        String password, lastName, firstName;
        Account account = null;

        String query = "SELECT * FROM Account WHERE id_account=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, idAccount);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                password = rs.getString("password");
                lastName = rs.getString("last_name");
                firstName = rs.getString("first_name");

                account = new Account(idAccount, password, lastName, firstName);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return account;
    }

    /**
     * Checks account with id_account identifier and password from table Account.
     */
    public boolean checkAccount(String idAccount, String password) {

        String query = "SELECT id_account, password FROM Account WHERE id_account=? AND password=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, idAccount);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs != null && rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return false;
    }
}
