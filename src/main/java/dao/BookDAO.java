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
     * Returns list of books from table Book.
     */
    
}
