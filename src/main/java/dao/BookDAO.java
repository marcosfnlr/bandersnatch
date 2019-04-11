/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import model.Book;
import model.Account;

/**
 *
 * @author raphaelcja
 */
public class BookDAO extends AbstractDAO {
    
    public BookDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Returns list of published books from table Book.
     */
    public List<Book> listPublishedBooks() {
        List<Book> list = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO(dataSource);
        
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Book WHERE published=1");
            while (rs.next()){
                Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getBoolean("open_write"), 
                    rs.getBoolean("published"), account);
                list.add(book);
            }
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return list;
    }
    
    /**
     * Returns list of all books from table Book for which the user is an author.
     */
    public List<Book> listAuthorBooks(String author) {
        List<Book> list = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO(dataSource);
        String query = "SELECT * FROM Book WHERE id_book IN "
                + "(SELECT DISTINCT fk_book FROM Paragraph WHERE fk_account=?)";
        
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getBoolean("open_write"), 
                    rs.getBoolean("published"), account);
                list.add(book);
            }
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return list;
    }
    
    /**
     * Returns list of all books from table Book for which the user has an invitation.
     */
    public List<Book> listInvitationBooks(String author) {
        List<Book> list = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO(dataSource);
        String query = "SELECT * FROM Book WHERE id_book IN "
                + "(SELECT DISTINCT fk_book FROM Invitation WHERE fk_account=?)";
        
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getBoolean("open_write"), 
                    rs.getBoolean("published"), account);
                list.add(book);
            }
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return list;
    }
    
    /**
     * Adds book on table Book and returns its generated id.
     */
    public int addBook(String title, boolean openToWrite, boolean published, String creator) {
        String query = "INSERT INTO Book (title, open_write, published, fk_account) VALUES (?,?,?,?)";
        int idBook = 0;
        String returnCols[] = {"id_book"};
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query, returnCols);
            ) {
            ps.setString(1, title);
            ps.setBoolean(2, openToWrite);
            ps.setBoolean(3, published);
            ps.setString(4, creator);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
                idBook = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return idBook;
    }
    
    /**
     * Gets book with id_book identifier from table Book.
     */
    public Book getBook(int idBook) {
        String title;
        boolean openToWrite, published;
        Account creator;
        AccountDAO accountDAO = new AccountDAO(dataSource);        
        
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Book WHERE id_book=" + idBook);
            rs.next();
            title = rs.getString("title");
            openToWrite = rs.getBoolean("open_write");
            published = rs.getBoolean("published");
            creator =  accountDAO.getAccount(rs.getString("fk_account"));
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return new Book(idBook, title, openToWrite, published, creator);
    }
    
    /**
     * Deletes book with id_book identifier from table Book.
     */
    public void deleteBook(int idBook) {
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Book WHERE id_book=?");
            ) {
            ps.setInt(1, idBook);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Modifies book publishing status with id_book identifier from table Book.
     */
    public void publishBook(int idBook, boolean publish) {
        try (
	     Connection conn = getConn();
	     PreparedStatement ps = conn.prepareStatement
	       ("UPDATE Book SET published=? WHERE id_book=?");
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

        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                if(rs.getInt("count") > 0) return true;
            }
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }

        return false;
    }
}
