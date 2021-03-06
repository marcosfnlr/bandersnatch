/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @author raphaelcja
 */
public abstract class AbstractDAO {

    protected final DataSource dataSource;

    protected AbstractDAO(DataSource ds) {
        this.dataSource = ds;
    }

    protected Connection getConn() throws SQLException {
        return dataSource.getConnection();
    }
}
