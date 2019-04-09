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
import model.Choice;
import model.Paragraph;

/**
 *
 * @author raphaelcja
 */
public class ChoiceDAO extends AbstractDAO {
    
    public ChoiceDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Returns list of choices that exist in a given paragraph.
     */
    public List<Choice> listParagraphChoices(int idParagOrig) {
        List<Choice> list = new ArrayList<>();
        ParagraphDAO paragraphDAO = new ParagraphDAO(dataSource);
        String query = "SELECT * FROM Choice WHERE fk_parag_orig=?";
        
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ) {
            ps.setInt(1, idParagOrig);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Paragraph paragOrigin = paragraphDAO.getParagraph(rs.getInt("fk_parag_orig"));
                Paragraph paragDest = paragraphDAO.getParagraph(rs.getInt("fk_parag_dest"));
                Paragraph paragCond = paragraphDAO.getParagraph(rs.getInt("fk_parag_cond"));
                Choice c = new Choice(rs.getInt("id_choice"), rs.getString("text"), rs.getBoolean("locked"), 
                        rs.getBoolean("only_choice"), rs.getBoolean("cond_should_pass"), 
                        paragOrigin, paragDest, paragCond);
                list.add(c);
            }
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return list;
    }
    
    /**
     * Adds choice on table Choice and returns its id.
     */
    public int addChoice(String text, boolean locked, boolean onlyChoice, boolean condShouldPass,
            int paragOrigin, int paragDest, int paragCond) {
        String query = "INSERT INTO Choice (text, locked, only_choice, cond_should_pass, fk_parag_orig, "
                + "fk_parag_dest, fk_parag_cond) VALUES (?,?,?,?,?,?,?)";
        int idChoice = 0;
        String returnCols[] = {"id_choice"};
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(query, returnCols);
            ) {
            ps.setString(1, text);
            ps.setBoolean(2, locked);
            ps.setBoolean(3, onlyChoice);
            ps.setBoolean(4, condShouldPass);
            ps.setInt(5, paragOrigin);
            ps.setInt(6, paragDest);
            ps.setInt(7, paragCond);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
                idChoice = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return idChoice;
    }
    
    /**
     * Gets choice with id_choice identifier from table Choice.
     */
    public Choice getChoice(int idChoice) {
        String text;
        boolean locked, onlyChoice, condShouldPass;
        Paragraph paragOrigin, paragDest, paragCond;
        ParagraphDAO paragraphDAO = new ParagraphDAO(dataSource);

        try(
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Choice WHERE id_choice=" + idChoice);
            rs.next();
            text = rs.getString("text");
            locked = rs.getBoolean("locked");
            onlyChoice = rs.getBoolean("only_choice");
            condShouldPass = rs.getBoolean("cond_should_pass");
            paragOrigin = paragraphDAO.getParagraph(rs.getInt("fk_parag_orig"));
            paragDest = paragraphDAO.getParagraph(rs.getInt("fk_parag_dest"));
            paragCond = paragraphDAO.getParagraph(rs.getInt("fk_parag_cond"));
            
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
        
        return new Choice(idChoice, text, locked, onlyChoice, condShouldPass, paragOrigin, paragDest, paragCond);
    }
    
    /**
     * Deletes choice with id_choice identifier from table Choice.
     * TODO : constraints of deletion. here or controller?
     */
    public void deleteChoice(int idChoice) {
        try(
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Choice WHERE id_choice=?");
            ) {
            ps.setInt(1, idChoice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException ("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Modifies choice with id_choice identifier from table Choice by updating it's destiny paragraph.
     */
    public void setParagDest(int idChoice, int idParagDest) {
        try (
	     Connection conn = getConn();
	     PreparedStatement ps = conn.prepareStatement("UPDATE Choice SET id_parag_dest=? WHERE id_choice=?");
	     ) {
            ps.setInt(1, idParagDest);
            ps.setInt(2, idChoice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Change Choice to locked or unlocked based on the boolean parameter.
     */
    public void setLocked(Choice choice, boolean isLocked) {
        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement("UPDATE Choice SET locked=? WHERE id_choice=?");
        ) {
            ps.setBoolean(1, isLocked);
            ps.setInt(2, choice.getIdChoice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
