package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import model.Book;
import model.Account;

public class BookDAO extends AbstractDAO {

    public BookDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Returns list of published books from table Book.
     */
    public List<Book> listPublishedBooks() {
        List<Book> list = new ArrayList<>();
        List<String> accounts = new ArrayList<>();

        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT * FROM Book WHERE published=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(rs.getString("fk_account"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getBoolean("open_write"),
                        rs.getBoolean("published"), null);
                list.add(book);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < list.size(); i++) {
            Account account = accountDAO.getAccount(accounts.get(i));
            list.get(i).setCreator(account);
        }

        return list;
    }

    /**
     * Returns list of open to write books from table Book.
     */
    public List<Book> listOpenBooks() {
        List<Book> list = new ArrayList<>();
        List<String> accounts = new ArrayList<>();

        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT * FROM Book WHERE open_write=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(rs.getString("fk_account"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getBoolean("open_write"),
                        rs.getBoolean("published"), null);
                list.add(book);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < list.size(); i++) {
            Account account = accountDAO.getAccount(accounts.get(i));
            list.get(i).setCreator(account);
        }

        return list;
    }

    /**
     * Returns list of all accounts from table Account that are authors of a given book.
     */
    public List<Account> listBookAuthors(int idBook) {
        List<Account> list = new ArrayList<>();

        String query = "SELECT * FROM Account WHERE id_account IN "
                + "(SELECT DISTINCT fk_account FROM Paragraph WHERE fk_book=?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account(rs.getString("id_account"), rs.getString("password"),
                        rs.getString("last_name"), rs.getString("first_name"));
                list.add(account);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return list;
    }

    /**
     * Returns list of all books from table Book for which the user is an author.
     */
    public List<Book> listAuthorBooks(String author) {
        List<Book> list = new ArrayList<>();
        List<String> accounts = new ArrayList<>();

        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT * FROM Book WHERE id_book IN "
                + "(SELECT DISTINCT fk_book FROM Paragraph WHERE fk_account=?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(rs.getString("fk_account"));
                //Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getBoolean("open_write"),
                        rs.getBoolean("published"), null);
                list.add(book);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < list.size(); i++) {
            Account account = accountDAO.getAccount(accounts.get(i));
            list.get(i).setCreator(account);
        }

        return list;
    }

    /**
     * Returns list of all books from table Book for which the user has an invitation.
     */
    public List<Book> listInvitationBooks(String login) {
        List<Book> list = new ArrayList<>();
        List<String> accounts = new ArrayList<>();

        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT * FROM Book WHERE id_book IN "
                + "(SELECT DISTINCT fk_book FROM Invitation WHERE fk_account=?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(rs.getString("fk_account"));
                //Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getBoolean("open_write"),
                        rs.getBoolean("published"), null);
                list.add(book);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < list.size(); i++) {
            Account account = accountDAO.getAccount(accounts.get(i));
            list.get(i).setCreator(account);
        }

        return list;
    }

    /**
     * Returns list of Users invited to write on a given Book.
     */
    public List<Account> listUsersInvited(int idBook) {
        List<Account> userList = new ArrayList<>();
        List<String> accounts = new ArrayList<>();

        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT fk_account FROM Invitation WHERE fk_book=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(rs.getString("fk_account"));
                //Account account = accountDAO.getAccount(rs.getString("fk_account"));
                //userList.add(account);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < accounts.size(); i++) {
            Account account = accountDAO.getAccount(accounts.get(i));
            userList.add(account);
        }

        return userList;
    }

    /**
     * Returns list of Users not invited to write on a given Book.
     */
    public List<Account> listUsersNotInvited(int idBook) {
        List<Account> userList = new ArrayList<>();
        List<String> accounts = new ArrayList<>();

        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT id_account FROM Account " +
                "MINUS " +
                "SELECT fk_account FROM Invitation WHERE fk_book = ?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(rs.getString("id_account"));
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < accounts.size(); i++) {
            Account account = accountDAO.getAccount(accounts.get(i));
            userList.add(account);
        }

        return userList;
    }

    /**
     * Adds book on table Book and returns its generated id.
     */
    public int addBook(String title, boolean openToWrite, String creator) {
        int idBook = -1;

        String returnCols[] = {"id_book"};
        String query = "INSERT INTO Book (title, open_write, fk_account) VALUES (?,?,?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query, returnCols)
        ) {
            ps.setString(1, title);
            ps.setBoolean(2, openToWrite);
            ps.setString(3, creator);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                idBook = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return idBook;
    }

    /**
     * Gets book with id_book identifier from table Book.
     */
    public Book getBook(int idBook) {
        boolean openToWrite, published;
        String title, account = null;
        Book book = null;

        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT * FROM Book WHERE id_book=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                title = rs.getString("title");
                openToWrite = rs.getBoolean("open_write");
                published = rs.getBoolean("published");
                account = rs.getString("fk_account");

                book = new Book(idBook, title, openToWrite, published, null);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        if (book != null) {
            book.setCreator(accountDAO.getAccount(account));
        }

        return book;
    }

    /**
     * Deletes book with id_book identifier from table Book.
     */
    public void deleteBook(int idBook) {

        String query = "DELETE FROM Book WHERE id_book=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Modifies book publishing status with id_book identifier from table Book.
     */
    public void publishBook(int idBook, boolean publish) {

        String query = "UPDATE Book SET published=? WHERE id_book=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setBoolean(1, publish);
            ps.setInt(2, idBook);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Checks if book with id_book identifier from table Book has at least one conclusion.
     */
    public boolean checkConclusion(int idBook) {

        String query = "SELECT COUNT (id_paragraph) AS conclusions FROM Paragraph WHERE conclusion=1 AND fk_book=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();

            if (rs != null && rs.next()) {
                if (rs.getInt("conclusions") > 0) return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return false;
    }
}
