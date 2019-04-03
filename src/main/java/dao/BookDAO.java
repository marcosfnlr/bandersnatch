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
import model.Book;

/**
 *
 * @author raphaelcja
 */
public class BookDAO extends AbstractDataBaseDAO {
    
    public BookDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Returns list of published books from table Book.
     */
    public List<Book> getListPublishedBooks() {
        List<Book> list = new ArrayList<Book>();
        
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Book WHERE published=1");
            while (rs.next()){
                Book book = new Book(rs.getInt("idBook"), rs.getString("title"), rs.getBoolean("open"), 
                    rs.getBoolean("published"), rs.getString("creator"));
                list.add(book);
            }
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return list;
    }
    
    /**
     * Adds book on table Book.
     */
    public void addBook(String title, Boolean open, Boolean published, String creator) {
        String query = "INSERT INTO Book (title, open, published, creator) VALUES (?,?,?,?)";
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setString(1, title);
            ps.setBoolean(2, open);
            ps.setBoolean(3, published);
            ps.setString(4, creator);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Gets book with idBook identifier from table Book.
     */
    public Book getBook(int idBook) {
        String title, creator;
        Boolean open, published;
        
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Book WHERE idBook=" + idBook);
            rs.next();
            title = rs.getString("title");
            open = rs.getBoolean("open");
            published = rs.getBoolean("published");
            creator = rs.getString("creator");
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return new Book(idBook, title, open, published, creator);
    }
    
    /**
     * Deletes book with idBook identifier from table Book.
     */
    public void deleteBook(int idBook) {
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Book WHERE idBook=?");
            ) {
            ps.setInt(1, idBook);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Modifies book publishing status with idBook identifier from table Book.
     */
    public void publishBook(int idBook, boolean publish) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Book SET publish=? WHERE idBook=?");
	     ) {
            st.setBoolean(1, publish);
            st.setInt(2, idBook);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
