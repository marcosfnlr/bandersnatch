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
 * @author raphaelcja
 */
public class ParagraphDAO extends AbstractDAO {

    public ParagraphDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Returns list of paragraphs from a book.
     * TODO : where to verify account
     */
    public List<Paragraph> listParagraphs(int idBook) {
        List<Paragraph> list = new ArrayList<>();
        List<Integer> fk_books = new ArrayList<>();
        List<String> fk_accounts = new ArrayList<>();

        BookDAO bookDAO = new BookDAO(dataSource);
        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT * FROM Paragraph WHERE fk_book=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                fk_books.add(rs.getInt("fk_book"));
                fk_accounts.add(rs.getString("fk_account"));
                //Book book = bookDAO.getBook(rs.getInt("fk_book"));
                //Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Paragraph p = new Paragraph(rs.getInt("id_paragraph"), rs.getString("text"), rs.getBoolean("beginning"),
                        rs.getBoolean("conclusion"), null, null);
                list.add(p);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < list.size(); i++) {
            Book book = bookDAO.getBook(fk_books.get(i));
            Account account = accountDAO.getAccount(fk_accounts.get(i));
            list.get(i).setBook(book);
            list.get(i).setAuthor(account);
        }

        return list;
    }

    /**
     * Returns list of conclusion paragraphs from a book.
     * TODO : where to verify account
     */
    public List<Paragraph> listConclusions(int idBook) {
        List<Paragraph> list = new ArrayList<>();
        List<Integer> fk_books = new ArrayList<>();
        List<String> fk_accounts = new ArrayList<>();

        BookDAO bookDAO = new BookDAO(dataSource);
        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT * FROM Paragraph WHERE fk_book=? AND conclusion=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                fk_books.add(rs.getInt("fk_book"));
                fk_accounts.add(rs.getString("fk_account"));
                //Book book = bookDAO.getBook(rs.getInt("fk_book"));
                //Account account = accountDAO.getAccount(rs.getString("fk_account"));
                Paragraph p = new Paragraph(rs.getInt("id_paragraph"), rs.getString("text"), rs.getBoolean("beginning"),
                        rs.getBoolean("conclusion"), null, null);
                list.add(p);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < list.size(); i++) {
            Book book = bookDAO.getBook(fk_books.get(i));
            Account account = accountDAO.getAccount(fk_accounts.get(i));
            list.get(i).setBook(book);
            list.get(i).setAuthor(account);
        }

        return list;
    }

    /**
     * Gets first paragraph of a book from table Paragraph.
     */
    public Paragraph getBeginning(int idBook) {
        boolean beginning, conclusion;
        int idParagraph = -1;
        String text, account = null;
        Paragraph paragraph = null;

        BookDAO bookDAO = new BookDAO(dataSource);
        AccountDAO accountDAO = new AccountDAO(dataSource);
        ChoiceDAO choiceDAO = new ChoiceDAO(dataSource);

        String query = "SELECT * FROM Paragraph WHERE beginning=1 AND fk_book=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idParagraph = rs.getInt("id_paragraph");
                text = rs.getString("text");
                beginning = rs.getBoolean("beginning");
                conclusion = rs.getBoolean("conclusion");
                account = rs.getString("fk_account");

                paragraph = new Paragraph(idParagraph, text, beginning, conclusion, null, null);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        if (paragraph != null) {
            paragraph.setBook(bookDAO.getBook(idBook));
            paragraph.setAuthor(accountDAO.getAccount(account));
        }

        return paragraph;
    }

    /**
     * Gets first paragraph of a book from table Paragraph.
     */
    public Paragraph getBeginningWithChoices(int idBook) {
        ChoiceDAO choiceDAO = new ChoiceDAO(dataSource);

        Paragraph paragraph = getBeginning(idBook);

        if (paragraph != null) {
            paragraph.setChoices(choiceDAO.listParagOrigChoices(paragraph.getIdParagraph()));
        }

        return paragraph;
    }

    /**
     * Adds paragraph on table Paragraph and returns its id.
     */
    public int addParagraph(String text, boolean beginning, boolean conclusion, int book, String author) {
        int idParagraph = 0;

        String returnCols[] = {"id_paragraph"};
        String query = "INSERT INTO Paragraph (text, beginning, conclusion, fk_book, fk_account) VALUES (?,?,?,?,?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query, returnCols)
        ) {
            ps.setString(1, text);
            ps.setBoolean(2, beginning);
            ps.setBoolean(3, conclusion);
            ps.setInt(4, book);
            ps.setString(5, author);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                idParagraph = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return idParagraph;
    }

    public Paragraph getParagraph(int idParagraph) {
        boolean beginning, conclusion;
        String text, account = null;
        int idBook = -1;
        Paragraph paragraph = null;

        BookDAO bookDAO = new BookDAO(dataSource);
        AccountDAO accountDAO = new AccountDAO(dataSource);

        String query = "SELECT * FROM Paragraph WHERE id_paragraph=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idParagraph);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                text = rs.getString("text");
                beginning = rs.getBoolean("beginning");
                conclusion = rs.getBoolean("conclusion");
                idBook = rs.getInt("fk_book");
                account = rs.getString("fk_account");

                paragraph = new Paragraph(idParagraph, text, beginning, conclusion, null, null);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        if (paragraph != null) {
            paragraph.setBook(bookDAO.getBook(idBook));
            paragraph.setAuthor(accountDAO.getAccount(account));
        }

        return paragraph;
    }

    /**
     * Gets paragraph with id_paragraph identifier and its choices.
     */
    public Paragraph getParagraphWithChoices(int idParagraph) {
        ChoiceDAO choiceDAO = new ChoiceDAO(dataSource);

        Paragraph paragraph = getParagraph(idParagraph);

        if (paragraph != null) {
            paragraph.setChoices(choiceDAO.listParagOrigChoices(idParagraph));
        }

        return paragraph;
    }

    /**
     * Deletes paragraph with id_paragraph identifier from table Paragraph.
     * TODO : constraints of deletion. here or controller?
     */
    public void deleteParagraph(int idParagraph) {

        String query = "DELETE FROM Paragraph WHERE id_paragraph=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idParagraph);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Modifies paragraph text with id_paragraph identifier from table Paragraph.
     * TODO : constraints of modification. here or controller?
     */
    public void modifyParagraph(int idParagraph, String text) {

        String query = "UPDATE Paragraph SET text=? WHERE id_paragraph=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, text);
            ps.setInt(2, idParagraph);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

}
