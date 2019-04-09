package dao;

import model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvitationDAO extends AbstractDAO {

    private AccountDAO accountDAO = new AccountDAO(dataSource);
    private BookDAO bookDAO = new BookDAO(dataSource);

    public InvitationDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Adds invitation on table Invitation.
     */
    public void addInvitation(String idAccount, int idBook) {
        String query = "INSERT INTO Invitation (fk_account, fk_book) VALUES (?,?)";

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, idAccount);
            ps.setInt(2, idBook);
            int count = ps.executeUpdate();
            if(count < 1) {
                throw new DAOException("Erreur dans la creation de l'invitation");
            }
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Deletes invitation with specific id_account and id_book from table Invitation.
     */
    public void deleteInvitation(String idAccount, int idBook) {
        String query = "DELETE FROM Invitation WHERE fk_account=? AND fk_book=?";

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, idAccount);
            ps.setInt(2, idBook);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Returns list of Invitations that exists for a given User.
     */
    public List<Invitation> listUserInvitations(String idAccount) {
        List<Invitation> list = new ArrayList<>();

        String query = "SELECT * FROM Invitation WHERE fk_account=?";

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, idAccount);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = accountDAO.getAccount(idAccount);
                Book book = bookDAO.getBook(rs.getInt("fk_book"));
                Invitation i = new Invitation(account, book);
                list.add(i);
            }

        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }

    /**
     * Returns list of Invitations that exists for a given Book.
     */
    public List<Invitation> listBookInvitations(int idBook) {
        List<Invitation> list = new ArrayList<>();

        String query = "SELECT * FROM Invitation WHERE fk_book=?";

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Book book = bookDAO.getBook(idBook);
                Invitation i = new Invitation(account, book);
                list.add(i);
            }

        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }
}
