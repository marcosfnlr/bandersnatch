package dao;

import javax.sql.DataSource;
import java.sql.*;

public class InvitationDAO extends AbstractDAO {

    public InvitationDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Adds invitation on table Invitation.
     */
    public void addInvitation(String idAccount, int idBook) {

        String query = "INSERT INTO Invitation (fk_account, fk_book) VALUES (?,?)";

        try (
                Connection conn = getConn();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, idAccount);
            ps.setInt(2, idBook);
            int count = ps.executeUpdate();

            if (count < 1) {
                throw new DAOException("Erreur dans la creation de l'invitation");
            }

        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}