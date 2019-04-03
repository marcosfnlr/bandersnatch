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
import model.Paragraph;
import model.User;

/**
 *
 * @author raphaelcja
 */
public class BookDAO extends AbstractDataBaseDAO {
    
    @Resource(name = "jdbc/Bandersnatch")
    private DataSource ds;
    
    public BookDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Returns list of published books from table Book.
     */
    public List<Book> getListPublishedBooks() {
        List<Book> list = new ArrayList<Book>();
        UserDAO userDAO = new UserDAO(ds);
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Book WHERE published=1");
            while (rs.next()){
                User user = userDAO.getUser(rs.getString("fk_account"));
                Paragraph paragraph = paragraphDAO.getParagraph(rs.getInt("fk_first_parag"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getBoolean("open_write"), 
                    rs.getBoolean("published"), user, paragraph);
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
    public void addBook(String title, boolean openToWrite, boolean published, User creator, Paragraph firstParagraph) {
        String query = "INSERT INTO Book (title, open_write, published, fk_account, fk_first_parag) VALUES (?,?,?,?,?)";
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setString(1, title);
            ps.setBoolean(2, openToWrite);
            ps.setBoolean(3, published);
            ps.setString(4, creator.getIdAccount());
            ps.setInt(5, firstParagraph.getIdParagraph());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Gets book with idBook identifier from table Book.
     */
    public Book getBook(int idBook) {
        String title;
        boolean openToWrite, published;
        User creator;
        Paragraph firstParagraph;
        UserDAO userDAO = new UserDAO(ds);
        ParagraphDAO paragraphDAO = new ParagraphDAO(ds);
        
        
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Book WHERE idBook=" + idBook);
            rs.next();
            title = rs.getString("title");
            openToWrite = rs.getBoolean("open_write");
            published = rs.getBoolean("published");
            creator =  userDAO.getUser(rs.getString("fk_account"));
            firstParagraph = paragraphDAO.getParagraph(rs.getInt("fk_first_parag"));
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return new Book(idBook, title, openToWrite, published, creator, firstParagraph);
    }
    
    /**
     * Deletes book with idBook identifier from table Book.
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
     * Modifies book publishing status with idBook identifier from table Book.
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
}
