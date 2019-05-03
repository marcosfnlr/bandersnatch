package dao;

import model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
    public void addHistory(History history) {
        String query = "INSERT INTO History (fk_account, fk_book, fk_choice, creation_date) "
                + "VALUES (?,?,?,?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, history.getAccount().getIdAccount());
            ps.setInt(2, history.getBook().getIdBook());
            ps.setInt(3, history.getChoice().getIdChoice());
            ps.setTimestamp(4, history.getDateCreated());
            int count = ps.executeUpdate();

            if (count < 1) {
                throw new DAOException("Erreur dans la creation de l'historique du utilisateur");
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Deletes history from table History.
     */
    public void deleteHistory(History history) {
        String query = "DELETE FROM History WHERE fk_account=? AND fk_book=? AND fk_choice=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, history.getAccount().getIdAccount());
            ps.setInt(2, history.getBook().getIdBook());
            ps.setInt(3, history.getChoice().getIdChoice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Returns list of History that exists for a given User.
     */
    public List<Book> listBooksUserHistory(String idAccount) {
        List<Book> list = new ArrayList<>();

        String query = "SELECT DISTINCT fk_book FROM History WHERE fk_account=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, idAccount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(bookDAO.getBook(rs.getInt("fk_book")));
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }

    /**
     * Returns list of History that exists for a given User in a given book.
     */
    public List<History> listUserHistoryFromBook(String idAccount, int idBook) {
        List<History> list = new ArrayList<>();

        String query = "SELECT * FROM History WHERE fk_account=? AND fk_book=?" +
                "ORDER BY creation_date ASC";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, idAccount);
            ps.setInt(2, idBook);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = accountDAO.getAccount(idAccount);
                Book book = bookDAO.getBook(rs.getInt("fk_book"));
                Choice choice = choiceDAO.getChoice(rs.getInt("fk_choice"));
                History h = new History(account, book, choice, rs.getTimestamp("creation_date"));
                list.add(h);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }
}
