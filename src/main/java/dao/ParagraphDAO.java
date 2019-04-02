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
import model.Paragraph;

/**
 *
 * @author raphaelcja
 */
public class ParagraphDAO extends AbstractDataBaseDAO {
    
    public ParagraphDAO(DataSource ds) {
        super(ds);
    }
    
}
