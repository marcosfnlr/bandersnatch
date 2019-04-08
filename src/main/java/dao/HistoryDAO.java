package dao;

import model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryDAO extends AbstractDAO {

    private AccountDAO accountDAO = new AccountDAO(dataSource);
    private BookDAO bookDAO = new BookDAO(dataSource);
    private ChoiceDAO choiceDAO = new ChoiceDAO(dataSource);

    public HistoryDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Adds history on table History.
     */
    public void addHistory(String idAccount, int idBook, int idChoice) {
        String query = "INSERT INTO History (fk_account, fk_book, fk_choice, creation_date) "
                + "VALUES (?,?,?,?)";

        /* Getting current time. Check after if there's better approach */
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, idAccount);
            ps.setInt(2, idBook);
            ps.setInt(3, idChoice);
            ps.setTimestamp(4, currentTimestamp);
            int count = ps.executeUpdate();
            if(count < 1) {
                throw new DAOException("Erreur dans la creation de l'historique du utilisateur");
            }
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Deletes history with specific id_account, id_book and id_choice from table History.
     */
    public void deleteHistory(String idAccount, int idBook,int idChoice) {
        String query = "DELETE FROM History WHERE fk_account=? AND fk_book=? AND fk_choice=?";

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, idAccount);
            ps.setInt(2, idBook);
            ps.setInt(3, idChoice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Returns list of History that exists for a given User.
     */
    public List<History> listUserHistory(String idAccount) {
        List<History> list = new ArrayList<>();

        String query = "SELECT * FROM History WHERE fk_account=?";

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, idAccount);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = accountDAO.getAccount(idAccount);
                Book book = bookDAO.getBook(rs.getInt("fk_book"));
                Choice choice = choiceDAO.getChoice(rs.getInt("fk_choice"));
                History h = new History(account, book, choice, rs.getTimestamp("creation_date"));
                list.add(h);
            }

        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }

    /**
     * Returns list of History that exists for a given Book.
     */
    public List<History> listBookHistory(int idBook) {
        List<History> list = new ArrayList<>();

        String query = "SELECT * FROM History WHERE fk_book=?";

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Book book = bookDAO.getBook(idBook);
                Choice choice = choiceDAO.getChoice(rs.getInt("fk_choice"));
                History h = new History(account, book, choice, rs.getTimestamp("creation_date"));
                list.add(h);
            }

        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }

    /**
     * Returns list of History that exists for a given Choice.
     */
    public List<History> listChoiceHistory(int idChoice) {
        List<History> list = new ArrayList<>();

        String query = "SELECT * FROM History WHERE fk_choice=?";

        try(
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, idChoice);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Book book = bookDAO.getBook(rs.getInt("fk_book"));
                Choice choice = choiceDAO.getChoice(idChoice);
                History h = new History(account, book, choice, rs.getTimestamp("creation_date"));
                list.add(h);
            }

        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }
}
