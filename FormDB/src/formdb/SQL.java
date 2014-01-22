/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formdb;

/**
 *
 * @author LEE
 */
public class SQL {
    
    PDFFieldReader reader = new PDFFieldReader(Window.getPath());
    DataField[] data = reader.getDataFields();
    String command = "CREATE TABLE forms(Number INTEGER NOT NULL AUTO_INCREMENT, ";
    
    public String createCommand(){
        boolean temp = false;
        
        for(int i = 0; i < data.length; i++){
            if(data[i].isBoolean()){
                if(data[i].getFieldValue().equalsIgnoreCase("yes"))
                    temp = true;
                else
                    temp = false;
                command += data[i].getFieldName() + " " + temp + ", ";
            }
            else{
                command += data[i].getFieldName() + " VARCHAR(255), ";
            }
        }
        
        command += "PRIMARY KEY (Number)";
        
        
        return command;
    }
    
    
}
