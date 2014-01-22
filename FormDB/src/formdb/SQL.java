/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formdb;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LEE
 */
public class SQL {
    
    String url = "jdbc:mysql://localhost:3306/";
    Connection conn;
    Statement st;
    
    public SQL (String user, String password, String database) throws SQLException {
        //Set up connection to database with provided credentials
        this.conn = (Connection) DriverManager.getConnection(url + database, user, password);
        this.st = (Statement) this.conn.createStatement();
    }
    
    //Clean up resources
    public void close () {
        try {
            if (this.st != null) this.st.close();
            if (this.conn != null) this.conn.close();
        }
        catch (SQLException ex) {
            System.out.println (ex.getMessage());
        }
    }
    
    //Gets a list of all tables in the current database
    public ArrayList<String> getTables () {
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            DatabaseMetaData md = this.conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                tableNames.add (rs.getString(3));
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tableNames;
    }
    
    //Execute a command on the database
    public boolean execute (String cmd) {
        boolean result = false;
        try {
            result = this.st.execute(cmd);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(cmd);
        }
        return result;
    }
    
    //Creates a command to create a table based on a PDF file
    public static String createCommand(String tableName, String path){
        //Parse PDF file
        PDFFieldReader reader = new PDFFieldReader(path);
        DataField[] data = reader.getDataFields();
        
        String command = "CREATE TABLE " + tableName + "(Number INTEGER NOT NULL AUTO_INCREMENT,\n";
        //Append each field name as the column name
        for (DataField field : data) {
            if (field.isBoolean()) {
                command += field.getFieldName() + " BOOLEAN,\n";
            } else {
                command += field.getFieldName() + " VARCHAR(255),\n";
            }
        }
        
        command += "PRIMARY KEY (Number));";
        
        return command;
    }
    
    //Creates a command to insert field values into a table
    public static String insertCommand(String tableName, String path){
        //Parse PDF file
        PDFFieldReader reader = new PDFFieldReader(path);
        DataField[] data = reader.getDataFields();
        
        String command = "INSERT INTO " + tableName + " (";
        //Append field names in order
        for (int i = 0; i < data.length; i++) {
            command += data[i].getFieldName();
            if (i < data.length - 1) {
                command += ", ";
            }
        }
        
        command += ") VALUES (";
        boolean temp;
        //Append field values in order
        for (int i = 0; i < data.length; i++) {
            if (data[i].isBoolean()) {
                //Convert yes/no to true/false
                if(data[i].getFieldValue().equalsIgnoreCase("yes"))
                    temp = true;
                else
                    temp = false;
                command += temp;
            }
            else {
                command += "'" + data[i].getFieldValue() + "'";
            }
            if (i < data.length - 1) {
                command += ", ";
            }
        }
        
        command += ");";
        
        return command;
    }
}
