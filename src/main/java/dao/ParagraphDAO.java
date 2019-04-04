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
import model.Account;
import model.Book;
import model.Paragraph;

/**
 *
 * @author raphaelcja
 */
public class ParagraphDAO extends AbstractDataBaseDAO {
    
    public ParagraphDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Returns list of paragraphs from a book.
     * TODO : where to verify account
     */
    public List<Paragraph> getListParagraphs(int idBook) {
        List<Paragraph> list = new ArrayList<Paragraph>();
        BookDAO bookDAO = new BookDAO(dataSource);
        AccountDAO accountDAO = new AccountDAO(dataSource);
        String query = "SELECT * FROM Paragraph WHERE fk_book=?";
                
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Book book = bookDAO.getBook(rs.getInt("fk_book"));
                Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Paragraph p = new Paragraph(rs.getInt("id_paragraph"), rs.getString("text"), rs.getBoolean("conclusion"), 
                    book, account);
                list.add(p);
            }
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return list;
    }
    
    /**
     * Adds paragraph on table Paragraph.
     */
    public void addParagraph(String text, boolean conclusion, int book, String author) {
        String query = "INSERT INTO Paragraph (text, conclusion, fk_book, fk_account) VALUES (?,?,?,?)";
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setString(1, text);
            ps.setBoolean(2, conclusion);
            ps.setInt(3, book);
            ps.setString(4, author);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Gets paragraph with id_paragraph identifier from table Paragraph.
     * TODO : if used to edit text, where to check account ?
     */
    public Paragraph getParagraph(int idParagraph) {
        String text;
        boolean conclusion;
        Book book;
        Account author;
        BookDAO bookDAO = new BookDAO(dataSource);
        AccountDAO accountDAO = new AccountDAO(dataSource);
        
        
        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Paragraph WHERE id_paragraph=" + idParagraph);
            rs.next();
            text = rs.getString("text");
            conclusion = rs.getBoolean("conclusion");
            book = bookDAO.getBook(rs.getInt("fk_book"));
            author = accountDAO.getAccount(rs.getString("fk_account"));
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return new Paragraph(idParagraph, text, conclusion, book, author);
    }
    
    /**
     * Deletes paragraph with id_paragraph identifier from table Paragraph.
     * TODO : constraints of deletion. here or controller?
     */
    public void deleteParagraph(int idParagraph) {
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Paragraph WHERE id_paragraph=?");
            ) {
            ps.setInt(1, idParagraph);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Modifies paragraph text with id_paragraph identifier from table Paragraph.
     * TODO : constraints of modification. here or controller?
     */
    public void modifyParagraph(int idParagraph, String text) {
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement
                ("UPDATE Paragraph SET text=? WHERE id_paragraph=?")
            ) {
            ps.setString(1, text);
            ps.setInt(2, idParagraph);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }
    
}
