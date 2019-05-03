package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import model.Choice;
import model.Paragraph;

public class ChoiceDAO extends AbstractDAO {

    public ChoiceDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Returns list of all choices that exist in a given paragraph.
     */
    public List<Choice> listParagOrigChoices(int idParagOrig) {
        List<Choice> list = new ArrayList<>();
        List<Integer> origins = new ArrayList<>();
        List<Integer> dests = new ArrayList<>();
        List<Integer> conds = new ArrayList<>();

        ParagraphDAO paragraphDAO = new ParagraphDAO(dataSource);

        String query = "SELECT * FROM Choice WHERE fk_parag_orig=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idParagOrig);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                origins.add(rs.getInt("fk_parag_orig"));
                dests.add(rs.getInt("fk_parag_dest"));
                conds.add(rs.getInt("fk_parag_cond"));
                //Paragraph paragOrigin = paragraphDAO.getParagraph(rs.getInt("fk_parag_orig"));
                //Paragraph paragDest = paragraphDAO.getParagraph(rs.getInt("fk_parag_dest"));
                //Paragraph paragCond = paragraphDAO.getParagraph(rs.getInt("fk_parag_cond"));
                Choice c = new Choice(rs.getInt("id_choice"), rs.getString("text"), rs.getBoolean("locked"),
                        rs.getBoolean("only_choice"), rs.getBoolean("cond_should_pass"),
                        null, null, null);
                list.add(c);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < list.size(); i++) {
            Paragraph paragOrigin = paragraphDAO.getParagraph(origins.get(i));
            Paragraph paragDest = paragraphDAO.getParagraph(dests.get(i));
            Paragraph paragCond = paragraphDAO.getParagraph(conds.get(i));
            list.get(i).setParagOrigin(paragOrigin);
            list.get(i).setParagDest(paragDest);
            list.get(i).setParagCond(paragCond);
        }

        return list;
    }

    /**
     * Returns list of choices that have a given paragraph as destination.
     */
    public List<Choice> listParagDestChoices(int idParagDest) {
        List<Choice> list = new ArrayList<>();
        List<Integer> origins = new ArrayList<>();
        List<Integer> dests = new ArrayList<>();
        List<Integer> conds = new ArrayList<>();

        ParagraphDAO paragraphDAO = new ParagraphDAO(dataSource);

        String query = "SELECT * FROM Choice WHERE fk_parag_dest=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idParagDest);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                origins.add(rs.getInt("fk_parag_orig"));
                dests.add(rs.getInt("fk_parag_dest"));
                conds.add(rs.getInt("fk_parag_cond"));
                //Paragraph paragOrigin = paragraphDAO.getParagraph(rs.getInt("fk_parag_orig"));
                //Paragraph paragDest = paragraphDAO.getParagraph(rs.getInt("fk_parag_dest"));
                //Paragraph paragCond = paragraphDAO.getParagraph(rs.getInt("fk_parag_cond"));
                Choice c = new Choice(rs.getInt("id_choice"), rs.getString("text"), rs.getBoolean("locked"),
                        rs.getBoolean("only_choice"), rs.getBoolean("cond_should_pass"),
                        null, null, null);
                list.add(c);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        for (int i = 0; i < list.size(); i++) {
            Paragraph paragOrigin = paragraphDAO.getParagraph(origins.get(i));
            Paragraph paragDest = paragraphDAO.getParagraph(dests.get(i));
            Paragraph paragCond = paragraphDAO.getParagraph(conds.get(i));
            list.get(i).setParagOrigin(paragOrigin);
            list.get(i).setParagDest(paragDest);
            list.get(i).setParagCond(paragCond);
        }

        return list;
    }

    /**
     * Adds choice on table Choice and returns its id.
     */
    public int addChoice(String text, boolean onlyChoice, boolean condShouldPass,
                         int paragOrigin, int paragCond) {
        int idChoice = -1;

        String returnCols[] = {"id_choice"};
        String query = "INSERT INTO Choice (text, only_choice, cond_should_pass, fk_parag_orig, "
                + "fk_parag_cond) VALUES (?,?,?,?,?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query, returnCols)
        ) {
            ps.setString(1, text);
            ps.setBoolean(2, onlyChoice);
            ps.setBoolean(3, condShouldPass);
            ps.setInt(4, paragOrigin);
            ps.setInt(5, paragCond);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                idChoice = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        return idChoice;
    }

    /**
     * Gets choice with id_choice identifier from table Choice.
     */
    public Choice getChoice(int idChoice) {

        int origin = -1, dest = -1, cond = -1;
        boolean locked, onlyChoice, condShouldPass;
        String text;
        Choice choice = null;

        ParagraphDAO paragraphDAO = new ParagraphDAO(dataSource);

        String query = "SELECT * FROM Choice WHERE id_choice=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idChoice);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                text = rs.getString("text");
                locked = rs.getBoolean("locked");
                onlyChoice = rs.getBoolean("only_choice");
                condShouldPass = rs.getBoolean("cond_should_pass");
                origin = rs.getInt("fk_parag_orig");
                dest = rs.getInt("fk_parag_dest");
                cond = rs.getInt("fk_parag_cond");

                choice = new Choice(idChoice, text, locked, onlyChoice, condShouldPass, null, null, null);
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }

        if (choice != null) {
            choice.setParagOrigin(paragraphDAO.getParagraph(origin));
            choice.setParagDest(paragraphDAO.getParagraph(dest));
            choice.setParagCond(paragraphDAO.getParagraph(cond));
        }

        return choice;
    }

    /**
     * Modifies choice with id_choice identifier from table Choice by updating it's destiny paragraph.
     */
    public void setParagDest(int idChoice, int idParagDest) {

        String query = "UPDATE Choice SET fk_parag_dest=? WHERE id_choice=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
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
    public void setLocked(int idChoice, boolean isLocked) {

        String query = "UPDATE Choice SET locked=? WHERE id_choice=?";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setBoolean(1, isLocked);
            ps.setInt(2, idChoice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}