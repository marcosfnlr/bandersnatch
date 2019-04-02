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

/**
 *
 * @author raphaelcja
 */
public class ChoiceDAO extends AbstractDataBaseDAO {
    
    public ChoiceDAO(DataSource ds) {
        super(ds);
    }
}
